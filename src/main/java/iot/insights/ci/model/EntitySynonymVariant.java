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
@Document(collection = "synonym_variant")
@Data
public class EntitySynonymVariant implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 2117219041268695875L;

  @Field("synonym_variant_id")
  @Id
  private String id;

  @Field("synonym_value")
  private String name;

  @Field("synonym_id")
  private String synonymId;

  public EntitySynonymVariant() {

  }

  public EntitySynonymVariant(String name, String synonymId) {
    this.name = name;
    this.synonymId = synonymId;
  }
}
