/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.record;

import com.google.gson.Gson;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Kafka Record Factory. */
@Component
public class KafkaRecordFactory {
  @Autowired
  public KafkaRecordFactory(ApplicationProperties applicationProperties, Gson gson) {
    this.applicationProperties = applicationProperties;
    this.gson = gson;
  }

  private final Gson gson;
  private final ApplicationProperties applicationProperties;

  /**
   * Creates a Kafka Record from a Blueprint execution event.
   *
   * @param event the event that should be represented as value in the Kafka Record
   * @return the Kafka Record
   */
  public ProducerRecord<String, String> createBlueprintExecutionRecord(
      BlueprintExecutionEvent event) {
    String serializedEvent = gson.toJson(event);
    return new ProducerRecord<>(
        applicationProperties.getProperty("wesmooth.kafka.topic.blueprint.execution"),
        serializedEvent);
  }
}
