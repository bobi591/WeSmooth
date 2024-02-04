/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker.kafka;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.wesmooth.service.sdk.IFailureConsumer;
import com.wesmooth.service.sdk.ISuccessConsumer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerWorkerTest {
  private final String kafkaTopic = "TEST_TOPIC_123";
  @Mock private KafkaConsumer<String, String> kafkaConsumerMock;
  @Mock private ScheduledExecutorService scheduledExecutorServiceMock;
  @Mock private ISuccessConsumer workerSuccessConsumerMock;
  @Mock private IFailureConsumer workerFailureConsumerMock;

  @Test
  public void testStart() {
    KafkaConsumerWorker kafkaConsumerWorker =
        new KafkaConsumerWorker(kafkaTopic, kafkaConsumerMock, scheduledExecutorServiceMock);
    kafkaConsumerWorker.start(workerSuccessConsumerMock, workerFailureConsumerMock);
    verify(scheduledExecutorServiceMock, times(1))
        .scheduleAtFixedRate(any(), eq(0L), eq(2L), eq(TimeUnit.SECONDS));
  }

  @Test
  public void testStop() {
    KafkaConsumerWorker kafkaConsumerWorker =
        new KafkaConsumerWorker(kafkaTopic, kafkaConsumerMock, scheduledExecutorServiceMock);
    kafkaConsumerWorker.stop();
    verify(kafkaConsumerMock, times(1)).close();
    verify(scheduledExecutorServiceMock, times(1)).shutdown();
  }

  @Test
  public void testWorkerLogicSuccesses() {
    ConsumerRecord consumerRecord1 = mock(ConsumerRecord.class);
    ConsumerRecord consumerRecord2 = mock(ConsumerRecord.class);
    ConsumerRecord consumerRecord3 = mock(ConsumerRecord.class);
    Map<TopicPartition, List<ConsumerRecord>> records = new HashMap<>();
    records.put(
        new TopicPartition(kafkaTopic, 1),
        Arrays.asList(consumerRecord1, consumerRecord2, consumerRecord3));

    ConsumerRecords consumerRecordsMock = new ConsumerRecords(records);
    when(kafkaConsumerMock.poll(any())).thenReturn(consumerRecordsMock);

    KafkaConsumerWorker kafkaConsumerWorker =
        new KafkaConsumerWorker(
            kafkaTopic, kafkaConsumerMock, Executors.newSingleThreadScheduledExecutor());
    kafkaConsumerWorker.start(workerSuccessConsumerMock, workerFailureConsumerMock);

    await().pollDelay(1, TimeUnit.SECONDS).untilAsserted(() -> Assertions.assertTrue(true));

    verify(workerSuccessConsumerMock, times(3)).onSuccess(any());
  }

  @Test
  public void testWorkerLogicErrors() {
    when(kafkaConsumerMock.poll(any())).thenThrow(RuntimeException.class);

    KafkaConsumerWorker kafkaConsumerWorker =
        new KafkaConsumerWorker(
            kafkaTopic, kafkaConsumerMock, Executors.newSingleThreadScheduledExecutor());
    kafkaConsumerWorker.start(workerSuccessConsumerMock, workerFailureConsumerMock);

    await().pollDelay(1, TimeUnit.SECONDS).untilAsserted(() -> Assertions.assertTrue(true));

    verify(workerFailureConsumerMock, times(1)).onFailure(any());
  }
}
