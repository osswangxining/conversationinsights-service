package iot.insights.ci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.EntitySynonymRepository;
import iot.insights.ci.dao.mongo.EntitySynonymVariantRepository;
import iot.insights.ci.model.EntitySynonym;
import iot.insights.ci.model.EntitySynonymVariant;

@RestController
public class SynonymController {
  @Autowired
  EntitySynonymRepository repository;
  @Autowired
  EntitySynonymVariantRepository variantRepository;

  @RequestMapping(value = "/synonyms", method = RequestMethod.POST)
  @ResponseBody
  public EntitySynonym save(@RequestBody EntitySynonym body) throws Exception {
    try {
      return repository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/synonyms/{id}", method = RequestMethod.GET)
  @ResponseBody
  public EntitySynonym get(@PathVariable("id") String id) throws Exception {
    try {
      return repository.findOne(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/synonyms/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public EntitySynonym update(@RequestBody EntitySynonym body, @PathVariable("id") String id) throws Exception {
    try {
      body.setId(id);
      return repository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/synonyms/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void delete(@PathVariable("id") String id) throws Exception {
    try {
      repository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

 

  @RequestMapping(value = "/entities/{entityId}/synonyms", method = RequestMethod.GET)
  @ResponseBody
  public List<EntitySynonym> getSynonyms(@PathVariable("entityId") String entityId) throws Exception {
    try {
      return repository.findByEntityId(entityId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/synonyms/{synonymId}/variants", method = RequestMethod.GET)
  @ResponseBody
  public List<EntitySynonymVariant> getEntitySynonymVariants(@PathVariable("synonymId") String synonymId) throws Exception {
    try {
      return variantRepository.findBySynonymId(synonymId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/synonyms/{synonymId}/variants", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteEntitySynonymVariants(@PathVariable("synonymId") String synonymId) throws Exception {
    try {
      variantRepository.deleteBySynonymId(synonymId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/variants", method = RequestMethod.POST)
  @ResponseBody
  public EntitySynonymVariant saveEntitySynonymVariant(@RequestBody EntitySynonymVariant body) throws Exception {
    try {
      return variantRepository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/variants/{id}", method = RequestMethod.GET)
  @ResponseBody
  public EntitySynonymVariant getEntitySynonymVariant(@PathVariable("id") String id) throws Exception {
    try {
      return variantRepository.findOne(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/variants/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteEntitySynonymVariant(@PathVariable("id") String id) throws Exception {
    try {
      variantRepository.delete(id);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
