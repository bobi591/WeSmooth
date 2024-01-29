/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

  private final Environment environment;

  @Autowired
  public ApplicationProperties(Environment environment) {
    this.environment = environment;
  }

  public String getProperty(String propertyName) {
    return this.environment.getProperty(propertyName);
  }
}
