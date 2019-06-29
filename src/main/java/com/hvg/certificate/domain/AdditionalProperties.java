package com.hvg.certificate.domain;

public class AdditionalProperties {

  private String serialNumber;
  private String type;
  private int version;
  private String publicKey;
  private String sigAlgName;
  private String sigAlgOID;
  private long basicConstraints;

  public AdditionalProperties(String serialNumber, String type,
                              int version, String publicKey,
                              String sigAlgName, String sigAlgOID,
                              long basicConstraints) {

    this.serialNumber = serialNumber;
    this.type = type;
    this.version = version;
    this.publicKey = publicKey;
    this.sigAlgName = sigAlgName;
    this.sigAlgOID = sigAlgOID;
    this.basicConstraints = basicConstraints;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public String getPublicKey() {
    return publicKey;
  }

  public void setPublicKey(String publicKey) {
    this.publicKey = publicKey;
  }

  public String getSigAlgName() {
    return sigAlgName;
  }

  public void setSigAlgName(String sigAlgName) {
    this.sigAlgName = sigAlgName;
  }

  public String getSigAlgOID() {
    return sigAlgOID;
  }

  public void setSigAlgOID(String sigAlgOID) {
    this.sigAlgOID = sigAlgOID;
  }

  public long getBasicConstraints() {
    return basicConstraints;
  }

  public void setBasicConstraints(long basicConstraints) {
    this.basicConstraints = basicConstraints;
  }

  @Override
  public String toString() {
    return "AdditionalProperties{" +
             "serialNumber='" + serialNumber + '\'' +
             ", type='" + type + '\'' +
             ", version=" + version +
             ", publicKey='" + publicKey + '\'' +
             ", sigAlgName='" + sigAlgName + '\'' +
             ", sigAlgOID='" + sigAlgOID + '\'' +
             ", basicConstraints=" + basicConstraints +
             '}';
  }
}
