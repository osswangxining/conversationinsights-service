package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.EntitySynonymVariant;

public interface EntitySynonymVariantRepository extends MongoRepository<EntitySynonymVariant, String> {
  List<EntitySynonymVariant> findByNameAndSynonymId(String name, String synonymId);

  List<EntitySynonymVariant> findBySynonymId(String synonymId);
  
  void deleteBySynonymId(String synonymId);
}
