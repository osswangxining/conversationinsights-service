package iot.insights.ci.training;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Data;

@Data
@JsonSerialize(using = CustomTrainingDataModelSerializer.class)
@JsonDeserialize(using = CustomTrainingDataModelDeserializer.class)
public class TrainingDataModel implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1664362900351125091L;

  private TrainingCoreModel root;

  public TrainingDataModel() {

  }

  public TrainingDataModel(TrainingCoreModel root) {
    this.root = root;
  }

}
