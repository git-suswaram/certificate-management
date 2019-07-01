package com.hvg.certificate.response;

import com.hvg.certificate.domain.AdditionalProperties;
import com.hvg.certificate.domain.CertificateInfo;
import com.hvg.certificate.domain.Validity;
import com.hvg.certificate.request.CertificateRequest;

import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CertificateResponseParser {

  public static CertificateResponse parseResponse(CertificateRequest certificateRequest) {

    String certificateType;
    String subjectDN;
    String issuerDN;
    String subjectPrincipal;
    String issuerPrincipal;
    Validity validity;
    AdditionalProperties additionalProperties;
    CertificateInfo certificateInfo;
    List<CertificateInfo> certificateInfoList = new ArrayList<>();
    Predicate<X509Certificate> x509CertificatePredicate;

    Date startDate = certificateRequest.getStartDate();
    Date endDate = certificateRequest.getEndDate();
    String filter = certificateRequest.getFilter();

    x509CertificatePredicate = getX509CertificatePredicate(startDate, endDate, filter);

    List<X509Certificate> certificateList =
      Arrays.asList(certificateRequest.getX509Certificates())
            .stream()
            .filter(x509CertificatePredicate)
            .collect(Collectors.toList());

    for (X509Certificate x509Certificate : certificateList) {

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

      certificateType = subjectDN.equals(issuerDN) ? "ROOT" :
                          (x509Certificate.getBasicConstraints() != -1 ? "INTERMEDIATE" : "DOMAIN");

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

    return new CertificateResponse(certificateInfoList);
  }

  private static Predicate<X509Certificate> getX509CertificatePredicate(Date startDate, Date endDate, String filter) {

    Predicate<X509Certificate> x509CertificatePredicate = null;

    if ("none".equalsIgnoreCase(filter)) {

      if (startDate != null & endDate != null) {

        x509CertificatePredicate = e -> e.getNotBefore().before(startDate) && e.getNotAfter().after(endDate);

      } else if (startDate == null & endDate != null) {

      } else if (startDate != null) {

      } else {

      }
    } else if ("active".equalsIgnoreCase(filter)) {
      if (startDate != null & endDate != null) {

      } else if (startDate == null & endDate != null) {

      } else if (startDate != null) {

      } else {

      }

    } else if ("expired".equalsIgnoreCase(filter)) {
      if (startDate != null & endDate != null) {

      } else if (startDate == null & endDate != null) {

      } else if (startDate != null) {

      } else {

      }

    } else {

    }
    return x509CertificatePredicate;
  }
}
