/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.sandbox;

import com.wesmooth.service.sdk.IFailureConsumer;
import com.wesmooth.service.sdk.ISuccessConsumer;
import com.wesmooth.service.sdk.groovy.executor.GroovyExecutor;

/**
 * Base class for Groovy Sandboxes. This class should prepare the environment in which the Groovy
 * code should run.
 */
public abstract class GroovySandbox implements AutoCloseable {
  protected GroovySandbox() {}

  /**
   * Runs the provided Groovy executor in a sandbox environment.<br>
   * <b>This method is expected to be an asynchronous action where successes or failures should be
   * transferred to the thread launched the async action via the provided success and failure
   * callback.</b>
   *
   * @param groovyExecutor the executable Groovy code.
   * @param successConsumer callback for successful events.
   * @param failureConsumer callback for failure events
   */
  public abstract void run(
      GroovyExecutor<?> groovyExecutor,
      ISuccessConsumer<String> successConsumer,
      IFailureConsumer<Exception> failureConsumer);
}
