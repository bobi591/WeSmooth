/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.executor;

import com.wesmooth.service.sdk.groovy.IGroovyContainer;
import org.springframework.stereotype.Component;

@Component
public class GroovyExecutorFactory {
  private GroovyExecutorFactory() {}

  public GroovyStringExecutor createForString(IGroovyContainer<String> groovyContainer) {
    return new GroovyStringExecutor(groovyContainer);
  }
}
