/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker;

/**
 * @author Boris Georgiev
 */
public interface IWorker<
    T, S extends IWorkerSuccessConsumer<T>, F extends IWorkerFailureConsumer<Exception>> {
  void start(S successCallback, F failureCallback);

  void stop();
}
