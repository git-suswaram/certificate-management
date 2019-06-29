package com.hvg.certificate.controller;

import com.hvg.certificate.response.CertificateResponse;
import com.hvg.certificate.response.CertificateResponseParser;
import com.hvg.certificate.service.FileStorageService;
import com.hvg.certificate.util.SSLCertificateUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Security;
import java.security.cert.X509Certificate;

@RestController
public class CertificateController {

  private static final Logger logger = LoggerFactory.getLogger(CertificateController.class);

  @Autowired
  private FileStorageService fileStorageService;

  public CertificateController() {
    Security.addProvider(new BouncyCastleProvider());
  }

  /*
  * cert-validity { attach-input : @ValidFormat certificate file }
  * cert-contents { attach-input : @ValidFormat certificate file }
  *
  * cert-details-from-truststore  { attach-input : @ValidFormat truststore file(s),
  *                                 body-input   : hostname-filter (@ValidFormat hostname or empty),
  *                                                status-filter (@Valid active/expired),
  *                                                date-filter (@Valid from/to date & Time) }
  *
  * create-cert-from-url {  body-input : @ValidFormat URL }
  * */

  @PostMapping("/cert-contents")
  public CertificateResponse getContentsFromDEREncodedCert(@RequestParam("certFile") MultipartFile certFile) throws Exception {

    String fileName = fileStorageService.storeFile(certFile);
    String certAsPEMString = SSLCertificateUtil.convertDERToPEM(fileName);
    X509Certificate[] x509Certificates = SSLCertificateUtil.readDetailsFromPEMEncodedCertificate(certAsPEMString);
    return CertificateResponseParser
             .parseResponse(x509Certificates);
  }

  @PostMapping("/pem-contents")
  public CertificateResponse getContentsFromPEMEncodedCert(@RequestParam("certFile") MultipartFile certFile) throws Exception {

    String fileName = fileStorageService.storeFile(certFile);
    X509Certificate[] x509Certificate = SSLCertificateUtil.readDetailsFromPEMEncodedCertificateFile(fileName);
    return CertificateResponseParser
             .parseResponse(x509Certificate);
  }
}
