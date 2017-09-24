/**
 * 
 */
package iot.insights.ci.training;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Data
public class TrainingCommonModel implements Serializable {
  /**
  * 
  */
  private static final long serialVersionUID = 7336747529589557092L;

  private String text;
  private String intent;
  private List<TrainingEntity> entities;
}
