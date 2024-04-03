/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.services.users.login;

import com.mongodb.client.model.Filters;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.mongodb.dto.user.User;
import com.wesmooth.service.sdk.security.Jwt;
import com.wesmooth.service.sdk.security.JwtUtility;
import com.wesmooth.service.sdk.services.users.AuthenticationException;
import com.wesmooth.service.sdk.services.users.login.dto.LoginRequest;
import java.time.Duration;
import lombok.AllArgsConstructor;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoginService {
  @Autowired private MongoConnectionBean mongoConnectionBean;

  @Autowired private JwtUtility jwtUtility;

  public String login(LoginRequest loginRequest) throws AuthenticationException {
    try {
      long expiration = System.currentTimeMillis() + Duration.ofMinutes(30).toMillis();
      Bson eqUsername = Filters.eq("username", loginRequest.getUsername());
      Bson eqPassword = Filters.eq("password", loginRequest.getPassword());
      User user =
          mongoConnectionBean
              .getCollection(User.class)
              .find(Filters.and(eqUsername, eqPassword))
              .first();
      if (user != null) {
        Jwt jwt =
            new Jwt(
                new Jwt.Header(),
                new Jwt.Payload(expiration, loginRequest.getUsername(), "WeSmooth!"));
        return jwtUtility.createJwt(jwt);
      }
      throw new AuthenticationException("The provided credentials are invalid.");
    } catch (Exception e) {
      throw new AuthenticationException(e);
    }
  }
}
