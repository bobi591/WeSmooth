/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.wesmooth.service.sdk.kafka.common.KafkaFeatureFactory;
import com.wesmooth.service.sdk.kafka.consumer.KafkaConsumerProperties;
import com.wesmooth.service.sdk.kafka.producer.KafkaProducerProperties;
import com.wesmooth.service.sdk.kafka.record.KafkaRecordFactory;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaBeanTest {
  @Mock
  private KafkaFeatureFactory<KafkaConsumer<String, String>, KafkaConsumerProperties>
      kafkaConsumerFactoryMock;

  @Mock private KafkaConsumer kafkaConsumerMock;

  @Mock
  private KafkaFeatureFactory<KafkaProducer<String, String>, KafkaProducerProperties>
      kafkaProducerFactoryMock;

  @Mock private KafkaProducer kafkaProducerMock;
  @Mock private KafkaRecordFactory kafkaRecordFactoryMock;
  @Mock private KafkaFeaturesFactory kafkaFeaturesFactory;

  @BeforeEach
  public void setup() {
    when(kafkaFeaturesFactory.createConsumerFactory()).thenReturn(kafkaConsumerFactoryMock);
    when(kafkaFeaturesFactory.createProducerFactory()).thenReturn(kafkaProducerFactoryMock);
    when(kafkaConsumerFactoryMock.create()).thenReturn(kafkaConsumerMock);
    when(kafkaProducerFactoryMock.create()).thenReturn(kafkaProducerMock);
  }

  @Test
  public void testCreateMethods() {
    final KafkaBean kafkaBean = new KafkaBean(kafkaFeaturesFactory, kafkaRecordFactoryMock);
    assertEquals(kafkaConsumerMock, kafkaBean.createConsumer());
    assertEquals(kafkaProducerMock, kafkaBean.createProducer());
    assertEquals(kafkaRecordFactoryMock, kafkaBean.createKafkaRecordFactory());
  }
}
