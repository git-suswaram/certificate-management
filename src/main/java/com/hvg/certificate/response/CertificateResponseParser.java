package com.hvg.certificate.response;

import com.hvg.certificate.domain.AdditionalProperties;
import com.hvg.certificate.domain.CertificateInfo;
import com.hvg.certificate.domain.Validity;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class CertificateResponseParser {

  public static CertificateResponse parseResponse(X509Certificate[] x509Certificates) {

    String subjectDN;
    String issuerDN;
    String subjectPrincipal;
    String issuerPrincipal;
    Validity validity;
    AdditionalProperties additionalProperties;
    CertificateInfo certificateInfo;
    String certificateType;
    List<CertificateInfo> certificateInfoList = new ArrayList<>();

    for (X509Certificate x509Certificate : x509Certificates) {

      subjectDN = x509Certificate.getSubjectDN().toString();
      issuerDN = x509Certificate.getIssuerDN().toString();
      subjectPrincipal = x509Certificate.getSubjectX500Principal().toString();
      issuerPrincipal = x509Certificate.getIssuerX500Principal().toString();

      validity = new Validity(x509Certificate.getNotBefore(), x509Certificate.getNotAfter());

      additionalProperties = new AdditionalProperties(
        x509Certificate.getSerialNumber().toString(),
        x509Certificate.getType(),
        x509Certificate.getVersion(),
        x509Certificate.getPublicKey().toString(),
        x509Certificate.getSigAlgName(),
        x509Certificate.getSigAlgOID(),
        x509Certificate.getBasicConstraints()
        );

      certificateType = x509Certificate.getBasicConstraints() == -1 ? "DOMAIN" :
                          (!subjectDN.equals(issuerDN) ? "INTERMEDIATE" : "ROOT");

      certificateInfo = new CertificateInfo(
        certificateType,
        subjectDN,
        issuerDN,
        subjectPrincipal,
        issuerPrincipal,
        validity,
        additionalProperties);
      certificateInfoList.add(certificateInfo);
    }
    CertificateResponse certificateResponse = new CertificateResponse(certificateInfoList);

    return certificateResponse;
  }
}
