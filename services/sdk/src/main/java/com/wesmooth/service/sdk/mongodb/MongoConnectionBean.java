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
import com.wesmooth.service.sdk.kafka.events.BlueprintSectionExecutionEvent;
import com.wesmooth.service.sdk.mongodb.dto.blueprint.Blueprint;
import com.wesmooth.service.sdk.mongodb.dto.blueprint.BlueprintSection;
import com.wesmooth.service.sdk.mongodb.dto.user.User;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** A bean exposing all required Mongodb functionalities in WeSmooth! */
@Component
public class MongoConnectionBean implements DisposableBean {
  private final String mongoDbName;
  private final MongoClient mongoDbClient;

  @Autowired
  MongoConnectionBean(final ApplicationProperties applicationProperties) {
    ConnectionString connectionString =
        new ConnectionString(applicationProperties.getProperty("wesmooth.mongodb.uri"));
    // TODO: We should add all preservable classes in Mongodb here... is there automatic way to add
    // them here?
    PojoCodecProvider pojoCodecProvider =
        PojoCodecProvider.builder()
            .register(
                Blueprint.class,
                BlueprintSection.class,
                BlueprintSectionExecutionEvent.class,
                User.class)
            .build();
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

  /**
   * Returns the collection for specific DTO type.
   *
   * @param clazz the class of the DTO <i>(the collection in mongodb should carry the same name as
   *     the DTO class, but with lowercase)</i>
   * @return the mongodb collection holding the objects of the requested DTO type
   * @param <T> the type of the DTO
   */
  public <T> MongoCollection<T> getCollection(Class<T> clazz) {
    String collectionName = clazz.getSimpleName().toLowerCase();
    return this.mongoDbClient.getDatabase(this.mongoDbName).getCollection(collectionName, clazz);
  }

  /**
   * Method invoked during the destruction of the bean which is figuratively accepted as the end of
   * the application's lifecycle.
   *
   * @throws Exception an exception that can occur during the termination of the lifecycle
   */
  @Override
  public void destroy() throws Exception {
    this.mongoDbClient.close();
  }
}
