/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.executor;

import com.wesmooth.service.sdk.IFailureConsumer;
import com.wesmooth.service.sdk.ISuccessConsumer;
import com.wesmooth.service.sdk.groovy.IGroovyContainer;

/**
 * Base class for Groovy Executors. This class should consume the environment in which the Groovy
 * code should run, process the Groovy code and execute it on the provided Groovy sandbox.
 *
 * @param <GroovyCodeType> the type of the groovy code.
 */
public abstract class GroovyExecutor<GroovyCodeType> {
  protected final IGroovyContainer<GroovyCodeType> groovyContainer;

  protected GroovyExecutor(IGroovyContainer<GroovyCodeType> groovyContainer) {
    this.groovyContainer = groovyContainer;
  }

  /**
   * Executes the provided Groovy code.<br>
   * <b>It is recommended to call this method in {@link
   * com.wesmooth.service.sdk.groovy.sandbox.GroovySandbox#run}, otherwise the execution of the
   * Groovy will not be safe and will execute on a single thread.</b>
   *
   * @param successConsumer consumer of successful events.
   * @param failureConsumer consumer of failure events
   */
  public abstract void execute(
      ISuccessConsumer<String> successConsumer, IFailureConsumer<Exception> failureConsumer);
}
