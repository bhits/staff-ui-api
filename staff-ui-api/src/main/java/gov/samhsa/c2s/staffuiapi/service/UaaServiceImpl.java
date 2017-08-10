package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.config.StaffUiApiProperties;
import gov.samhsa.c2s.staffuiapi.infrastructure.UaaClient;
import gov.samhsa.c2s.staffuiapi.service.exception.AuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UaaServiceImpl implements UaaService {
    private static final String OAUTH2_GRAND_TYPE = "password";
    private static final String OAUTH2_RESPONSE_TYPE = "token";

    private final StaffUiApiProperties staffUiApiProperties;
    private final UaaClient uaaClient;

    @Autowired
    public UaaServiceImpl(StaffUiApiProperties staffUiApiProperties, UaaClient uaaClient) {
        this.staffUiApiProperties = staffUiApiProperties;
        this.uaaClient = uaaClient;
    }

    @Override
    public Object login(String username, String password) {
        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("client_id", staffUiApiProperties.getOauth2().getClient().getClientId());
        requestParams.put("client_secret", staffUiApiProperties.getOauth2().getClient().getClientSecret());
        requestParams.put("grant_type", OAUTH2_GRAND_TYPE);
        requestParams.put("response_type", OAUTH2_RESPONSE_TYPE);
        requestParams.put("username", username);
        requestParams.put("password", password);

        try {
            return uaaClient.getTokenUsingPasswordGrant(requestParams);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new AuthenticationException(e);
        }
    }
}
