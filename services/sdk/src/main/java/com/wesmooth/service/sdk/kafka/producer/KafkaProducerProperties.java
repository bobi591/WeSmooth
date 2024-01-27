/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.producer;

import com.wesmooth.service.sdk.kafka.common.IKafkaFeatureProperties;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;

public class KafkaProducerProperties implements IKafkaFeatureProperties {

  private final Properties properties = new Properties();

  public void setBootstrapServersConfig(String bootstrapServers) {
    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
  }

  public void setKeyDeserializerClass(Class deserializerClass) {
    properties.setProperty(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, deserializerClass.getName());
  }

  public void setValueDeserializerClass(Class deserializerClass) {
    properties.setProperty(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializerClass.getName());
  }

  @Override
  public Properties build() {
    return new Properties(this.properties);
  }
}
