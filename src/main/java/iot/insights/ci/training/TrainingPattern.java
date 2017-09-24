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
public class TrainingPattern implements Serializable {
  /**
  * 
  */
  private static final long serialVersionUID = 3801125177903599848L;

  private String name;
  private String pattern;
}
