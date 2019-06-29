package com.hvg.certificate.domain;

import java.util.Date;

public class Validity {

  private Date validityStartDateTime;
  private Date validityEndDateTime;

  public Validity(Date validityStartDateTime, Date validityEndDateTime) {
    this.validityStartDateTime = validityStartDateTime;
    this.validityEndDateTime = validityEndDateTime;
  }

  public Date getValidityStartDateTime() {
    return validityStartDateTime;
  }

  public void setValidityStartDateTime(Date validityStartDateTime) {
    this.validityStartDateTime = validityStartDateTime;
  }

  public Date getValidityEndDateTime() {
    return validityEndDateTime;
  }

  public void setValidityEndDateTime(Date validityEndDateTime) {
    this.validityEndDateTime = validityEndDateTime;
  }

  @Override
  public String toString() {
    return "Validity{" +
             "validityStartDateTime=" + validityStartDateTime +
             ", validityEndDateTime=" + validityEndDateTime +
             '}';
  }
}
