package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.ExpressionParameter;

public interface ExpressionParameterRepository extends MongoRepository<ExpressionParameter, String> {
  List<ExpressionParameter> findByStartAndEndAndEntityIdAndExpressionId(int start, int end, String entityId,
      String expressionId);

  List<ExpressionParameter> findByExpressionId(String id);

}
