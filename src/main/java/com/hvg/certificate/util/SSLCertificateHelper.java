package com.hvg.certificate.util;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.openssl.jcajce.JcaMiscPEMGenerator;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import org.bouncycastle.util.io.pem.PemWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Collection;

public class SSLCertificateHelper {

  public static String convertX509CertToBase64PEMString(String certFileLocation)
  throws Exception {

    Collection<? extends Certificate> certificates = SSLCertificateReaderUtil
                                                       .readCertificatesFromFileAsCollection(certFileLocation);
    return convertX509CertToBase64PEMString(certificates);
  }

  public static String convertX509CertToBase64PEMString(Collection<? extends Certificate> x509Certs)
  throws IOException {
    StringWriter stringWriter = new StringWriter();

    for (Certificate certificate : x509Certs) {

      stringWriter = new StringWriter();

      try (PemWriter pemWriter = new PemWriter(stringWriter)) {
        PemObjectGenerator gen = new JcaMiscPEMGenerator(certificate);
        pemWriter.writeObject(gen);
      }
    }
    return stringWriter.toString();
  }

  public static void createCertFiles(X509Certificate x509Cert, String certType, int i, String destinationLocation)

  throws IOException, CertificateEncodingException {
    FileOutputStream os =
      new FileOutputStream(StringUtils.join(destinationLocation, certType, "_", i));
    os.write(x509Cert.getEncoded());
  }
}
