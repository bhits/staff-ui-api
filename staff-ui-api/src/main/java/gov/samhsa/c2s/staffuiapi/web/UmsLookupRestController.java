package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.service.UmsLookupService;
import gov.samhsa.c2s.staffuiapi.service.dto.UserCreationLookupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("ums")
public class UmsLookupRestController {

    @Autowired
    private UmsLookupService umsLookupService;

    @GetMapping("/userCreationLookupInfo")
    public UserCreationLookupDto getUserCreationLookupInfo(@RequestHeader("Accept-Language") Locale locale) {
        return umsLookupService.getUserCreationLookupInfo(locale);
    }
}
