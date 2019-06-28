package com.hvg.certificate.controller;

import com.hvg.certificate.response.CertificateResponse;
import com.hvg.certificate.service.FileStorageService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Security;
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

  @PostMapping("/uploadMultipleFiles")
  public List<CertificateResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
    return Arrays.asList(files)
                 .stream()
                 .map(file -> uploadFile(file))
                 .collect(Collectors.toList());
  }

  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {

    // Load file as Resource
    Resource resource = fileStorageService.loadFileAsResource(fileName);

    // Try to determine file's content type
    String contentType = null;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      logger.info("Could not determine file type.");
    }

    // Fallback to the default content type if type could not be determined
    if (contentType == null) {
      contentType = "application/octet-stream";
    }

    return ResponseEntity.ok()
                         .contentType(MediaType.parseMediaType(contentType))
                         .header(HttpHeaders.CONTENT_DISPOSITION,
                                 "attachment; filename=\"" + resource.getFilename() + "\"")
                         .body(resource);
  }
}
