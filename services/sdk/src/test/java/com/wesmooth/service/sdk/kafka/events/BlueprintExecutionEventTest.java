/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wesmooth.service.sdk.mongodb.dto.Blueprint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlueprintExecutionEventTest {
  @Test
  public void testNullPointerExceptionWhenEventStatusNull() {
    assertThrows(
        NullPointerException.class,
        () -> new BlueprintExecutionEvent(null, "test", new Blueprint()));
  }

  @Test
  public void testNullPointerExceptionWhenExecutionIdNull() {
    assertThrows(
        NullPointerException.class,
        () -> new BlueprintExecutionEvent(EventStatus.START, null, new Blueprint()));
  }

  @Test
  public void testNullPointerExceptionWhenBlueprintNull() {
    assertThrows(
        NullPointerException.class,
        () -> new BlueprintExecutionEvent(EventStatus.START, "test", null));
  }
}