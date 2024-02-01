/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka;

import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerProperties;
import com.wesmooth.service.sdk.kafka.producer.KafkaProducerProperties;
import com.wesmooth.service.sdk.kafka.record.KafkaRecordFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** A bean exposing all required Kafka functionalities in WeSmooth! */
@Component
public class KafkaBean {
  private final KafkaFeatureFactory<KafkaConsumer<String, String>, KafkaConsumerProperties>
      kafkaConsumerFactory;
  private final KafkaFeatureFactory<KafkaProducer<String, String>, KafkaProducerProperties>
      kafkaProducerFactory;
  private final KafkaRecordFactory kafkaRecordFactory;

  @Autowired
  public KafkaBean(
      KafkaFeaturesFactory kafkaFeaturesFactory, KafkaRecordFactory kafkaRecordFactory) {
    this.kafkaConsumerFactory = kafkaFeaturesFactory.createConsumerFactory();
    this.kafkaProducerFactory = kafkaFeaturesFactory.createProducerFactory();
    this.kafkaRecordFactory = kafkaRecordFactory;
  }

  /**
   * Creates a Kafka Consumer
   *
   * @return the Kafka Consumer
   */
  public KafkaConsumer<String, String> createConsumer() {
    return this.kafkaConsumerFactory.create();
  }

  /**
   * Creates a Kafka Producer
   *
   * @return the Kafka Producer
   */
  public KafkaProducer<String, String> createProducer() {
    return this.kafkaProducerFactory.create();
  }

  /**
   * Creates a Kafka Record Factory
   *
   * @return the Kafka Record Factory
   */
  public KafkaRecordFactory createKafkaRecordFactory() {
    return this.kafkaRecordFactory;
  }
}
