package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.IntentExpression;

public interface IntentExpressionRepository extends MongoRepository<IntentExpression, String> {
  List<IntentExpression> findByNameAndIntentId(String name, String intentId);

  List<IntentExpression> findByIntentId(String id);

}
