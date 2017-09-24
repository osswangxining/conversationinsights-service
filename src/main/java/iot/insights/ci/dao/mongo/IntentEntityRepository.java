package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.IntentEntity;

public interface IntentEntityRepository extends MongoRepository<IntentEntity, String> {
  List<IntentEntity> findByName(String name);
}
