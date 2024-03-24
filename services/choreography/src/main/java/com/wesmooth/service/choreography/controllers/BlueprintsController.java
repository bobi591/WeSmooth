/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers;

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
import org.apache.kafka.clients.producer.KafkaProducer;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** The public contract (API) for performing actions relevant to Blueprints. */
@CrossOrigin
@RestController
@RequestMapping("/blueprints")
public class BlueprintsController {
  private final MongoConnectionBean mongoConnection;
  private final KafkaProducer kafkaProducer;
  private final KafkaRecordFactory kafkaRecordFactory;

  @Autowired
  public BlueprintsController(MongoConnectionBean mongoConnection, KafkaBean kafkaBean) {
    this.mongoConnection = mongoConnection;
    this.kafkaProducer = kafkaBean.createProducer();
    this.kafkaRecordFactory = kafkaBean.createKafkaRecordFactory();
  }

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
    BlueprintExecutionEvent blueprintExecutionEvent =
        new BlueprintExecutionEvent(executionId, blueprint);
    kafkaProducer.send(kafkaRecordFactory.createBlueprintExecutionRecord(blueprintExecutionEvent));
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
