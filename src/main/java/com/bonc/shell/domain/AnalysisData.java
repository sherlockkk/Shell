package com.bonc.shell.domain;

public class AnalysisData {
  /**
   * 开始时间
   */
  private Long startTime;
  /**
   * 结束时间
   */
  private Long endTime;
  /**
   * 测点编码集
   */
  private String pointCodes;
  /**
   * 用户sessionId
   */
  private String sessionId;

  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public String getPointCodes() {
    return pointCodes;
  }

  public void setPointCodes(String pointCodes) {
    this.pointCodes = pointCodes;
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  @Override
  public String toString() {
    return "AnalysisData{" +
        "startTime=" + startTime +
        ", endTime=" + endTime +
        ", pointCodes='" + pointCodes + '\'' +
        ", sessionId='" + sessionId + '\'' +
        '}';
  }
}
