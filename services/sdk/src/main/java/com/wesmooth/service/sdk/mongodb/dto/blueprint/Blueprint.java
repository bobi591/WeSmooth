/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.mongodb.dto.blueprint;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Blueprint {
  private ObjectId id;

  @BsonProperty("blueprint_name")
  private String name;

  @BsonProperty("blueprint_description")
  private String description;

  @BsonProperty("blueprint_sections")
  private List<BlueprintSection> sections;
}
