/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers.blueprints;

import static com.mongodb.client.model.Filters.eq;

import com.mongodb.client.MongoCursor;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import com.wesmooth.service.sdk.kafka.events.BlueprintSectionExecutionEvent;
import com.wesmooth.service.sdk.kafka.record.KafkaRecordFactory;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.mongodb.dto.blueprint.Blueprint;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/** The public contract (API) for performing actions relevant to Blueprints. */
@CrossOrigin
@RestController
@RequestMapping("/blueprints")
public class BlueprintsController {

  @Autowired
  public BlueprintsController(final MongoConnectionBean mongoConnection, final KafkaBean kafkaBean, KafkaRecordFactory kafkaRecordFactory) {
    this.mongoConnection = mongoConnection;
    this.kafkaProducer = kafkaBean.createProducer();
    this.kafkaRecordFactory = kafkaRecordFactory;
  }
  private final MongoConnectionBean mongoConnection;
  private final KafkaProducer<String, String> kafkaProducer;
  private final KafkaRecordFactory kafkaRecordFactory;

  @GetMapping
  public List<Blueprint> getAll() {
    List<Blueprint> results = new LinkedList<>();
    try (MongoCursor<Blueprint> cursor =
        mongoConnection.getCollection(Blueprint.class).find().iterator()) {
      while (cursor.hasNext()) {
        results.add(cursor.next());
      }
    }
    return results;
  }

  @PostMapping
  public void insert(@RequestBody List<Blueprint> blueprints) {
    mongoConnection.getCollection(Blueprint.class).insertMany(blueprints);
  }

  @PostMapping
  @RequestMapping("/execute")
  public String execute(@RequestBody String blueprintName) {
    String executionId = UUID.randomUUID().toString();
    Bson blueprintNameFilter = eq("blueprint_name", blueprintName);
    Blueprint blueprint =
        mongoConnection.getCollection(Blueprint.class).find(blueprintNameFilter).first();
    if (blueprint == null) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "Blueprint with name: " + blueprintName + " not found");
    }
    BlueprintExecutionEvent blueprintExecutionEvent =
        new BlueprintExecutionEvent(executionId, blueprint);
    kafkaProducer.send(
            kafkaRecordFactory.createBlueprintExecutionRecord(blueprintExecutionEvent));
    return "Kafka event for blueprint execution created with executionId: " + executionId;
  }

  @GetMapping
  @RequestMapping("/statuses")
  public List<BlueprintSectionExecutionEvent> getAllBlueprintSectionExecutionEvents() {
    List<BlueprintSectionExecutionEvent> results = new LinkedList<>();
    try (MongoCursor<BlueprintSectionExecutionEvent> cursor =
        mongoConnection.getCollection(BlueprintSectionExecutionEvent.class).find().iterator()) {
      while (cursor.hasNext()) {
        results.add(cursor.next());
      }
    }
    return results;
  }
}
