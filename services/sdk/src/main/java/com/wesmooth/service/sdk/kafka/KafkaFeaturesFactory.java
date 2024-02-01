/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka;

import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerFactory;
import com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerProperties;
import com.wesmooth.service.sdk.kafka.producer.KafkaProducerFactory;
import com.wesmooth.service.sdk.kafka.producer.KafkaProducerProperties;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KafkaFeaturesFactory {
  private final ApplicationProperties applicationProperties;

  @Autowired
  public KafkaFeaturesFactory(final ApplicationProperties applicationProperties) {
    this.applicationProperties = applicationProperties;
  }

  KafkaFeatureFactory<KafkaConsumer<String, String>, KafkaConsumerProperties>
      createConsumerFactory() {
    // build consumer properties
    final KafkaConsumerProperties kafkaConsumerProperties = new KafkaConsumerProperties();
    kafkaConsumerProperties.setAutoResetConfig(OffsetResetStrategy.EARLIEST);
    kafkaConsumerProperties.setBootstrapServersConfig(
        applicationProperties.getProperty("wesmooth.kafka.server"));
    kafkaConsumerProperties.setGroupIdConfig(
        applicationProperties.getProperty("wesmooth.kafka.groupid"));
    kafkaConsumerProperties.setKeyDeserializerClass(StringDeserializer.class);
    kafkaConsumerProperties.setValueDeserializerClass(StringDeserializer.class);
    return new KafkaConsumerFactory(kafkaConsumerProperties);
  }

  KafkaFeatureFactory<KafkaProducer<String, String>, KafkaProducerProperties>
      createProducerFactory() {
    // build producer properties
    final KafkaProducerProperties kafkaProducerProperties;
    kafkaProducerProperties = new KafkaProducerProperties();
    kafkaProducerProperties.setBootstrapServersConfig(
        applicationProperties.getProperty("wesmooth.kafka.server"));
    kafkaProducerProperties.setKeySerializerClass(StringSerializer.class);
    kafkaProducerProperties.setValueSerializerClass(StringSerializer.class);
    return new KafkaProducerFactory(kafkaProducerProperties);
  }
}
