/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy.executor;

import com.wesmooth.service.sdk.IFailureConsumer;
import com.wesmooth.service.sdk.ISuccessConsumer;
import com.wesmooth.service.sdk.groovy.IGroovyContainer;
import groovy.util.Eval;

/** String Groovy Code executor. */
public class GroovyStringExecutor extends GroovyExecutor<String> {
  GroovyStringExecutor(IGroovyContainer<String> groovyContainer) {
    super(groovyContainer);
  }

  @Override
  public void execute(
      ISuccessConsumer<String> successConsumer, IFailureConsumer<Exception> failureConsumer) {
    try {
      successConsumer.onSuccess(String.valueOf(Eval.me(this.groovyContainer.getGroovyCode())));
    } catch (Exception e) {
      failureConsumer.onFailure(e);
    }
  }
}
