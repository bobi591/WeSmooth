/* WeSmooth! 2024 */
package com.wesmooth.service.sdk.mongodb.dto.blueprint;

import com.wesmooth.service.sdk.groovy.IGroovyContainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlueprintSection implements IGroovyContainer<String> {
  @BsonProperty("blueprint_section_name")
  private String name;

  @BsonProperty("blueprint_section_description")
  private String description;

  @BsonProperty("blueprint_groovy_code")
  private String groovyCode;
}
