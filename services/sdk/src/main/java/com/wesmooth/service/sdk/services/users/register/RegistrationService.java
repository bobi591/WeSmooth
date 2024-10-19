/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.services.users.register;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.mongodb.dto.user.User;
import com.wesmooth.service.sdk.services.users.AuthenticationException;
import com.wesmooth.service.sdk.services.users.register.dto.RegistrationRequest;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegistrationService {
  @Autowired
  public RegistrationService(MongoConnectionBean mongoConnectionBean) {
    this.mongoConnectionBean = mongoConnectionBean;
  }

  private final MongoConnectionBean mongoConnectionBean;

  public void register(RegistrationRequest registrationRequest) throws AuthenticationException {
    Bson eqUsername = Filters.eq("username", registrationRequest.getUsername());
    Bson eqEmail = Filters.eq("email", registrationRequest.getEmail());
    MongoCollection<User> userCollection = mongoConnectionBean.getCollection(User.class);
    User foundUser = userCollection.find(Filters.and(eqUsername, eqEmail)).first();
    if (foundUser == null) {
      User user = new User();
      user.setEmail(registrationRequest.getEmail());
      user.setName(registrationRequest.getName());
      user.setUsername(registrationRequest.getUsername());
      user.setPassword(registrationRequest.getPassword());
      userCollection.insertOne(user);
    } else {
      throw new AuthenticationException("The provided username or email is in use.");
    }
  }
}
