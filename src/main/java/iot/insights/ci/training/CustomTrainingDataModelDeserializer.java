/**
 * 
 */
package iot.insights.ci.training;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author xiningwang
 *
 */
public class CustomTrainingDataModelDeserializer extends StdDeserializer<TrainingDataModel> {

  /**
   * 
   */
  private static final long serialVersionUID = -6436199660017155333L;

  public CustomTrainingDataModelDeserializer() {
    this(null);
  }

  public CustomTrainingDataModelDeserializer(Class<TrainingDataModel> t) {
    super(t);
  }

  @Override
  public TrainingDataModel deserialize(JsonParser parser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    ObjectMapper mapper = (ObjectMapper) parser.getCodec();
    ObjectNode node = (ObjectNode) mapper.readTree(parser);

    JsonNode jsonNode = node.get(Constants.ROOT_ELEMEMT_NAME);
    String writeValueAsString = mapper.writeValueAsString(jsonNode);
    TrainingCoreModel coreModel = mapper.readValue(writeValueAsString, TrainingCoreModel.class);
    return new TrainingDataModel(coreModel);
  }

}
