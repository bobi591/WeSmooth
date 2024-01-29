/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.google.gson.Gson;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaRecordFactoryTest {
  @Mock Gson gsonMock;
  @Mock ApplicationProperties applicationPropertiesMock;

  @Test
  public void testKafkaRecordCreation() {
    KafkaRecordFactory sut = new KafkaRecordFactory(applicationPropertiesMock, gsonMock);
    when(applicationPropertiesMock.getProperty(eq("wesmooth.kafka.topic.blueprint.execution")))
        .thenReturn("testTopic");
    when(gsonMock.toJson(any(BlueprintExecutionEvent.class))).thenReturn("testValue");
    ProducerRecord result = sut.createBlueprintExecutionRecord(new BlueprintExecutionEvent());
    assertEquals("testTopic", result.topic());
    assertEquals("testValue", result.value());
  }
}
