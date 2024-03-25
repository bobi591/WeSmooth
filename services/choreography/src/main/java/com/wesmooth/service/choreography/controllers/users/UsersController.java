/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers.users;

import com.wesmooth.service.choreography.controllers.users.dto.LoginRequest;
import com.wesmooth.service.sdk.mongodb.MongoConnectionBean;
import com.wesmooth.service.sdk.mongodb.dto.user.User;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
  public ResponseEntity createJwt(@RequestBody LoginRequest loginRequest)
      throws NoSuchPaddingException,
          IllegalBlockSizeException,
          NoSuchAlgorithmException,
          InvalidKeySpecException,
          BadPaddingException,
          InvalidKeyException {
    long expiration = System.currentTimeMillis() + Duration.ofMinutes(30).toMillis();
    if (loginRequest.getUsername().equals("admin")
        && loginRequest.getPassword().equals("admin123")) {
      Jwt jwt =
          new Jwt(
              new Jwt.Header(),
              new Jwt.Payload(expiration, loginRequest.getUsername(), "WeSmooth!"));
      return ResponseEntity.status(200).body(jwtUtility.createJwt(jwt));
    }
    return ResponseEntity.status(401).body("Inavlid credentials.");
  }
}
