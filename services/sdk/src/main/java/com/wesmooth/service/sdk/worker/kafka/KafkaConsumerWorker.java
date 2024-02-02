/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker.kafka;

import com.wesmooth.service.sdk.worker.IWorker;
import com.wesmooth.service.sdk.worker.IWorkerFailureConsumer;
import com.wesmooth.service.sdk.worker.IWorkerSuccessConsumer;
import java.time.Duration;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * Kafka Consumer polling each 2 seconds using {@link ScheduledExecutorService}.
 *
 * @author Boris Georgiev
 */
@Slf4j
public class KafkaConsumerWorker
    implements IWorker<
        ConsumerRecord<String, String>,
        IWorkerSuccessConsumer<ConsumerRecord<String, String>>,
        IWorkerFailureConsumer<Exception>> {

  private final String kafkaTopic;
  private final KafkaConsumer<String, String> kafkaConsumer;
  private final ScheduledExecutorService scheduledExecutorService;

  public KafkaConsumerWorker(
      String kafkaTopic,
      KafkaConsumer<String, String> kafkaConsumer,
      ScheduledExecutorService scheduledExecutorService) {
    this.kafkaTopic = kafkaTopic;
    this.kafkaConsumer = kafkaConsumer;
    this.scheduledExecutorService = scheduledExecutorService;
  }

  public KafkaConsumerWorker(String kafkaTopic, KafkaConsumer<String, String> kafkaConsumer) {
    this.kafkaTopic = kafkaTopic;
    this.kafkaConsumer = kafkaConsumer;
    this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
  }

  /**
   * Start the Kafka Consumer Worker by scheduling the poll at fixed rate of 2 seconds.
   *
   * @param successCallback the callback that will be called on successful retrieval of event and
   *     will provide the actual event as {@link ConsumerRecord}.
   * @param failureCallback the callback that will be called on (Exception) failure during the
   *     retrieval of the event and will provide the actual failure as {@link Exception}.
   */
  @Override
  public void start(
      IWorkerSuccessConsumer<ConsumerRecord<String, String>> successCallback,
      IWorkerFailureConsumer<Exception> failureCallback) {
    log.info("Subscribing worker.");
    this.kafkaConsumer.subscribe(Collections.singleton(this.kafkaTopic));
    log.info("Staring worker.");
    this.scheduledExecutorService.scheduleAtFixedRate(
        () -> {
          try {
            log.info("Polling Kafka Consumer.");
            ConsumerRecords<String, String> consumerRecords =
                this.kafkaConsumer.poll(Duration.ofMillis(10));
            consumerRecords.records(this.kafkaTopic).forEach(successCallback::onSuccess);
          } catch (Throwable e) {
            failureCallback.onFailure(new Exception(e));
          }
        },
        0,
        2,
        TimeUnit.SECONDS);
  }

  /** Stops the worker by closing the Kafka Consumer and the Scheduled Executor Service. */
  @Override
  public void stop() {
    log.info("Requesting worker shutdown.");
    this.scheduledExecutorService.shutdown();
    this.kafkaConsumer.close();
  }
}
