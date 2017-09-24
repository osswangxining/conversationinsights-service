/**
 * 
 */
package iot.insights.ci.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Document(collection = "configurations")
@Data
public class Configuration implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 6341603972996978477L;

  @Field("config_id")
  @Id
  private String id;
  
  @Field("key")
  @Indexed(unique = true)
  private String key;

  @Field("value")
  private String value;

  public Configuration() {
    
  }
  
  public Configuration(String key, String value) {
    this.key = key;
    this.value = value;
  }
}
