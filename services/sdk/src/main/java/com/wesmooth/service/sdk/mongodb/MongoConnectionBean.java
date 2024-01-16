/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.mongodb;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.wesmooth.service.sdk.configuration.ApplicationProperties;
import com.wesmooth.service.sdk.mongodb.dto.Blueprint;
import com.wesmooth.service.sdk.mongodb.dto.BlueprintSection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MongoConnectionBean implements DisposableBean {
  private final String mongoDbName;
  private final MongoClient mongoDbClient;

  @Autowired
  MongoConnectionBean(final ApplicationProperties applicationProperties) {
    ConnectionString connectionString =
        new ConnectionString(applicationProperties.getProperty("wesmooth.mongodb.uri"));
    PojoCodecProvider pojoCodecProvider =
        PojoCodecProvider.builder().register(Blueprint.class, BlueprintSection.class).build();
    CodecRegistry codecRegistry =
        fromRegistries(
            MongoClientSettings.getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));
    MongoClientSettings clientSettings =
        MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .codecRegistry(codecRegistry)
            .build();
    this.mongoDbClient = MongoClients.create(clientSettings);
    this.mongoDbName = applicationProperties.getProperty("wesmooth.mongodb.dbname");
  }

  public <T> MongoCollection<T> getCollection(Class<T> clazz) {
    String collectionName = clazz.getSimpleName().toLowerCase();
    return this.mongoDbClient.getDatabase(this.mongoDbName).getCollection(collectionName, clazz);
  }

  @Override
  public void destroy() throws Exception {
    this.mongoDbClient.close();
  }
}
