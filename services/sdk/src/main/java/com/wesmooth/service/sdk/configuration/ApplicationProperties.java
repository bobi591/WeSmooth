/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationProperties {
  @Autowired private Environment environment;

  public String getProperty(String propertyName) {
    return this.environment.getProperty(propertyName);
  }
}
