/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

  @Bean
  @SuppressWarnings("removal")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.cors().and().build();
  }
}
