package iot.insights.ci.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.AgentRepository;
import iot.insights.ci.model.Agent;

@RestController
public class AgentController {
  @Autowired
  AgentRepository repository;

  @RequestMapping(value = "/agents", method = RequestMethod.GET)
  @ResponseBody
  public List<Agent> findAll() throws Exception {
    try {
      return repository.findAll();
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents", method = RequestMethod.POST)
  @ResponseBody
  public Agent save(@RequestBody Agent agent) throws Exception {
    try {
      return repository.save(agent);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Agent getAgent(@PathVariable("id") String agentId) throws Exception {
    try {
      return repository.findOne(agentId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public Agent updateAgent(@RequestBody Agent agent, @PathVariable("id") String agentId) throws Exception {
    try {
      agent.setId(agentId);
      return repository.save(agent);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/agents/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public void deleteAgent(@PathVariable("id") String agentId) throws Exception {
    try {
      repository.delete(agentId);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
}
