package com.hvg.certificate.request;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class CertificateRequestParamModel {

  private MultipartFile certFile;
  private String encodingType;
  private String filter;
  private String hostName;
  private String startDate;
  private String endDate;

  public CertificateRequestParamModel(){

  }

  public CertificateRequestParamModel(MultipartFile certFile, String encodingType, String filter,
                                      String startDate, String endDate) {
    this.certFile = certFile;
    this.encodingType = encodingType;
    this.filter = filter;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public CertificateRequestParamModel(MultipartFile certFile, String encodingType, String filter, String hostName,
                                      String startDate, String endDate) {
    this.certFile = certFile;
    this.encodingType = encodingType;
    this.filter = filter;
    this.hostName = hostName;
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public MultipartFile getCertFile() {
    return certFile;
  }

  public void setCertFile(MultipartFile certFile) {
    this.certFile = certFile;
  }

  public String getEncodingType() {
    return encodingType;
  }

  public void setEncodingType(String encodingType) {
    this.encodingType = encodingType;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }
}
