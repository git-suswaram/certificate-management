package com.hvg.certificate.request;

import com.hvg.certificate.util.SSLCertificateUtil;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.hvg.certificate.util.SSLCertificateUtil.readDetailsFromPEMEncodedCertificate;
import static com.hvg.certificate.util.SSLCertificateUtil.readDetailsFromPEMEncodedCertificateFile;

public class CertificateRequestParser {

  public static CertificateRequest parse(CertificateRequestParamModel certificateRequestParamModel, String fileName)
  throws Exception {

    X509Certificate[] x509Certificates;

    if ("PEM".equalsIgnoreCase(certificateRequestParamModel.getEncodingType())) {
      x509Certificates = readDetailsFromPEMEncodedCertificateFile(fileName);
    } else if ("DER".equalsIgnoreCase(certificateRequestParamModel.getEncodingType())) {
      String certAsPEMString = SSLCertificateUtil.convertDERToPEM(fileName);
      x509Certificates = readDetailsFromPEMEncodedCertificate(certAsPEMString);
    } else {
      throw new IllegalArgumentException("Invalid encoding type");
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    Date startDate = !certificateRequestParamModel.getStartDate().isEmpty()
                       ? simpleDateFormat.parse(certificateRequestParamModel.getStartDate())
                       : null;

    Date endDate = !certificateRequestParamModel.getEndDate().isEmpty()
                     ? simpleDateFormat.parse(certificateRequestParamModel.getEndDate())
                     : null;

    String filter = certificateRequestParamModel.getFilter();

    return new CertificateRequest(x509Certificates, filter, startDate, endDate);
  }
}
