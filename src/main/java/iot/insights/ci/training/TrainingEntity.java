/**
 * 
 */
package iot.insights.ci.training;

import java.io.Serializable;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Data
public class TrainingEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -5459289700883575194L;

  private String entity;
  private String value;
  private int start;
  private int end;
}
