package com.hvg.certificate.request;

import java.security.cert.X509Certificate;
import java.util.Date;

public class CertificateRequest {

  private X509Certificate[] x509Certificates;
  private String filter;
  private Date startDate;
  private Date endDate;

  public CertificateRequest(X509Certificate[] x509Certificates, String filter, Date startDate, Date endDate) {
    this.x509Certificates = x509Certificates;
    this.filter = filter;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public X509Certificate[] getX509Certificate() {
    return x509Certificates;
  }

  public void setX509Certificate(X509Certificate[] x509Certificates) {
    this.x509Certificates = x509Certificates;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
}
