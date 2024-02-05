/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import com.wesmooth.service.sdk.mongodb.dto.Blueprint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/** The Blueprint Execution Event. */
@Setter
@Getter
@NoArgsConstructor
public class BlueprintExecutionEvent {
  private String executionId;
  private Blueprint blueprint;

  public BlueprintExecutionEvent(
      @NonNull final String executionId, @NonNull final Blueprint blueprint) {
    this.executionId = executionId;
    this.blueprint = blueprint;
  }
}
