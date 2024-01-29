/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.producer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerPropertiesTest {
  @Test
  public void testKafkaProducerProperties() {
    KafkaProducerProperties sut = new KafkaProducerProperties();
    sut.setBootstrapServersConfig("bootstrapServers");
    sut.setKeySerializerClass(Class.class);
    sut.setValueSerializerClass(Class.class);
    Properties result = sut.build();
    assertEquals(3, result.size());
    assertEquals("bootstrapServers", result.getProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
    assertEquals(
        Class.class.getName(), result.getProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
    assertEquals(
        Class.class.getName(), result.getProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
  }
}
