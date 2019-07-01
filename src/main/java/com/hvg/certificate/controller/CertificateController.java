package com.hvg.certificate.controller;

import com.hvg.certificate.request.CertificateRequest;
import com.hvg.certificate.request.CertificateRequestParamModel;
import com.hvg.certificate.request.CertificateRequestParser;
import com.hvg.certificate.response.CertificateResponse;
import com.hvg.certificate.response.CertificateResponseParser;
import com.hvg.certificate.service.FileStorageService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Security;

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
  public CertificateResponse getContentsFromDEREncodedCert(
    @ModelAttribute CertificateRequestParamModel certificateRequestParamModel)
  throws Exception {

    String fileName = fileStorageService.storeFile(certificateRequestParamModel.getCertFile());
    CertificateRequest certificateRequest = CertificateRequestParser.parse(certificateRequestParamModel, fileName);

    return CertificateResponseParser.parseResponse(certificateRequest);
  }

}
