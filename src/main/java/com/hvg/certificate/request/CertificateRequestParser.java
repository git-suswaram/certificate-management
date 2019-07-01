package com.hvg.certificate.request;

import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class CertificateRequestParser {

  public static CertificateRequest parse(X509Certificate[] x509Certificates, Map<String, String> filterCriteriaMap)
  throws ParseException {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

    Date startDate = !filterCriteriaMap.get("START_DATE").isEmpty()
                       ? simpleDateFormat.parse(filterCriteriaMap.get("START_DATE"))
                       : null;

    Date endDate = !filterCriteriaMap.get("END_DATE").isEmpty()
                     ? simpleDateFormat.parse(filterCriteriaMap.get("END_DATE"))
                     : null;

    String filter = filterCriteriaMap.get("FILTER");

    CertificateRequest certificateRequest = new CertificateRequest(x509Certificates, filter, startDate, endDate);

    return certificateRequest;
  }
}
