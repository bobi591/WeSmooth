/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security.filters;

import com.wesmooth.service.sdk.security.JwtUtility;
import com.wesmooth.service.sdk.services.users.AuthenticationException;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Base class of JWT Filters.
 *
 * @author Boris Georgiev
 */
public abstract class BaseJwtFilter extends OncePerRequestFilter {
  protected final JwtUtility jwtUtility;

  public BaseJwtFilter(JwtUtility jwtUtility) {
    this.jwtUtility = jwtUtility;
  }

  /**
   * If the condition is false, throws a public exception.
   *
   * @param condition the condition
   * @throws AuthenticationException the security exception
   */
  protected final void trueOrThrow(boolean condition) throws AuthenticationException {
    if (condition) {
      return;
    }
    throw new AuthenticationException();
  }
}
