/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.consumer;

import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/** Kafka Consumer Factory. */
public class KafkaConsumerFactory
    extends KafkaFeatureFactory<KafkaConsumer<String, String>, KafkaConsumerProperties> {
  public KafkaConsumerFactory(KafkaConsumerProperties properties) {
    super(properties);
  }

  @Override
  public KafkaConsumer<String, String> create() {
    return new KafkaConsumer<>(super.featureProperties);
  }
}
