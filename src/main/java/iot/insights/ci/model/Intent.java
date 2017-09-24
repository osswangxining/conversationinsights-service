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
@Document(collection = "intents")
@Data
public class Intent implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 2117219041268695872L;

  @Field("intent_id")
  @Id
  private String id;

  @Field("intent_name")
  private String name;

  @Field("agent_id")
  private String agentId;

  public Intent() {

  }

  public Intent(String name, String agentId) {
    this.name = name;
    this.agentId = agentId;
  }
}
