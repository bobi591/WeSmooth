/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka;

import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

public class KafkaBean {
  @Autowired ApplicationProperties applicationProperties;

  @Bean
  public Map<String, Object> producerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
        applicationProperties.getProperty("wesmooth.kafka.server"));
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
    return props;
  }

  @Bean
  public ProducerFactory<Integer, Object> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  @Bean
  public KafkaTemplate<Integer, Object> kafkaTemplate() {
    return new KafkaTemplate<Integer, Object>(producerFactory());
  }

  @Bean
  public KafkaAdmin admin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic blueprintsExecutionTopic() {
    return TopicBuilder.name("queuing.blueprints.execution")
        .partitions(5)
        .replicas(2)
        .compact()
        .build();
  }

  @Bean
  public NewTopic blueprintsSectionExecutionTopic() {
    return TopicBuilder.name("queuing.blueprintsSection.execution")
        .partitions(5)
        .replicas(2)
        .compact()
        .build();
  }
}
