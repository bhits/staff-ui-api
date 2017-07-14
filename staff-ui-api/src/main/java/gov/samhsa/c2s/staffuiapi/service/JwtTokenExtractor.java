package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.service.dto.JwtTokenKey;

public interface JwtTokenExtractor {
    String getValueByKey(JwtTokenKey key);
}