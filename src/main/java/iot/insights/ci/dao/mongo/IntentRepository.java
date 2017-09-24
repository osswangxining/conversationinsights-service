package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.Intent;

public interface IntentRepository extends MongoRepository<Intent, String> {

  List<Intent> findByNameAndAgentId(String name, String agentId);

  List<Intent> findByAgentId(String agentId);

}
