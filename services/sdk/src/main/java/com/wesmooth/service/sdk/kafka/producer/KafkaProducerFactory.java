/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.producer;

import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerFactory
    extends KafkaFeatureFactory<KafkaProducer<String, String>, KafkaProducerProperties> {
  public KafkaProducerFactory(KafkaProducerProperties properties) {
    super(properties);
  }

  @Override
  public KafkaProducer<String, String> create() {
    return new KafkaProducer<>(this.featureProperties);
  }
}
