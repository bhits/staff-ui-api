package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

@Data
public class BaseUmsLookupDto {
    String code;

    String displayName;

    String description;

    String codeSystem;

    String codeSystemOID;

    String codeSystemName;
}
