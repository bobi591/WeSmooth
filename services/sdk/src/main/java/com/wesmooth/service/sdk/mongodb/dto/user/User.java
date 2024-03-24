/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.mongodb.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

/**
 * @author Boris Georgiev
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
  private ObjectId id;

  @BsonProperty("username")
  private String username;

  @BsonProperty("email")
  private String email;

  @BsonProperty("password")
  private String password;

  @BsonProperty("name")
  private String name;
}
