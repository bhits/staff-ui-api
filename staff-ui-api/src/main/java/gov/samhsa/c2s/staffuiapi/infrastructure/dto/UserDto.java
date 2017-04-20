package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.List;

@Data
public class UserDto {

    private Long id;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String firstName;

    @Past
    @NotNull
    private LocalDate birthDate;

    @NotEmpty
    private String genderCode;

    private String socialSecurityNumber;

    private AddressDto address;

    private List<TelecomDto> telecom;

    private String role;

    private String locale;
}
