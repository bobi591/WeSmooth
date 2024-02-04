/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * This bean gives access to the properties of the environment in which the current application is
 * running.
 */
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {

  private final Environment environment;

  @Autowired
  public ApplicationProperties(Environment environment) {
    this.environment = environment;
  }

  /**
   * Get a property from the environment. <br>
   * For example the value of the <i>wesmooth.kafka.server</i> property.
   *
   * @param propertyName the name of the property in the environment or the property file
   * @return the string representation of the property value or null not found
   */
  public String getProperty(String propertyName) {
    // TODO: Should we make a check for null and throw an Exception when happens?
    return this.environment.getProperty(propertyName);
  }
}
