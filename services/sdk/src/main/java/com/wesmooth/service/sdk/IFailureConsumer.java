/* WeSmooth! 2024 */
package com.wesmooth.service.sdk;

/**
 * A Functional Interface for consuming failures in workers or different components within the
 * application.
 *
 * @author Boris Georgiev
 */
@FunctionalInterface
public interface IFailureConsumer<FailureType extends Exception> {
  void onFailure(FailureType failure);
}
