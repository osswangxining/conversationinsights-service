/**
 * 
 */
package iot.insights.ci.training;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * @author xiningwang
 *
 */
public class CustomTrainingDataModelSerializer extends StdSerializer<TrainingDataModel> {

  /**
   * 
   */
  private static final long serialVersionUID = -8380332658788846489L;

  public CustomTrainingDataModelSerializer() {
    this(null);
  }

  public CustomTrainingDataModelSerializer(Class<TrainingDataModel> t) {
    super(t);
  }

  @Override
  public void serialize(TrainingDataModel model, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeStartObject();
    generator.writeObjectField(Constants.ROOT_ELEMEMT_NAME, model.getRoot());
    generator.writeEndObject();
  }

}
