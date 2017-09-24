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
@Document(collection = "parameters")
@Data
public class ExpressionParameter implements Serializable {

  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = 2117219041268695878L;

  @Field("parameter_id")
  @Id
  private String id;

  @Field("parameter_value")
  private String name;

  @Field("parameter_required")
  private Boolean required = false;

  @Field("parameter_start")
  private int start;

  @Field("parameter_end")
  private int end;

  @Field("entity_id")
  private String entityId;

  @Field("expression_id")
  private String expressionId;

  public ExpressionParameter() {

  }

  public ExpressionParameter(String name, boolean required, int start, int end, String entityId, String expressionId) {
    this.name = name;
    this.required = required;
    this.start = start;
    this.end = end;
    this.entityId = entityId;
    this.expressionId = expressionId;
  }
}
