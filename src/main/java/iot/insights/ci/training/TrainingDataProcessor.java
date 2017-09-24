/**
 * 
 */
package iot.insights.ci.training;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author xiningwang
 *
 */
public class TrainingDataProcessor {

  public static TrainingDataModel get(String content) throws JsonParseException, JsonMappingException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    return objectMapper.readValue(content, TrainingDataModel.class);
  }

  public static String toString(TrainingDataModel model)
      throws JsonGenerationException, JsonMappingException, IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    StringWriter sw = new StringWriter();
    objectMapper.writeValue(sw, model);
    return sw.toString();
  }

  /**
   * @param args
   */
//  public static void main(String[] args) {
//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//    objectMapper.setSerializationInclusion(Include.NON_NULL);
//
//    List<TrainingCommonModel> commons = new ArrayList<TrainingCommonModel>();
//    List<TrainingPattern> patterns = new ArrayList<TrainingPattern>();
//    List<TrainingEntitySynonym> synonyms = new ArrayList<TrainingEntitySynonym>();
//    TrainingCoreModel root = new TrainingCoreModel(commons, synonyms, patterns);
//    TrainingDataModel model = new TrainingDataModel(root);
//
//    StringWriter sw = new StringWriter();
//    try {
//      objectMapper.writeValue(sw, model);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    System.out.println(sw.toString());
//    System.out.println("------------------------------");
//
//    try {
//      TrainingDataModel readValue = objectMapper.readValue(new File(
//          "/Users/xiningwang/Documents/IoTWork/sts-bundle/workspace/ConversationalInsightsService/src/test/java/franken_data.json"),
//          TrainingDataModel.class);
//      System.out.println(readValue.toString());
//      objectMapper.writeValue(System.out, readValue);
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//  }

}
