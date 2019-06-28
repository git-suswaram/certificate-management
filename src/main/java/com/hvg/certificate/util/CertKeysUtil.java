package com.hvg.certificate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.x509.X509CertImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.security.*;
import java.security.cert.CertificateException;

public class CertKeysUtil {

  private static final Logger log = LoggerFactory.getLogger(CertKeysUtil.class);
  public static final int KEY_SIZE = 2048;

  public static PublicKey readPublicKeyFromCertFile(final String certFileLocation)
  throws FileNotFoundException, CertificateException {

    return new X509CertImpl(new FileInputStream(new File(certFileLocation))).getPublicKey();
  }

  public static KeyPair generateRSAKeyPair()
  throws NoSuchAlgorithmException, NoSuchProviderException {

    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
    generator.initialize(KEY_SIZE);

    KeyPair keyPair = generator.generateKeyPair();
    log.info("RSA key pair generated.");
    return keyPair;
  }

}
