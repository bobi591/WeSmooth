/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerPropertiesTest {
  @Test
  public void testKafkaConsumerProperties() {
    KafkaConsumerProperties sut = new KafkaConsumerProperties();
    sut.setGroupIdConfig("groupIdConfig");
    sut.setAutoResetConfig(OffsetResetStrategy.LATEST);
    sut.setBootstrapServersConfig("bootstrapServers");
    sut.setKeyDeserializerClass(Class.class);
    sut.setValueDeserializerClass(Class.class);
    Properties result = sut.build();
    assertEquals(5, result.size());
    assertEquals("groupIdConfig", result.getProperty(ConsumerConfig.GROUP_ID_CONFIG));
    assertEquals(
        OffsetResetStrategy.LATEST.toString(),
        result.getProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG));
    assertEquals("bootstrapServers", result.getProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
    assertEquals(
        Class.class.getName(), result.getProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
    assertEquals(
        Class.class.getName(), result.getProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
  }
}
