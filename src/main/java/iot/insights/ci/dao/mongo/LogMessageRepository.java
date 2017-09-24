package iot.insights.ci.dao.mongo;

import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.LogMessage;

public interface LogMessageRepository extends MongoRepository<LogMessage, String> {


  List<LogMessage> getLogsByEventType(String eventType);


}
