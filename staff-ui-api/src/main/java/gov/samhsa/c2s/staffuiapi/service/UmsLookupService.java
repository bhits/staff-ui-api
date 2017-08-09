package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.service.dto.UserCreationLookupDto;

import java.util.Locale;

public interface UmsLookupService {
    UserCreationLookupDto getUserCreationLookupInfo();
}
