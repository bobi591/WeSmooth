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

  @Override
  public void afterPropertiesSet() throws Exception {
    kafkaConsumerWorker.start(
        success -> log.info("Consuming blueprint execution event: " + success.value()),
        failure -> log.error(failure.getMessage()));
  }

  @Override
  public void destroy() throws Exception {
    kafkaConsumerWorker.stop();
  }
}
