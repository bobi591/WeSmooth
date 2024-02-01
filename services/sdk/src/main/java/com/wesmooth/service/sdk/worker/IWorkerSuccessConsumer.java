/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.worker;

/**
 * @author Boris Georgiev
 */
@FunctionalInterface
public interface IWorkerSuccessConsumer<T> {
  void onSuccess(T success);
}
