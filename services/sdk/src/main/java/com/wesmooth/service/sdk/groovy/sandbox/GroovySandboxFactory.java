/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.sandbox;

import org.springframework.stereotype.Component;

/** Factory bean for Groovy Sandboxes. */
@Component
public class GroovySandboxFactory {
  private GroovySandboxFactory() {}

  /**
   * Creates a new instance of Groovy Sandbox with Virtual Threads.
   *
   * @return a new instance of Groovy Sandbox with Virtual Threads.
   */
  public GroovySandbox createWithVirtualThreads() {
    return new GroovyVirtualThreadsSandbox();
  }
}
