package gov.samhsa.c2s.staffuiapi.service.dto;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.AddressDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RoleDto;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {
    private Long id;

    @NotBlank
    private String lastName;

    private String middleName;

    @NotBlank
    private String firstName;

    @NotBlank
    private String email;

    @Past
    @NotNull
    private LocalDate birthDate;

    @NotBlank
    private String genderCode;

    private String socialSecurityNumber;

    private String phone;

    private AddressDto address;

    @NotNull
    private List<RoleDto> role;

    @NotBlank
    private String locale;
}
