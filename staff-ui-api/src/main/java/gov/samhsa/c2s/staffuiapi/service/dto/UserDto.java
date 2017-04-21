package gov.samhsa.c2s.staffuiapi.service.dto;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.AddressDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RoleDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    private String lastName;

    private String firstName;

    private String email;

    private LocalDate birthDate;

    private String genderCode;

    private String socialSecurityNumber;

    private String phone;

    private AddressDto address;

    private List<RoleDto> role;

    private String locale;
}
