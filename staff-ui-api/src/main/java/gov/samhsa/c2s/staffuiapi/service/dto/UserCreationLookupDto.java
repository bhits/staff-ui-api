package gov.samhsa.c2s.staffuiapi.service.dto;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RoleDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreationLookupDto {
    private List<RoleDto> roles;
    private List<BaseUmsLookupDto> genderCodes;
    private List<BaseUmsLookupDto> stateCodes;
    private List<BaseUmsLookupDto> countryCodes;
    private List<BaseUmsLookupDto> locales;
}
