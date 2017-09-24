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
public class TrainingEntitySynonym implements Serializable {
  /**
  * 
  */
  private static final long serialVersionUID = 5250014093872431687L;

  private String value;
  private List<String> synonyms;
}
