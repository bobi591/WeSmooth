/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka;

import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerProperties;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import com.wesmooth.service.sdk.kafka.producer.KafkaProducerProperties;
import com.wesmooth.service.sdk.kafka.record.KafkaRecordFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaBean {
  private final ApplicationProperties applicationProperties;
  private final KafkaFeatureFactory<KafkaConsumer, KafkaConsumerProperties> kafkaConsumerFactory;
  private final KafkaFeatureFactory<KafkaProducer, KafkaProducerProperties> kafkaProducerFactory;
  private final KafkaRecordFactory kafkaRecordFactory;

  @Autowired
  public KafkaBean(
      ApplicationProperties applicationProperties,
      KafkaFeaturesFactory kafkaFeaturesFactory,
      KafkaRecordFactory kafkaRecordFactory) {
    this.applicationProperties = applicationProperties;
    this.kafkaConsumerFactory = kafkaFeaturesFactory.createConsumerFactory();
    this.kafkaProducerFactory = kafkaFeaturesFactory.createProducerFactory();
    this.kafkaRecordFactory = kafkaRecordFactory;
  }

  public KafkaConsumer createConsumer() {
    return this.kafkaConsumerFactory.create();
  }

  public KafkaProducer createProducer() {
    return this.kafkaProducerFactory.create();
  }

  public ProducerRecord createRecord(BlueprintExecutionEvent blueprintExecutionEvent) {
    return this.kafkaRecordFactory.createBlueprintExecutionRecord(blueprintExecutionEvent);
  }
}
