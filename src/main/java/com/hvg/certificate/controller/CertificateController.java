package com.hvg.certificate.controller;

import com.hvg.certificate.response.CertificateResponse;
import com.hvg.certificate.service.FileStorageService;
import com.hvg.certificate.util.SSLCertificateReaderUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
  public String getCertificateContents(@RequestParam("certFile") MultipartFile certFile) throws Exception {

    String fileName = fileStorageService.storeFile(certFile);
    X509Certificate x509Certificate = SSLCertificateReaderUtil.readCertificateFromFile(fileName);
    return x509Certificate.toString();

  }

  @PostMapping("/pem-contents")
  public String getPemFileContents(@RequestParam("certFile") MultipartFile certFile) throws Exception {

    String fileName = fileStorageService.storeFile(certFile);
    X509Certificate[] x509Certificate = SSLCertificateReaderUtil.getCertificateDetailsFromPEM(fileName);
    return x509Certificate.toString();

  }

  @PostMapping("/uploadMultipleFiles")
  public List<CertificateResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return Arrays.asList(files)
                 .stream()
                 .map(file -> uploadFile(file))
                 .collect(Collectors.toList());
  }

  @PostMapping("/uploadFile")
  public CertificateResponse uploadFile(@RequestParam("file") MultipartFile file) {

    String fileName = fileStorageService.storeFile(file);

    String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                                                        .path("/downloadFile/")
                                                        .path(fileName)
                                                        .toUriString();

    return new CertificateResponse(fileName, fileDownloadUri,
                                   file.getContentType(), file.getSize());
  }

}
