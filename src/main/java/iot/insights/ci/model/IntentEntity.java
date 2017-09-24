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
@Document(collection = "entities")
@Data
public class IntentEntity implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = -5192355397220265199L;

  @Field("entity_id")
  @Id
  private String id;

  @Field("entity_name")
  private String name;

  public IntentEntity() {

  }

  public IntentEntity(String name) {
    this.name = name;
  }
}
