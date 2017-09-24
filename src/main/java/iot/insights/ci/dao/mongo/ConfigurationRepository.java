package iot.insights.ci.dao.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import iot.insights.ci.model.Configuration;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {

  Configuration findByKey(String key);

}
