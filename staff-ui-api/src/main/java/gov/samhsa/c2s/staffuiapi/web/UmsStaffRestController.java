package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.service.UmsService;
import gov.samhsa.c2s.staffuiapi.service.dto.ProfileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("ums/staffs")
public class UmsStaffRestController {

    @Autowired
    private UmsService umsService;

    @GetMapping("/profile")
    public ProfileResponse getProviderProfile() {
        return umsService.getProviderProfile();
    }
}
