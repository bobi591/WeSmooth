/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers;

import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.security.jwt.JwtUtility;
import com.wesmooth.service.sdk.security.jwt.dto.Jwt;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Boris Georgiev
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {
  private final MongoConnectionBean mongoConnectionBean;
  private final JwtUtility jwtUtility;

  @Autowired
  public UsersController(MongoConnectionBean mongoConnection, JwtUtility jwtUtility) {
    this.mongoConnectionBean = mongoConnection;
    this.jwtUtility = jwtUtility;
  }

  @PostMapping
  @RequestMapping("/oauth2")
  public String createJwt()
      throws NoSuchPaddingException,
          IllegalBlockSizeException,
          NoSuchAlgorithmException,
          InvalidKeySpecException,
          BadPaddingException,
          InvalidKeyException {
    long expiration = System.currentTimeMillis() + Duration.ofMinutes(30).toMillis();
    Jwt jwt = new Jwt(new Jwt.Header(), new Jwt.Payload(expiration, "test", "test"));
    return jwtUtility.createJwt(jwt);
  }
}
