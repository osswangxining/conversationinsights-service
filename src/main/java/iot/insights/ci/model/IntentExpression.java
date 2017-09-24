/**
 * 
 */
package iot.insights.ci.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Document(collection = "expressions")
@Data
public class IntentExpression implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 2117219041268695873L;

  @Field("expression_id")
  @Id
  private String id;

  @Field("expression_text")
  private String name;

  @Field("intent_id")
  private String intentId;
  
  @Transient
  private List<ExpressionParameter> parameters = new ArrayList<>();
  
  public IntentExpression() {

  }

  public IntentExpression(String name, String intentId) {
    this.name = name;
    this.intentId = intentId;
  }
}
