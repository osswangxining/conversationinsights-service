package iot.insights.ci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.IntentEntityRepository;
import iot.insights.ci.model.IntentEntity;

@RestController
public class EntityController {
  @Autowired
  IntentEntityRepository repository;

  @RequestMapping(value = "/entities", method = RequestMethod.GET)
  @ResponseBody
  public List<IntentEntity> findAll() throws Exception {
    try {
      return repository.findAll();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/entities", method = RequestMethod.POST)
  @ResponseBody
  public IntentEntity save(@RequestBody IntentEntity body) throws Exception {
    try {
      return repository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/entities/{id}", method = RequestMethod.GET)
  @ResponseBody
  public IntentEntity get(@PathVariable("id") String id) throws Exception {
    try {
      return repository.findOne(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/entities/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public IntentEntity update(@RequestBody IntentEntity body, @PathVariable("id") String id) throws Exception {
    try {
      body.setId(id);
      return repository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/entities/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void delete(@PathVariable("id") String id) throws Exception {
    try {
      repository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/intents/{id}/unique_intent_entities", method = RequestMethod.GET)
  @ResponseBody
  public IntentEntity getIntentEntity(@PathVariable("id") String intentId) throws Exception {
    try {
      return repository.findOne(intentId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

}
