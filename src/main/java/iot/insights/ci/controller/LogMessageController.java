package iot.insights.ci.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import iot.insights.ci.dao.mongo.LogMessageRepository;
import iot.insights.ci.model.LogMessage;

@RestController
public class LogMessageController {
  @Autowired
  LogMessageRepository repository;
  @Autowired
  MongoTemplate template;

  @RequestMapping(value = "/nlu_log/{eventType}", method = RequestMethod.GET)
  @ResponseBody
  public List<LogMessage> getLogsByEventType(@PathVariable("eventType") String eventType) throws Exception {
    try {
      // @TODO order by logtime desc LIMIT 100
      return repository.getLogsByEventType(eventType);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/nlu_log", method = { RequestMethod.POST })
  @ResponseBody
  public LogMessage save(@RequestBody LogMessage body) throws Exception {
    try {
      SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
      String logtime = dateFormatLocal.format(new Date());
      body.setLogtime(logtime);
      return repository.save(body);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/dashboard/intent_usage_by_day", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getIntentUsageByDay() throws Exception {
    try {
      // @TODO
      //select * from intent_usage_by_day
      // return repository.getIntentUsageByDay();
      List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
      
      for (int i = 20; i > 10; i--) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("logtime", getPastDate(i));
        item.put("count", new Random().nextInt(100) + 200);
        result.add(item);
      }
      for (int i = 10; i >= 0; i--) {
        Map<String, Object> item = new HashMap<String, Object>();
        item.put("logtime", getPastDate(i));
        item.put("count", new Random().nextInt(1000) + 1000);
        result.add(item);
      }
      
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  @RequestMapping(value = "/dashboard/intent_usage_total", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getIntentUsageStatistics() throws Exception {
    try {
      // @TODO
      // SELECT count(*) AS count FROM nlu_log
      //WHERE nlu_log.event_type::text = 'parse'
      List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
      Map<String, Object> item = new HashMap<String, Object>();
      item.put("count", 2000);
      result.add(item);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/dashboard/request_usage_total", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getRequestUsageStatistics() throws Exception {
    try {
      // @TODO
      // SELECT count(*) AS count FROM nlu_log
      List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
      Map<String, Object> item = new HashMap<String, Object>();
      item.put("count", 10831);
      result.add(item);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  @RequestMapping(value = "/dashboard/avg_intent_usage_by_day", method = RequestMethod.GET)
  @ResponseBody
  public List<Map<String, Object>> getAvgIntentUsageByDay() throws Exception {
    try {
      // @TODO
      // select round(avg(count)) as avg from intent_usage_by_day
      //WHERE nlu_log.event_type::text = 'parse'
      List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
      Map<String, Object> item = new HashMap<String, Object>();
      item.put("avg", 1203);
      result.add(item);
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  
  public static String getPastDate(int past) {
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
    Date today = calendar.getTime();
    SimpleDateFormat format = new SimpleDateFormat("MM/dd");
    String result = format.format(today);

    return result;
  }
}
