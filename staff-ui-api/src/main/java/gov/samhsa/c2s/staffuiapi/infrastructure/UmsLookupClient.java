package gov.samhsa.c2s.staffuiapi.infrastructure;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RoleDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("ums")
public interface UmsLookupClient {
    @RequestMapping(value = "/locales", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getLocales();

    @RequestMapping(value = "/statecodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getStateCodes();

    @RequestMapping(value = "/countrycodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getCountryCodes();

    @RequestMapping(value = "/gendercodes", method = RequestMethod.GET)
    List<BaseUmsLookupDto> getGenderCodes();

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    List<RoleDto> getRoles();
}
