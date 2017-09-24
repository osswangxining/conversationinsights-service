/**
 * 
 */
package iot.insights.ci.training;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author xiningwang
 *
 */
public class CustomTrainingCoreModelDeserializer extends StdDeserializer<TrainingCoreModel> {

  /**
   * 
   */
  private static final long serialVersionUID = -3597522139982206858L;

  public CustomTrainingCoreModelDeserializer() {
    this(null);
  }

  public CustomTrainingCoreModelDeserializer(Class<TrainingCoreModel> t) {
    super(t);
  }

  @Override
  public TrainingCoreModel deserialize(JsonParser parser, DeserializationContext ctx)
      throws IOException, JsonProcessingException {
    ObjectMapper mapper = (ObjectMapper) parser.getCodec();
    ObjectNode node = (ObjectNode) mapper.readTree(parser);

    JsonNode jsonNode = node.get(Constants.COMMON_ELEMEMT_NAME);
    String commonsAsString = mapper.writeValueAsString(jsonNode);
    JavaType commonsClassCollection = mapper.getTypeFactory().constructCollectionType(List.class,
        TrainingCommonModel.class);
    List<TrainingCommonModel> commons = mapper.readValue(commonsAsString, commonsClassCollection);

    jsonNode = node.get(Constants.SYNONYM_ELEMEMT_NAME);
    String synonymsAsString = mapper.writeValueAsString(jsonNode);
    JavaType synonymsClassCollection = mapper.getTypeFactory().constructCollectionType(List.class,
        TrainingEntitySynonym.class);
    List<TrainingEntitySynonym> synonyms = mapper.readValue(synonymsAsString, synonymsClassCollection);

    jsonNode = node.get(Constants.SYNONYM_ELEMEMT_NAME);
    String patternsAsString = mapper.writeValueAsString(jsonNode);
    JavaType patternsClassCollection = mapper.getTypeFactory().constructCollectionType(List.class,
        TrainingPattern.class);
    List<TrainingPattern> patterns = mapper.readValue(patternsAsString, patternsClassCollection);

    return new TrainingCoreModel(commons, synonyms, patterns);
  }

}
