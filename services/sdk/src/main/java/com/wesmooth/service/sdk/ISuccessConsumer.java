/* WeSmooth! 2024 */
package com.wesmooth.service.sdk;

/**
 * A Functional Interface for consuming successes in workers or different components within the
 * application.
 *
 * @author Boris Georgiev
 */
@FunctionalInterface
public interface ISuccessConsumer<SuccessType> {
  void onSuccess(SuccessType success);
}
