package com.hvg.certificate.domain;

public class CertificateInfo {

  private String certificateType;
  private String subjectDN;
  private String issuerDN;
  private String subjectPrincipal;
  private String issuerPrincipal;
  private Validity validity;
  private AdditionalProperties additionalProperties;

  public CertificateInfo(String certificateType, String subjectDN, String issuerDN, String subjectPrincipal,
                         String issuerPrincipal, Validity validity,
                         AdditionalProperties additionalProperties) {
    this.certificateType = certificateType;
    this.subjectDN = subjectDN;
    this.issuerDN = issuerDN;
    this.subjectPrincipal = subjectPrincipal;
    this.issuerPrincipal = issuerPrincipal;
    this.validity = validity;
    this.additionalProperties = additionalProperties;
  }

  public String getCertificateType() {
    return certificateType;
  }

  public void setCertificateType(String certificateType) {
    this.certificateType = certificateType;
  }

  public String getSubjectDN() {
    return subjectDN;
  }

  public void setSubjectDN(String subjectDN) {
    this.subjectDN = subjectDN;
  }

  public String getIssuerDN() {
    return issuerDN;
  }

  public void setIssuerDN(String issuerDN) {
    this.issuerDN = issuerDN;
  }

  public String getSubjectPrincipal() {
    return subjectPrincipal;
  }

  public void setSubjectPrincipal(String subjectPrincipal) {
    this.subjectPrincipal = subjectPrincipal;
  }

  public String getIssuerPrincipal() {
    return issuerPrincipal;
  }

  public void setIssuerPrincipal(String issuerPrincipal) {
    this.issuerPrincipal = issuerPrincipal;
  }

  public Validity getValidity() {
    return validity;
  }

  public void setValidity(Validity validity) {
    this.validity = validity;
  }

  public AdditionalProperties getAdditionalProperties() {
    return additionalProperties;
  }

  public void setAdditionalProperties(AdditionalProperties additionalProperties) {
    this.additionalProperties = additionalProperties;
  }

  @Override
  public String toString() {
    return "CertificateInfo{" +
             "certificateType='" + certificateType + '\'' +
             ", subjectDN='" + subjectDN + '\'' +
             ", issuerDN='" + issuerDN + '\'' +
             ", subjectPrincipal='" + subjectPrincipal + '\'' +
             ", issuerPrincipal='" + issuerPrincipal + '\'' +
             ", validity=" + validity +
             ", additionalProperties=" + additionalProperties +
             '}';
  }
}
