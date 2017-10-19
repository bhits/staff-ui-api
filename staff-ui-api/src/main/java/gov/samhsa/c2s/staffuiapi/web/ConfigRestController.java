package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.config.StaffUiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigRestController {
    private final StaffUiProperties staffUiProperties;

    @Autowired
    public ConfigRestController(StaffUiProperties staffUiProperties){
        this.staffUiProperties = staffUiProperties;
    }

    @GetMapping("/config")
    public StaffUiProperties getStaffConfig() {
        return staffUiProperties;
    }
}
