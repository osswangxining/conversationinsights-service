package iot.insights.ci.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.ExpressionParameterRepository;
import iot.insights.ci.dao.mongo.IntentExpressionRepository;
import iot.insights.ci.dao.mongo.IntentRepository;
import iot.insights.ci.exception.CIErrorCode;
import iot.insights.ci.exception.CIException;
import iot.insights.ci.model.ExpressionParameter;
import iot.insights.ci.model.Intent;
import iot.insights.ci.model.IntentExpression;

@RestController
public class IntentController {
  @Autowired
  IntentRepository repository;

  @Autowired
  IntentExpressionRepository expressionRepository;

  @Autowired
  ExpressionParameterRepository parameterRepository;

  @RequestMapping(value = "/intents/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Intent getIntent(@PathVariable("id") String id) throws Exception {
    try {
      return repository.findOne(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void delete(@PathVariable("id") String id) throws Exception {
    try {
      repository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents", method = RequestMethod.POST)
  @ResponseBody
  public Intent save(@RequestBody Intent intent) throws Exception {
    try {
      return repository.save(intent);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents/{id}/intents", method = RequestMethod.GET)
  @ResponseBody
  public List<Intent> getIntents(@PathVariable("id") String agentId) throws Exception {
    try {
      // @TODO only return 50
      List<Intent> findByAgentId = repository.findByAgentId(agentId);
      List<Intent> result = new ArrayList<>();
      if (findByAgentId != null) {
        for (int i = 0; i < findByAgentId.size(); i++) {
          if (i < 50) {
            result.add(findByAgentId.get(i));
          }
        }
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents/{id}/intents", method = RequestMethod.POST)
  @ResponseBody
  public Intent save(@RequestBody Intent intent, @PathVariable("id") String agentId) throws Exception {
    try {
      intent.setAgentId(agentId);
      return repository.save(intent);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/expressions", method = RequestMethod.GET)
  @ResponseBody
  public List<IntentExpression> getIntentExpressions(@PathVariable("id") String intentId) throws Exception {
    try {
      // @TODO only return 50
      List<IntentExpression> findByIntentId = expressionRepository.findByIntentId(intentId);
      List<IntentExpression> result = new ArrayList<>();
      if (findByIntentId != null) {
        for (int i = 0; i < findByIntentId.size(); i++) {
          if (i < 50) {
            result.add(findByIntentId.get(i));
          }
        }
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/expressions/parameters", method = RequestMethod.GET)
  @ResponseBody
  public List<IntentExpression> getIntentExpressionsParameters(@PathVariable("id") String intentId) throws Exception {
    try {
      // @TODO only return 50
      List<IntentExpression> findByIntentId = expressionRepository.findByIntentId(intentId);
      List<IntentExpression> result = new ArrayList<>();
      if (findByIntentId != null) {
        for (int i = 0; i < findByIntentId.size(); i++) {
          if (i < 50) {
            IntentExpression intentExpression = findByIntentId.get(i);
            String expressionId = intentExpression.getId();
            List<ExpressionParameter> findByExpressionId = parameterRepository.findByExpressionId(expressionId);
            intentExpression.setParameters(findByExpressionId);
            result.add(intentExpression);
          }
        }
      }

      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/expressions/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteIntentExpressions(@PathVariable("id") String id) throws Exception {
    try {
      expressionRepository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/expressions", method = RequestMethod.POST)
  @ResponseBody
  public IntentExpression saveIntentExpressionsByIntentId(@RequestBody IntentExpression expression,
      @PathVariable("id") String intentId) throws Exception {
    try {
      expression.setIntentId(intentId);
      return expressionRepository.save(expression);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/expressions", method = RequestMethod.POST)
  @ResponseBody
  public IntentExpression saveIntentExpressions(@RequestBody IntentExpression expression) throws Exception {
    try {
      Intent intent = repository.findOne(expression.getIntentId());
      if (intent == null) {
        throw new CIException(CIErrorCode.ITEM_NOT_FOUND);
      }
      return expressionRepository.save(expression);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/parameters", method = RequestMethod.GET)
  @ResponseBody
  public List<ExpressionParameter> getIntentParameters(@PathVariable("id") String intentId) throws Exception {
    try {
      List<IntentExpression> findByIntentId = expressionRepository.findByIntentId(intentId);
      List<ExpressionParameter> result = new ArrayList<>();
      if (findByIntentId != null) {
        findByIntentId.forEach(intentExpression -> {
          List<ExpressionParameter> findByExpressionId = parameterRepository
              .findByExpressionId(intentExpression.getId());
          if (findByExpressionId != null) {
            result.addAll(findByExpressionId);
          }
        });
      }
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/parameters/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteParameters(@PathVariable("id") String id) throws Exception {
    try {
      parameterRepository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/parameters", method = RequestMethod.POST)
  @ResponseBody
  public ExpressionParameter saveIntentParametersByIntentId(@RequestBody ExpressionParameter parameter,
      @PathVariable("id") String intentId) throws Exception {
    try {

      return parameterRepository.save(parameter);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/parameters", method = RequestMethod.POST)
  @ResponseBody
  public ExpressionParameter saveIntentParameters(@RequestBody ExpressionParameter parameter) throws Exception {
    try {

      return parameterRepository.save(parameter);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/parameters/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public ExpressionParameter updateIntentParameter(@RequestBody ExpressionParameter parameter,
      @PathVariable("id") String id) throws Exception {
    try {
      ExpressionParameter findOne = parameterRepository.findOne(id);
      if(findOne != null && id.equals(findOne.getId())) {
        findOne.setEntityId(parameter.getEntityId());
        return parameterRepository.save(findOne);
      } else {
        throw new CIException(CIErrorCode.ITEM_NOT_FOUND);
      }
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/parameters/{id}", method = RequestMethod.GET)
  @ResponseBody
  public ExpressionParameter getParameter(@PathVariable("id") String id) throws Exception {
    try {
      return parameterRepository.findOne(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
