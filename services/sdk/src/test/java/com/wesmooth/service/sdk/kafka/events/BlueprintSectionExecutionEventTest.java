/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wesmooth.service.sdk.mongodb.dto.BlueprintSection;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlueprintSectionExecutionEventTest {
  @Test
  public void testNullPointerExceptionWhenEventStatusNull() {
    assertThrows(
        NullPointerException.class,
        () -> new BlueprintSectionExecutionEvent(null, "123", new BlueprintSection()));
  }

  @Test
  public void testNullPointerExceptionWhenExecutionIdNull() {
    assertThrows(
        NullPointerException.class,
        () ->
            new BlueprintSectionExecutionEvent(EventStatus.FAILURE, null, new BlueprintSection()));
  }

  @Test
  public void testNullPointerExceptionWhenBlueprintSectionNull() {
    assertThrows(
        NullPointerException.class,
        () -> new BlueprintSectionExecutionEvent(EventStatus.FAILURE, "123", null));
  }
}
