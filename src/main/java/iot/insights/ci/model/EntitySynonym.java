/**
 * 
 */
package iot.insights.ci.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Document(collection="synonyms")  
@Data
public class EntitySynonym implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 2117219041268695874L;

  @Field("synonym_id")
  @Id
  private String id;

  @Field("synonym_reference")
  private String name;

  @Field("entity_id")
  private String entityId;

  public EntitySynonym() {

  }

  public EntitySynonym(String name, String entityId) {
    this.name = name;
    this.entityId = entityId;
  }
}
