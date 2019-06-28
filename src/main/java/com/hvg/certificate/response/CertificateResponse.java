package com.hvg.certificate.response;

public class CertificateResponse {
  private String fileName;
  private String fileDownloadUri;
  private String fileType;
  private long size;

  public CertificateResponse(String fileName, String fileDownloadUri, String fileType, long size) {
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

  // Getters and Setters (Omitted for brevity)
}

