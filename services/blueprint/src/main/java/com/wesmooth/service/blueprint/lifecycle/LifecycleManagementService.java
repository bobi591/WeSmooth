/* WeSmooth! 2024 */
package com.wesmooth.service.blueprint.lifecycle;

import com.google.gson.Gson;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.groovy.executor.GroovyExecutor;
import com.wesmooth.service.sdk.groovy.executor.GroovyExecutorFactory;
import com.wesmooth.service.sdk.groovy.sandbox.GroovySandbox;
import com.wesmooth.service.sdk.groovy.sandbox.GroovySandboxFactory;
import com.wesmooth.service.sdk.kafka.KafkaBean;
import com.wesmooth.service.sdk.kafka.events.BlueprintExecutionEvent;
import com.wesmooth.service.sdk.kafka.events.BlueprintSectionExecutionEvent;
import com.wesmooth.service.sdk.kafka.events.EventStatus;
import com.wesmooth.service.sdk.kafka.record.KafkaRecordFactory;
import com.wesmooth.service.sdk.worker.kafka.KafkaConsumerWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
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
  private final Gson gson;
  private final KafkaConsumerWorker kafkaConsumerWorker;
  private final KafkaProducer kafkaProducer;
  private final KafkaRecordFactory kafkaRecordFactory;
  private final GroovyExecutorFactory groovyExecutorFactory;
  private final GroovySandbox groovySandbox;

  @Autowired
  public LifecycleManagementService(
      final ApplicationProperties applicationProperties,
      final Gson gson,
      final KafkaBean kafkaBean,
      final GroovySandboxFactory groovySandboxFactory,
      final GroovyExecutorFactory groovyExecutorFactory) {
    this.gson = gson;
    KafkaConsumer<String, String> kafkaConsumer = kafkaBean.createConsumer();
    this.kafkaConsumerWorker =
        new KafkaConsumerWorker(
            applicationProperties.getProperty("wesmooth.kafka.topic.blueprint.execution"),
            kafkaConsumer);
    this.kafkaProducer = kafkaBean.createProducer();
    this.groovySandbox = groovySandboxFactory.createWithVirtualThreads();
    this.groovyExecutorFactory = groovyExecutorFactory;
    this.kafkaRecordFactory = kafkaBean.createKafkaRecordFactory();
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
        event -> {
          // Convert the event JSON into BlueprintExecutionEvent object.
          BlueprintExecutionEvent blueprintExecutionEvent =
              gson.fromJson(event.value(), BlueprintExecutionEvent.class);
          log.info(
              "Consuming blueprint execution event: " + blueprintExecutionEvent.getExecutionId());
          // Iterate all sections of the retrieved Blueprint in the event.
          blueprintExecutionEvent
              .getBlueprint()
              .getSections()
              .forEach(
                  blueprintSection -> {
                    // Create Groovy Executor
                    GroovyExecutor<String> groovyExecutor =
                        this.groovyExecutorFactory.createForString(blueprintSection);
                    // Pass the Groovy Executor to the Groovy Sandbox for execution.
                    groovySandbox.run(
                        groovyExecutor,
                        success -> {
                          // Send execution status success event
                          log.info(success);
                          kafkaProducer.send(
                              kafkaRecordFactory.createBlueprintSectionExecutionRecord(
                                  new BlueprintSectionExecutionEvent(
                                      EventStatus.SUCCESS,
                                      blueprintExecutionEvent.getExecutionId(),
                                      blueprintSection)));
                        },
                        exception -> {
                          log.error(exception.getMessage());
                          // Send execution status failure event
                          kafkaProducer.send(
                              kafkaRecordFactory.createBlueprintSectionExecutionRecord(
                                  new BlueprintSectionExecutionEvent(
                                      EventStatus.FAILURE,
                                      blueprintExecutionEvent.getExecutionId(),
                                      blueprintSection)));
                        });
                  });
        },
        exception -> log.error(exception.getMessage()));
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
    groovySandbox.close();
    kafkaProducer.close();
  }
}
