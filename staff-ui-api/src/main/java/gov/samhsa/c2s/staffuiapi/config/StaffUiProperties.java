package gov.samhsa.c2s.staffuiapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Component
@ConfigurationProperties(prefix = "c2s.staff-ui")
@Data
public class StaffUiProperties {
    @NotNull
    @Valid
    private Features features;

    @Data
    public static class Features {
        private boolean demoDisclaimerEnabled;
    }
}
