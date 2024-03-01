/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public class SecurityConfiguration {
  @Bean
  @SuppressWarnings("removal")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .authorizeHttpRequests(
            requests -> {
              requests.requestMatchers("/*").hasAnyRole().anyRequest().authenticated();
            })
        .formLogin(form -> form.loginPage("/login").permitAll())
        .logout(LogoutConfigurer::permitAll)
        .oauth2ResourceServer()
        .jwt();
    return http.build();
  }
}
