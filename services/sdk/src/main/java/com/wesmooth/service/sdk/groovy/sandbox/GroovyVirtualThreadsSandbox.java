/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.sandbox;

import com.wesmooth.service.sdk.IFailureConsumer;
import com.wesmooth.service.sdk.ISuccessConsumer;
import com.wesmooth.service.sdk.groovy.executor.GroovyExecutor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Sandbox for String Groovy Code execution using Virtual Threads. */
class GroovyVirtualThreadsSandbox extends GroovySandbox {
  private final ExecutorService executorService;

  GroovyVirtualThreadsSandbox(ExecutorService executorService) {
    this.executorService = executorService;
  }

  public GroovyVirtualThreadsSandbox() {
    this.executorService = Executors.newVirtualThreadPerTaskExecutor();
  }

  @Override
  public void run(
      GroovyExecutor<?> groovyExecutor,
      ISuccessConsumer<String> successConsumer,
      IFailureConsumer<Exception> failureConsumer) {
    executorService.submit(
        () -> {
          try {
            groovyExecutor.execute(successConsumer, failureConsumer);
          } catch (Exception exception) {
            failureConsumer.onFailure(exception);
          }
        });
  }

  @Override
  public void close() throws Exception {
    this.executorService.close();
  }
}
