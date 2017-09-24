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
public class CustomTrainingCoreModelSerializer extends StdSerializer<TrainingCoreModel> {

  /**
   * 
   */
  private static final long serialVersionUID = -5107520313550837466L;

  public CustomTrainingCoreModelSerializer() {
    this(null);
  }

  public CustomTrainingCoreModelSerializer(Class<TrainingCoreModel> t) {
    super(t);
  }

  @Override
  public void serialize(TrainingCoreModel model, JsonGenerator generator, SerializerProvider provider)
      throws IOException {
    generator.writeStartObject();
    generator.writeObjectField(Constants.COMMON_ELEMEMT_NAME, model.getCommons());
    generator.writeObjectField(Constants.SYNONYM_ELEMEMT_NAME, model.getSynonyms());
    generator.writeObjectField(Constants.PATTERN_ELEMEMT_NAME, model.getPatterns());
    generator.writeEndObject();
  }

}
