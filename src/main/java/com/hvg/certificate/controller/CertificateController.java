package com.hvg.certificate.controller;

import com.hvg.certificate.request.CertificateRequest;
import com.hvg.certificate.request.CertificateRequestParser;
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
import java.util.HashMap;
import java.util.Map;

import static com.hvg.certificate.util.SSLCertificateUtil.readDetailsFromPEMEncodedCertificate;
import static com.hvg.certificate.util.SSLCertificateUtil.readDetailsFromPEMEncodedCertificateFile;

@RestController
public class CertificateController {

  private static final Logger log = LoggerFactory.getLogger(CertificateController.class);

  @Autowired
  private FileStorageService fileStorageService;

  public CertificateController() {
    Security.addProvider(new BouncyCastleProvider());
  }

  // how does a sample root or intermediate cert expiry notification look like (based on what criteria to search?)

  /*
   * cert-contents-from-file       { body form-data =>
   *                                     certFile      : @ValidFormat DER/PEM
   *                                     encodingType  : @ValidValues DER/PEM
   *                                     filter        : @ValidValues active/expired
   *                                     startDate     : @ValidDate
   *                                     endDate       : @ValidDate
   *                               }
   *
   * cert-details-from-truststore  { body form-data =>
   *                                     trustStore    : @ValidFormat JKS
   *                                     filter        : @ValidValues active/expired
   *                                     hostname      : @ValidFormat hostname or empty
   *                                     startDate     : @ValidDate
   *                                     endDate       : @ValidDate
   *                               }
   *
   * cert-contents-from-url        { body form-data =>
   *                                     url           : @ValidFormat url
   *                                     filter        : @ValidValues active/expired
   *                                     startDate     : @ValidDate
   *                                     endDate       : @ValidDate
   *                               }
   *
   * */

  @PostMapping("/cert-contents-from-file")
  public CertificateResponse getContentsFromDEREncodedCert(@RequestParam("certFile") MultipartFile certFile,
                                                           @RequestParam("encodingType") String encodingType,
                                                           @RequestParam("filter") String filter,
                                                           @RequestParam("startDate") String startDate,
                                                           @RequestParam("endDate") String endDate)
  throws Exception {

    X509Certificate[] x509Certificates;
    Map<String, String> filterCriteriaMap = new HashMap<>();

    String fileName = fileStorageService.storeFile(certFile);

    filterCriteriaMap.put("FILTER", filter);
    filterCriteriaMap.put("START_DATE", startDate);
    filterCriteriaMap.put("END_DATE", endDate);

    if ("PEM".equalsIgnoreCase(encodingType)) {
      x509Certificates = readDetailsFromPEMEncodedCertificateFile(fileName);
    } else if ("DER".equalsIgnoreCase(encodingType)) {
      String certAsPEMString = SSLCertificateUtil.convertDERToPEM(fileName);
      x509Certificates = readDetailsFromPEMEncodedCertificate(certAsPEMString);
    } else {
      throw new IllegalArgumentException("Invalid encoding type");
    }

    CertificateRequest certificateRequest = CertificateRequestParser.parse(x509Certificates, filterCriteriaMap);

    return CertificateResponseParser
             .parseResponse(certificateRequest);
  }

}
