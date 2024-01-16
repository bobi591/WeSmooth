/* WeSmooth! 2024 */
package com.wesmooth.service.choreography;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.wesmooth"})
public class ChoreographyService {
  public static void main(String[] args) {
    SpringApplication.run(ChoreographyService.class, args);
  }
}
