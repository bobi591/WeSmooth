/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.groovy;

/**
 * Interface that describes an object that contains Groovy code.
 *
 * @param <T> the type of the Groovy code. <i>For example it can be in {@link String} format.</i>
 */
public interface IGroovyContainer<T> {
  /**
   * Getter for the Groovy code.
   *
   * @return the Groovy code.
   */
  T getGroovyCode();
}
