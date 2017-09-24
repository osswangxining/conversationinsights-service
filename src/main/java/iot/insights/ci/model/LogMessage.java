/**
 * 
 */
package iot.insights.ci.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Document(collection = "logs")
@Data
public class LogMessage implements Serializable {


  /**
   * 
   */
  @Transient
  private static final long serialVersionUID = -1001363955676217584L;

  @Field("log_id")
  @Id
  private String id;

  @Field("logtime")
  private String logtime;
  
  @Field("ip_address")
  private String ipAddress;
  
  @Field("query")
  private String query;
  
  @Field("event_data")
  private String eventData;
  
  @Field("event_type")
  private String eventType;

  public LogMessage() {
    
  }
  
  
}
