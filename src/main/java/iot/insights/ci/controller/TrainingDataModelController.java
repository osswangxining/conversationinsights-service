package iot.insights.ci.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;

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

@RestController
public class TrainingDataModelController {
  public final static String JOIN_TOKEN = "_";

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

  @RequestMapping(value = "/trainingdatamodel/agent/{agentId}", method = RequestMethod.POST)
  @ResponseBody
  public void save(@RequestBody String content, @PathVariable("agentId") String agentId) throws Exception {
    try {
      TrainingDataModel model = TrainingDataProcessor.get(content);
      TrainingCoreModel root = model.getRoot();
      List<TrainingCommonModel> commons = root.getCommons();
      Map<String, String> intentIdsMap = new HashMap<>();
      Map<String, String> entityIdsMap = new HashMap<>();
      Map<String, String> synonymIdsMap = new HashMap<>();
      Set<String> entitySet = new HashSet<>();
      Map<String, Set<String>> entityNameAndValuesMap = new HashMap<>();
      Map<String, Set<String>> entityNameAndVariantsMap = new HashMap<>();
      Map<String, String> parametersMap = new HashMap<>();

      if (commons != null) {
        for (TrainingCommonModel trainingCommonModel : commons) {
          String _intent = trainingCommonModel.getIntent();
          List<Intent> _intents = intentRepository.findByNameAndAgentId(_intent, agentId);
          Intent savedIntent;
          if (_intents.isEmpty()) {
            Intent intent = new Intent(_intent, agentId);
            savedIntent = intentRepository.save(intent);
          } else {
            savedIntent = _intents.get(0);
          }
          intentIdsMap.put(savedIntent.getName(), savedIntent.getId());

          String text = trainingCommonModel.getText();
          List<IntentExpression> findByNameAndIntentId = intentExpressionRepository.findByNameAndIntentId(text,
              savedIntent.getId());
          IntentExpression savedIntentExpression;
          if (findByNameAndIntentId.isEmpty()) {
            IntentExpression expression = new IntentExpression(text, savedIntent.getId());
            savedIntentExpression = intentExpressionRepository.save(expression);
          } else {
            savedIntentExpression = findByNameAndIntentId.get(0);
          }

          List<TrainingEntity> entities = trainingCommonModel.getEntities();
          if (entities != null) {
            for (TrainingEntity trainingEntity : entities) {
              String entity = trainingEntity.getEntity();
              entitySet.add(entity);

              Set<String> valuesSet = entityNameAndValuesMap.get(entity);
              if (valuesSet == null) {
                valuesSet = new HashSet<String>();
              }
              String value = trainingEntity.getValue();
              if (value != null && !value.trim().isEmpty()) {
                valuesSet.add(value);
              }
              entityNameAndValuesMap.put(entity, valuesSet);

              int start = trainingEntity.getStart();
              int end = trainingEntity.getEnd();
              if (start >= 0 && end > start && text != null && end < text.length()) {
                String synonymVar = text.substring(start, end);

                Set<String> synonymVarSet = entityNameAndVariantsMap.get(entity);
                if (synonymVarSet == null) {
                  synonymVarSet = new HashSet<String>();
                }
                synonymVarSet.add(synonymVar);
                entityNameAndVariantsMap.put(entity, synonymVarSet);

                // key: start_end_expressionId_entityName
                // value: synonymVar
                String synonymVarKey = start + JOIN_TOKEN + end + JOIN_TOKEN + savedIntentExpression.getId()
                    + JOIN_TOKEN + entity;
                parametersMap.put(synonymVarKey, synonymVar);
              }
            }
          }
        } // end for root loop
        System.out.println("intentIdsMap::::::::::");
        System.out.println(Joiner.on('\n').withKeyValueSeparator(" -> ").join(intentIdsMap));

        // populate entitySet
        for (String entityName : entitySet) {
          List<IntentEntity> findByName = intentEntityRepository.findByName(entityName);
          if (findByName.isEmpty()) {
            IntentEntity entity = new IntentEntity(entityName);
            IntentEntity savedIntentEntity = intentEntityRepository.save(entity);
            entityIdsMap.put(entityName, savedIntentEntity.getId());
          } else {
            entityIdsMap.put(entityName, findByName.get(0).getId());
          }
        }
        System.out.println("entityIdsMap::::::::::");
        System.out.println(Joiner.on('\n').withKeyValueSeparator(" -> ").join(entityIdsMap));

        // populate entityNameAndValuesMap
        entityNameAndValuesMap.forEach((k, v) -> {
          String entityId = entityIdsMap.get(k);
          if (entityId == null) {
            System.out.println("entityId: null:" + k);
          } else {
            v.forEach(synonym -> {
              List<EntitySynonym> findByNameAndEntityId = entitySynonymRepository.findByNameAndEntityId(synonym,
                  entityId);
              if (findByNameAndEntityId.isEmpty()) {
                EntitySynonym entitySynonym = new EntitySynonym(synonym, entityId);
                EntitySynonym savedEntitySynonym = entitySynonymRepository.save(entitySynonym);
                synonymIdsMap.put(entityId + JOIN_TOKEN + synonym, savedEntitySynonym.getId());
              } else {
                synonymIdsMap.put(entityId + JOIN_TOKEN + synonym, findByNameAndEntityId.get(0).getId());
              }
            });
          }
        });
        System.out.println("synonymIdsMap::::::::::");
        System.out.println(Joiner.on('\n').withKeyValueSeparator(" -> ").join(synonymIdsMap));

        // populate entityNameAndVariantsMap
        entityNameAndVariantsMap.forEach((k, v) -> {
          String entityId = entityIdsMap.get(k);
          if (entityId == null) {
            System.out.println("entityId: null:" + k);
          } else {
            v.forEach(synonymVar -> {
              String synonymId = synonymIdsMap.get(entityId + JOIN_TOKEN + synonymVar);
              if (synonymId == null) {
                System.out.println("entityId_synonymVar:" + entityId + JOIN_TOKEN + synonymVar);
              } else {
                List<EntitySynonymVariant> findByNameAndSynonymId = entitySynonymVariantRepository
                    .findByNameAndSynonymId(synonymVar, synonymId);
                if (findByNameAndSynonymId.isEmpty()) {
                  EntitySynonymVariant entitySynonymVariant = new EntitySynonymVariant(synonymVar, synonymId);
                  entitySynonymVariantRepository.save(entitySynonymVariant);
                }
              }
            });
          }
        });

        // populate parametersMap
        // key: start_end_expressionId_entityName
        // value: synonymVar
        parametersMap.forEach((k, v) -> {
          Splitter omitEmptyStrings = Splitter.on(JOIN_TOKEN).omitEmptyStrings();
          List<String> splitToList = omitEmptyStrings.splitToList(k);
          if (splitToList != null && splitToList.size() >= 4) {
            boolean required = false;
            int start = Integer.parseInt(splitToList.get(0));
            int end = Integer.parseInt(splitToList.get(1));
            String expressionId = (splitToList.get(2));
            String entityId = entityIdsMap.get(splitToList.get(3));
            if (!Strings.isNullOrEmpty(entityId)) {
              List<ExpressionParameter> findByStartAndEndAndEntityIdAndExpressionId = expressionParameterRepository
                  .findByStartAndEndAndEntityIdAndExpressionId(start, end, entityId, expressionId);
              if (findByStartAndEndAndEntityIdAndExpressionId.isEmpty()) {
                ExpressionParameter parameter = new ExpressionParameter(v, required, start, end, entityId,
                    expressionId);
                expressionParameterRepository.save(parameter);
              }
            } else {
              System.out.println("########Warning:[entityId: " + entityId + "]");
            }
          }
        });
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
