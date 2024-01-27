/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.common;

import java.util.Properties;
import org.springframework.stereotype.Component;

@Component
public abstract class KafkaFeatureFactory<T, P extends IKafkaFeatureProperties> {
  protected final Properties featureProperties;

  public KafkaFeatureFactory(P properties) {
    this.featureProperties = properties.build();
  }

  public abstract T create();
}
