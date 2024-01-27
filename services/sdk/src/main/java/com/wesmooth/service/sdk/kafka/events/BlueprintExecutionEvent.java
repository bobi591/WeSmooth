/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.kafka.events;

import com.wesmooth.service.sdk.mongodb.dto.Blueprint;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class BlueprintExecutionEvent {
  private EventStatus eventStatus;
  private String executionId;
  private Blueprint blueprint;

  public BlueprintExecutionEvent(
      final EventStatus eventStatus, final String executionId, final Blueprint blueprint) {
    this.executionId = executionId;
    this.blueprint = blueprint;
  }

  public EventStatus getEventStatus() {
    return eventStatus;
  }

  public String getExecutionId() {
    return executionId;
  }

  public Blueprint getBlueprint() {
    return blueprint;
  }
}
