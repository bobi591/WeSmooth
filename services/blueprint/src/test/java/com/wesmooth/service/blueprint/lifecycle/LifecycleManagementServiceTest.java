/* WeSmooth! 2024 */
package com.wesmooth.service.blueprint.lifecycle;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LifecycleManagementServiceTest {
  @Mock ApplicationProperties applicationPropertiesMock;
  @Mock KafkaBean kafkaBeanMock;
  @Mock KafkaConsumer<String, String> kafkaConsumerMock;

  @BeforeEach
  public void setup() {
    when(kafkaBeanMock.createConsumer()).thenReturn(kafkaConsumerMock);
  }

  @Test
  public void testConstruction() {
    LifecycleManagementService lifecycleManagementService =
        new LifecycleManagementService(applicationPropertiesMock, kafkaBeanMock);
    verify(applicationPropertiesMock, times(1)).getProperty(anyString());
    verify(kafkaBeanMock, times(1)).createConsumer();
  }

  @Test
  public void testAfterPropertiesSet() throws Exception {
    LifecycleManagementService lifecycleManagementService =
        new LifecycleManagementService(applicationPropertiesMock, kafkaBeanMock);
    lifecycleManagementService.afterPropertiesSet();
    verify(kafkaConsumerMock, times(1)).subscribe(anyCollection());
  }

  @Test
  public void testDestroy() throws Exception {
    LifecycleManagementService lifecycleManagementService =
        new LifecycleManagementService(applicationPropertiesMock, kafkaBeanMock);
    lifecycleManagementService.destroy();
    verify(kafkaConsumerMock, times(1)).close();
  }
}
