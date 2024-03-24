/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.wesmooth.service.sdk.mongodb.dto.blueprint.Blueprint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BlueprintExecutionEventTest {
  @Test
  public void testNullPointerExceptionWhenExecutionIdNull() {
    assertThrows(
        NullPointerException.class, () -> new BlueprintExecutionEvent(null, new Blueprint()));
  }

  @Test
  public void testNullPointerExceptionWhenBlueprintNull() {
    assertThrows(NullPointerException.class, () -> new BlueprintExecutionEvent("test", null));
  }
}
