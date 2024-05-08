/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.common;

import java.util.Properties;
import org.springframework.stereotype.Component;

/**
 * Base class for a Kafka Feature Factory.
 *
 * @param <T> the feature type <i>(for example {@link
 *     org.apache.kafka.clients.consumer.KafkaConsumer})</i>
 * @param <P> the feature properties type <i>(for example {@link
 *     com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerProperties})</i>
 */
@Component
public abstract class KafkaFeatureFactory<T, P extends IKafkaFeatureProperties> {
  protected final Properties featureProperties;

  protected KafkaFeatureFactory(P properties) {
    this.featureProperties = properties.build();
  }

  /**
   * Returns the feature which the factory is responsible creating.
   *
   * @return the instantiated feature
   */
  public abstract T create();
}
