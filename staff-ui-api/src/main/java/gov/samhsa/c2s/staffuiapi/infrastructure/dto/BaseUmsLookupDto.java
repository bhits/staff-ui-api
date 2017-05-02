package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

@Data
public class BaseUmsLookupDto {
    private String code;

    private String displayName;

    private String description;

    private String codeSystem;

    private String codeSystemOID;

    private String codeSystemName;
}
