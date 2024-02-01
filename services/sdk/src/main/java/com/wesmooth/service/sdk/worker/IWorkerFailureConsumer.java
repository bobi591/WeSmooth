/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker;

/**
 * @author Boris Georgiev
 */
@FunctionalInterface
public interface IWorkerFailureConsumer<T extends Exception> {
  void onFailure(T failure);
}
