/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import com.wesmooth.service.sdk.mongodb.dto.blueprint.BlueprintSection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

/** The Blueprint Section Execution Event. */
@Setter
@Getter
@NoArgsConstructor
public class BlueprintSectionExecutionEvent {
  private EventStatus eventStatus;
  private String executionId;
  private BlueprintSection blueprintSection;

  public BlueprintSectionExecutionEvent(
      @NonNull final EventStatus eventStatus,
      @NonNull final String executionId,
      @NonNull final BlueprintSection blueprintSection) {
    this.eventStatus = eventStatus;
    this.executionId = executionId;
    this.blueprintSection = blueprintSection;
  }
}
