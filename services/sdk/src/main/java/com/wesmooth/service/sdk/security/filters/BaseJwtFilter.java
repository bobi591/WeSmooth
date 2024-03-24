/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security.filters;

import com.wesmooth.service.sdk.security.SecurityException;
import com.wesmooth.service.sdk.security.jwt.JwtUtility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
   * If the condition is false, throws a security exception.
   *
   * @param condition the condition
   * @throws SecurityException the security exception
   */
  protected final void trueOrThrow(boolean condition) throws SecurityException {
    if (condition) {
      return;
    }
    throw new SecurityException("JWT is missing, malformed or expired.");
  }

  /**
   * If the condition is false, continues the filter chain execution.
   *
   * @param condition the condition
   * @param request the request
   * @param response the response
   * @param filterChain the filter chain
   * @return the value of the condition, if it is false, then continues the filter chain execution.
   * @throws ServletException exception during the continuation of the filter chain execution
   * @throws IOException exception during the continuation of the filter chain execution
   */
  protected final boolean trueOrSkipFilter(
      boolean condition,
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    if (!condition) {
      filterChain.doFilter(request, response);
      return false;
    }
    return true;
  }
}
