/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers;

import com.mongodb.client.MongoCursor;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.mongodb.dto.Blueprint;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blueprints")
public class BlueprintsController {
  @Autowired MongoConnectionBean mongoConnectionBean;
  @Autowired KafkaBean kafkaBean;

  @GetMapping
  public List<Blueprint> getAll() {
    List<Blueprint> results = new LinkedList<>();
    try (MongoCursor<Blueprint> cursor =
        mongoConnectionBean.getCollection(Blueprint.class).find().iterator()) {
      while (cursor.hasNext()) {
        results.add(cursor.next());
      }
    }
    return results;
  }

  @PostMapping
  public void insert(@RequestBody List<Blueprint> blueprints) {
    mongoConnectionBean.getCollection(Blueprint.class).insertMany(blueprints);
  }

  @PostMapping
  @RequestMapping("/execute")
  public void execute(@RequestBody String bluePrintName) {}
}
