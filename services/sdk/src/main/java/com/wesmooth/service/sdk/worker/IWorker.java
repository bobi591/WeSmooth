/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker;

/**
 * The interface of a Worker with callbacks for success and failure.
 *
 * @param <T> the type of the success object
 * @param <S> the type of the success callback
 * @param <F> the type of the failure callback
 * @author Boris Georgiev
 */
public interface IWorker<
    T, S extends IWorkerSuccessConsumer<T>, F extends IWorkerFailureConsumer<Exception>> {
  /**
   * Starts the Worker. In this methods it is suppsed to put the multithreading logic like
   * ExecutorServices, etc.
   *
   * @param successCallback callback for provision of successful events of the worker.
   * @param failureCallback callback for provision of failure events (Exceptions) of the worker.
   */
  void start(S successCallback, F failureCallback);

  void stop();
}
