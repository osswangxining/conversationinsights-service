/**
 * 
 */
package iot.insights.ci.controller;

import java.io.Serializable;

import lombok.Data;

/**
 * @author xiningwang
 *
 */
@Data
public class ParsingData implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7896579956536328520L;

  private String q;
  private String model;
  private String time;
}
