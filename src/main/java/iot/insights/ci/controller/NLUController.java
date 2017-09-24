package iot.insights.ci.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.common.base.Joiner;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;

import iot.insights.ci.dao.mongo.EntitySynonymRepository;
import iot.insights.ci.dao.mongo.EntitySynonymVariantRepository;
import iot.insights.ci.dao.mongo.ExpressionParameterRepository;
import iot.insights.ci.dao.mongo.IntentEntityRepository;
import iot.insights.ci.dao.mongo.IntentExpressionRepository;
import iot.insights.ci.dao.mongo.IntentRepository;
import iot.insights.ci.model.EntitySynonym;
import iot.insights.ci.model.EntitySynonymVariant;
import iot.insights.ci.model.ExpressionParameter;
import iot.insights.ci.model.Intent;
import iot.insights.ci.model.IntentEntity;
import iot.insights.ci.model.IntentExpression;
import iot.insights.ci.training.TrainingCommonModel;
import iot.insights.ci.training.TrainingCoreModel;
import iot.insights.ci.training.TrainingDataModel;
import iot.insights.ci.training.TrainingDataProcessor;
import iot.insights.ci.training.TrainingEntity;
import iot.insights.ci.training.TrainingEntitySynonym;

@RestController
public class NLUController {

  @Value("${nlu.server.url}")
  private String nluServerUrl;

  @Autowired
  IntentRepository intentRepository;
  @Autowired
  IntentExpressionRepository intentExpressionRepository;
  @Autowired
  IntentEntityRepository intentEntityRepository;
  @Autowired
  EntitySynonymRepository entitySynonymRepository;
  @Autowired
  EntitySynonymVariantRepository entitySynonymVariantRepository;
  @Autowired
  ExpressionParameterRepository expressionParameterRepository;

  static {
    Unirest.setObjectMapper(new ObjectMapper() {
      private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

      public <T> T readValue(String value, Class<T> valueType) {
        try {
          return jacksonObjectMapper.readValue(value, valueType);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }

      public String writeValue(Object value) {
        try {
          return jacksonObjectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }

  @RequestMapping(value = "/config", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getConfig() throws Exception {
    try {
      HttpResponse<String> response = Unirest.get(nluServerUrl + "/config").asString();
      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getStatus() throws Exception {
    try {
      HttpResponse<String> response = Unirest.get(nluServerUrl + "/status").asString();
      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/version", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getVersion() throws Exception {
    try {
      JSONObject myString = new JSONObject();
      myString.put("version", "0.1");
      return myString.toString();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/parse", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String parse(@RequestBody ParsingData data) throws Exception {
    try {
      System.out.println("data:" + data);
      HttpResponse<String> response = Unirest.post(nluServerUrl + "/parse").header("accept", "text/plain")
          .header("Content-Type", "text/plain").body(data).asString();
      System.out.println(response.getBody());
      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agent/{agentId}/model", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public String getTrainingModel(@PathVariable("agentId") String agentId) throws Exception {
    try {
      String modelString = getModelString(agentId);
      return modelString;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agent/{agentId}/train", method = RequestMethod.POST, produces = "application/json")
  @ResponseBody
  public String traing(@PathVariable("agentId") String agentId) throws Exception {
    try {
      String modelString = getModelString(agentId);

      HttpResponse<String> response = Unirest.post(nluServerUrl + "/train").header("accept", "text/plain")
          .header("Content-Type", "text/plain").body(modelString).asString();
      return response.getBody();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  private String getModelString(String agentId) throws JsonGenerationException, JsonMappingException, IOException {
    List<TrainingCommonModel> commons = new ArrayList<>();
    List<TrainingEntitySynonym> synonyms = new ArrayList<>();
    Set<String> usedEntityIdSet = new HashSet<>();
    List<Intent> intents = intentRepository.findByAgentId(agentId);

    intents.forEach(intent -> {
      List<IntentExpression> expressions = intentExpressionRepository.findByIntentId(intent.getId());
      expressions.forEach(expression -> {
        TrainingCommonModel commonModel = new TrainingCommonModel();
        commons.add(commonModel);

        commonModel.setIntent(intent.getName());
        commonModel.setText(expression.getName());
        List<TrainingEntity> entities = new ArrayList<TrainingEntity>();
        commonModel.setEntities(entities);

        List<ExpressionParameter> parameters = expressionParameterRepository.findByExpressionId(expression.getId());
        parameters.forEach(parameter -> {
          TrainingEntity entity = new TrainingEntity();
          entity.setStart(parameter.getStart());
          entity.setEnd(parameter.getEnd());
          entity.setValue(parameter.getName());
          String entityId = parameter.getEntityId();
          if (entityId != null) {
            IntentEntity findOneEntity = intentEntityRepository.findOne(entityId);
            if (findOneEntity != null) {
              entity.setEntity(findOneEntity.getName());
            }
            entities.add(entity);
            usedEntityIdSet.add(entityId);
          }

        });
      });
    });// end for intets
    if (usedEntityIdSet != null) {
      System.out.println(Joiner.on('\n').join(usedEntityIdSet));
    }
    usedEntityIdSet.forEach(entityId -> {
      List<EntitySynonym> entitySynonyms = entitySynonymRepository.findByEntityId(entityId);
      System.out.println(Joiner.on('\n').join(entitySynonyms));
      entitySynonyms.forEach(entitySynonym -> {
        TrainingEntitySynonym syno = new TrainingEntitySynonym();
        syno.setValue(entitySynonym.getName());

        List<String> synonymAsStringList = new ArrayList<>();
        syno.setSynonyms(synonymAsStringList);

        List<EntitySynonymVariant> synonymVariants = entitySynonymVariantRepository
            .findBySynonymId(entitySynonym.getId());
        synonymVariants.forEach(synonymVariant -> {
          if (!syno.getValue().equals(synonymVariant.getName())) {
            synonymAsStringList.add(synonymVariant.getName());
          }
        });
        if (!synonymAsStringList.isEmpty()) {
          synonyms.add(syno);
        }
      });
    });

    TrainingCoreModel root = new TrainingCoreModel(commons, synonyms, null);
    TrainingDataModel model = new TrainingDataModel(root);
    String data = TrainingDataProcessor.toString(model);
    return data;
  }
}
