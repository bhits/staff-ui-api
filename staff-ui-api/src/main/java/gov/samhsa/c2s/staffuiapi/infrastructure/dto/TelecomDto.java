package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class TelecomDto {

    @NotBlank
    private String system;

    @NotBlank
    private String value;
}
