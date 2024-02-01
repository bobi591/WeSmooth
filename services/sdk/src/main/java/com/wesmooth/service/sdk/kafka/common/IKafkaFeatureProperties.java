/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.common;

import java.util.Properties;

/** Interface for the Kafka Feature Properties. */
public interface IKafkaFeatureProperties {
  /**
   * Turn the properties gathered in the object to a {@link Properties} representation.
   *
   * @return the properties representation
   */
  Properties build();
}
