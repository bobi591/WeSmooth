/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.services.users;

/**
 * Exception relevant to the authentication process and expected to return trigger HTTP Status 401
 * (Unauthorized).
 */
public class AuthenticationException extends Exception {
  public AuthenticationException() {}

  public AuthenticationException(String message) {
    super(message);
  }

  public AuthenticationException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthenticationException(Throwable cause) {
    super(cause);
  }

  public AuthenticationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
