/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.lifecycle;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import com.wesmooth.service.sdk.kafka.events.BlueprintSectionExecutionEvent;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.worker.kafka.KafkaConsumerWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Lifecycle Management Service which has logic for the beginning and the end of the existence of
 * the bean and the Choreography Service.
 */
@Service
@Slf4j
public class LifecycleManagementService implements InitializingBean, DisposableBean {
  private final Gson gson;
  private final KafkaConsumerWorker blueprintSectionExecutionEventsConsumerWorker;
  private final MongoConnectionBean mongoConnectionBean;

  @Autowired
  public LifecycleManagementService(
      final ApplicationProperties applicationProperties,
      final Gson gson,
      final KafkaBean kafkaBean,
      final MongoConnectionBean mongoConnectionBean) {
    this.gson = gson;
    KafkaConsumer<String, String> kafkaConsumer = kafkaBean.createConsumer();
    this.blueprintSectionExecutionEventsConsumerWorker =
        new KafkaConsumerWorker(
            applicationProperties.getProperty("wesmooth.kafka.topic.blueprintsection.execution"),
            kafkaConsumer);
    this.mongoConnectionBean = mongoConnectionBean;
  }

  /**
   * Method invoked during the creation of the bean which is figuratively accepted as the start of
   * the Choreography Service lifecycle.
   *
   * @throws Exception an exception that can occur during the beginning of the lifecycle
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    blueprintSectionExecutionEventsConsumerWorker.start(
        event -> {
          log.info("Saving Blueprint Section Execution Event: " + event);
          BlueprintSectionExecutionEvent executionEvent =
              gson.fromJson(event.value(), BlueprintSectionExecutionEvent.class);
          MongoCollection<BlueprintSectionExecutionEvent> collection =
              mongoConnectionBean.getCollection(BlueprintSectionExecutionEvent.class);
          collection.insertOne(executionEvent);
        },
        failure -> log.error(failure.getMessage()));
  }

  /**
   * Method invoked during the destruction of the bean which is figuratively accepted as the end of
   * the Choreography Service lifecycle.
   *
   * @throws Exception an exception that can occur during the termination of the lifecycle
   */
  @Override
  public void destroy() throws Exception {
    blueprintSectionExecutionEventsConsumerWorker.stop();
    mongoConnectionBean.destroy();
  }
}
