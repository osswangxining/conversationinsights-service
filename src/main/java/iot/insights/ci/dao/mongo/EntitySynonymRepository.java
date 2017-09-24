package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.EntitySynonym;

public interface EntitySynonymRepository extends MongoRepository<EntitySynonym, String> {
  List<EntitySynonym> findByNameAndEntityId(String name, String entityId);

  List<EntitySynonym> findByEntityId(String entityId);
}
