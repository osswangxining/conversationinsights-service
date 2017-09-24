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
@Document(collection = "agents")
@Data
public class Agent implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = -4995535590854311378L;

  @Field("agent_id")
  @Id
  private String id;

  @Field("agent_name")
  private String name;

  public Agent() {
    
  }
  
  public Agent(String name) {
    this.name = name;
  }
}
