package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UmsUserDto {
    private Long id;

    private String lastName;

    private String firstName;

    private LocalDate birthDate;

    private String genderCode;

    private String socialSecurityNumber;

    private AddressDto address;

    private List<TelecomDto> telecom;

    private List<RoleDto> role;

    private String locale;
}
