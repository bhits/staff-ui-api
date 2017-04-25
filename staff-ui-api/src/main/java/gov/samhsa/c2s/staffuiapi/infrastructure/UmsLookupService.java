package gov.samhsa.c2s.staffuiapi.infrastructure;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface UmsLookupService {
    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    Object getLocales();

    @RequestMapping(value = "/statecodes", method = RequestMethod.GET)
    Object getStateCodes();

    @RequestMapping(value = "/countrycodes", method = RequestMethod.GET)
    Object getCountryCodes();

    @RequestMapping(value = "/gendercodes", method = RequestMethod.GET)
    Object getGenderCodes();

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    Object getRoles();
}
