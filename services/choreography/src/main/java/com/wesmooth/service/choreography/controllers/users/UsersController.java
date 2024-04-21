/* WeSmooth! 2024 */
package com.wesmooth.service.choreography.controllers.users;

import com.wesmooth.service.sdk.security.SecurityException;
import com.wesmooth.service.sdk.services.users.AuthenticationException;
import com.wesmooth.service.sdk.services.users.login.LoginService;
import com.wesmooth.service.sdk.services.users.login.dto.LoginRequest;
import com.wesmooth.service.sdk.services.users.register.RegistrationService;
import com.wesmooth.service.sdk.services.users.register.dto.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Boris Georgiev
 */
@CrossOrigin
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UsersController {

  @Autowired private LoginService loginService;
  @Autowired private RegistrationService registrationService;

  @PostMapping
  @RequestMapping("/register")
  public void createUser(@RequestBody RegistrationRequest registrationRequest)
      throws AuthenticationException {
    registrationService.register(registrationRequest);
  }

  @PostMapping
  @RequestMapping("/oauth2")
  public String createJwt(@RequestBody LoginRequest loginRequest)
      throws AuthenticationException, SecurityException {
    return loginService.login(loginRequest);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<String> handleAuthorizationException(
      AuthenticationException authenticationException) {
    return new ResponseEntity<>(
        authenticationException.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
  }
}
