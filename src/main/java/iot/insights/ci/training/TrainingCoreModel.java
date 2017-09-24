/**
 * 
 */
package iot.insights.ci.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Data
@JsonSerialize(using = CustomTrainingCoreModelSerializer.class)
@JsonDeserialize(using = CustomTrainingCoreModelDeserializer.class)
public class TrainingCoreModel implements Serializable {
  /**
  * 
  */
  private static final long serialVersionUID = 7336747529589557092L;

  private List<TrainingCommonModel> commons;
  private List<TrainingEntitySynonym> synonyms;
  private List<TrainingPattern> patterns;

  public TrainingCoreModel() {

  }

  public TrainingCoreModel(List<TrainingCommonModel> commons, List<TrainingEntitySynonym> synonyms,
      List<TrainingPattern> patterns) {
    if (commons == null) {
      this.commons = new ArrayList<TrainingCommonModel>();
    } else {
      this.commons = commons;
    }

    if (synonyms == null) {
      this.synonyms = new ArrayList<TrainingEntitySynonym>();
    } else {
      this.synonyms = synonyms;
    }

    if (patterns == null) {
      this.patterns = new ArrayList<TrainingPattern>();
    } else {
      this.patterns = patterns;
    }
  }
}
