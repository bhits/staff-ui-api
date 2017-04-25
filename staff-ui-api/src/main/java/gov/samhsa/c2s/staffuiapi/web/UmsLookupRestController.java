package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.UmsLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ums")
public class UmsLookupRestController implements UmsLookupService {

    @Autowired
    private UmsLookupClient umsLookupClient;

    @Override
    public Object getLocales() {
        return umsLookupClient.getLocales();
    }

    @Override
    public Object getStateCodes() {
        return umsLookupClient.getStateCodes();
    }

    @Override
    public Object getCountryCodes() {
        return umsLookupClient.getCountryCodes();
    }

    @Override
    public Object getGenderCodes() {
        return umsLookupClient.getGenderCodes();
    }

    @Override
    public Object getRoles() {
        return umsLookupClient.getRoles();
    }
}
