package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.Agent;

public interface AgentRepository extends MongoRepository<Agent, String> {

  List<Agent> findByName(String name);

}
