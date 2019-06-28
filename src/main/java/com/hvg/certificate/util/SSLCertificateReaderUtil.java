package com.hvg.certificate.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.URL;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.*;
import java.util.*;

public class SSLCertificateReaderUtil {

  private static final Logger log = LoggerFactory.getLogger(SSLCertificateReaderUtil.class);

  private SSLCertificateReaderUtil() {
  }

  public Certificate[] extractCertificatesFromURL(String aURL) throws Exception {

    URL destinationURL = new URL(aURL);

    HttpsURLConnection conn = (HttpsURLConnection) destinationURL.openConnection();
    conn.connect();

    Certificate[] serverCertificates = conn.getServerCertificates();
    log.info("nb = {}", serverCertificates.length);

    return serverCertificates;
  }

  public static List<X509Certificate> getCertListFromDefaultTrustStore()
  throws NoSuchAlgorithmException, KeyStoreException {

    List<X509Certificate> x509Certificates = new ArrayList<>();
    TrustManagerFactory trustManagerFactory =
      TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

    trustManagerFactory.init((KeyStore) null);
    Arrays.stream(trustManagerFactory.getTrustManagers())
          .forEach(trustManager ->
                     x509Certificates
                       .addAll(Arrays.asList(
                         ((X509TrustManager) trustManager).getAcceptedIssuers())
                       )
          );

    return x509Certificates;
  }

  public static List<X509Certificate> getCertListFromTrustStores(Map<String, String> trustStoreDetailsMap)
  throws IOException,
           KeyStoreException,
           CertificateException,
           NoSuchAlgorithmException,
           InvalidAlgorithmParameterException {

    List<X509Certificate> x509Certificates = new ArrayList<>();
    String trustStoreReference;
    KeyStore trustStore;
    char[] password;
    FileInputStream fileInputStream;

    for (Map.Entry<String, String> entry : trustStoreDetailsMap.entrySet()) {
      trustStoreReference = entry.getKey();
      password = entry.getValue().toCharArray();

      fileInputStream = new FileInputStream(trustStoreReference);
      trustStore = KeyStore.getInstance(KeyStore.getDefaultType());

      trustStore.load(fileInputStream, password);

      PKIXParameters pkixParameters = new PKIXParameters(trustStore);

      for (TrustAnchor trustAnchor : pkixParameters.getTrustAnchors()) {
        X509Certificate certificate = trustAnchor.getTrustedCert();
        x509Certificates.add(certificate);
      }
    }
    return x509Certificates;
  }

  public static X509Certificate readCertificateFromFile(String certFileLocation)
  throws Exception {
    CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
    return (X509Certificate) certificateFactory
                               .generateCertificate(new FileInputStream(certFileLocation));
  }

  public static Collection<? extends Certificate> readCertificatesFromFileAsCollection(String certFileLocation)
  throws Exception {

    return readCertificatesFromFileAsCollection(new File(certFileLocation));
  }

  public static Collection<? extends Certificate> readCertificatesFromFileAsCollection(File certificateFile)
  throws Exception {

    CertificateFactory cf = CertificateFactory.getInstance("X.509");
    InputStream in = new FileInputStream(certificateFile);

    Collection<? extends Certificate> certs = cf.generateCertificates(in);
    in.close();

    return certs;
  }
}
