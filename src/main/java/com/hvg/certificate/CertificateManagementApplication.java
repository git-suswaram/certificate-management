package com.hvg.certificate;

import com.hvg.certificate.common.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
  FileStorageProperties.class
})
public class CertificateManagementApplication {

  public static void main(String[] args) {

    SpringApplication
      .run(CertificateManagementApplication.class, args);
  }

}
