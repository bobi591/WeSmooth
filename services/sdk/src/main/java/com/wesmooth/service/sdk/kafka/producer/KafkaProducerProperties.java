/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.producer;

import com.wesmooth.service.sdk.kafka.common.IKafkaFeatureProperties;
import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerConfig;

/**
 * All properties for a functional Kafka Consumer. <br>
 * <b>Note:</b> Each set method should be populated with a value!
 */
public class KafkaProducerProperties implements IKafkaFeatureProperties {

  private final Properties properties = new Properties();

  public void setBootstrapServersConfig(String bootstrapServers) {
    properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
  }

  public void setKeySerializerClass(Class deserializerClass) {
    properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, deserializerClass.getName());
  }

  public void setValueSerializerClass(Class deserializerClass) {
    properties.setProperty(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, deserializerClass.getName());
  }

  @Override
  public Properties build() {
    return this.properties;
  }
}
