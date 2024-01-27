/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.record;

import com.google.gson.Gson;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaRecordFactory {
  @Autowired private Gson gson;
  @Autowired ApplicationProperties applicationProperties;

  public ProducerRecord<String, String> createBlueprintExecutionRecord(
      BlueprintExecutionEvent event) {
    String serializedEvent = gson.toJson(event);
    return new ProducerRecord<>(
        applicationProperties.getProperty("wesmooth.kafka.topic.blueprint.execution"),
        serializedEvent);
  }
}
