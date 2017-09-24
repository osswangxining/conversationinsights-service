package iot.insights.ci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.ConfigurationRepository;
import iot.insights.ci.model.Configuration;

@RestController
public class ConfigurationController {
  @Autowired
  ConfigurationRepository repository;
  @Autowired  
  MongoTemplate template; 

  @RequestMapping(value = "/settings", method = RequestMethod.GET)
  @ResponseBody
  public List<Configuration> findAll() throws Exception {
    try {
      return repository.findAll();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/settings", method = {RequestMethod.POST, RequestMethod.PUT})
  @ResponseBody
  public Configuration save(@RequestBody Configuration body) throws Exception {
    try {
      Configuration findByKey = repository.findByKey(body.getKey());
      if(findByKey == null) {
        findByKey = body;
      } else {
        findByKey.setValue(body.getValue());
      }
      return repository.save(findByKey);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/settings/{key}", method = RequestMethod.GET)
  @ResponseBody
  public Configuration getAgent(@PathVariable("key") String key) throws Exception {
    try {
      return repository.findByKey(key);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/settings/{key}", method = RequestMethod.PUT)
  @ResponseBody
  public Configuration updateAgent(@RequestBody Configuration body, @PathVariable("key") String key) throws Exception {
    try {
      Configuration findByKey = repository.findByKey(key);
      if(findByKey == null) {
        findByKey = body;
      } else {
        findByKey.setValue(body.getValue());
      }
      return repository.save(findByKey);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  
}
