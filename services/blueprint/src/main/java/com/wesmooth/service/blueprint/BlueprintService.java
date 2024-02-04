/* WeSmooth! 2024 */
package com.wesmooth.service.blueprint; /* WeSmooth! 2024 */

import com.wesmooth.service.blueprint.lifecycle.LifecycleManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@Slf4j
@SpringBootApplication
@ComponentScan({
  "com.wesmooth.service.sdk.configuration",
  "com.wesmooth.service.sdk.worker",
  "com.wesmooth.service.sdk.kafka",
  "com.wesmooth.service.sdk.groovy",
  "com.wesmooth.service.blueprint.lifecycle"
})
public class BlueprintService {
  @Autowired LifecycleManagementService lifecycleManagementService;

  public static void main(String[] args) {
    SpringApplication.run(BlueprintService.class, args);
  }
}
