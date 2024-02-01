/* WeSmooth! 2024 */
package com.wesmooth.service.blueprint.lifecycle;

import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import com.wesmooth.service.sdk.worker.kafka.KafkaConsumerWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Lifecycle Management Service which has logic for the beginning and the end of the existence of
 * the bean and the Blueprint Service.
 */
@Service
@Slf4j
public class LifecycleManagementService implements InitializingBean, DisposableBean {
  private final KafkaConsumerWorker kafkaConsumerWorker;

  @Autowired
  public LifecycleManagementService(
      final ApplicationProperties applicationProperties, final KafkaBean kafkaBean) {
    KafkaConsumer<String, String> kafkaConsumer = kafkaBean.createConsumer();
    this.kafkaConsumerWorker =
        new KafkaConsumerWorker(
            applicationProperties.getProperty("wesmooth.kafka.topic.blueprint.execution"),
            kafkaConsumer);
  }

  /**
   * Method invoked during the creation of the bean which is figuratively accepted as the start of
   * the Blueprint Service lifecycle.
   *
   * @throws Exception an exception that can occur during the beginning of the lifecycle
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    kafkaConsumerWorker.start(
        success -> log.info("Consuming blueprint execution event: " + success.value()),
        failure -> log.error(failure.getMessage()));
  }

  /**
   * Method invoked during the destruction of the bean which is figuratively accepted as the end of
   * the Blueprint Service lifecycle.
   *
   * @throws Exception an exception that can occur during the termination of the lifecycle
   */
  @Override
  public void destroy() throws Exception {
    kafkaConsumerWorker.stop();
  }
}
