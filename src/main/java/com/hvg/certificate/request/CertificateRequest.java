package com.hvg.certificate.request;

import org.springframework.stereotype.Component;

import java.security.cert.X509Certificate;
import java.util.Date;

@Component
public class CertificateRequest {

  private X509Certificate[] x509Certificates;
  private String filter;
  private Date startDate;
  private Date endDate;

  public CertificateRequest(){}

  public CertificateRequest(X509Certificate[] x509Certificates, String filter, Date startDate, Date endDate) {
    this.x509Certificates = x509Certificates;
    this.filter = filter;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public X509Certificate[] getX509Certificates() {
    return x509Certificates;
  }

  public void setX509Certificates(X509Certificate[] x509Certificates) {
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
