package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddressDto {

    /**
     * The street address line.
     */
    private String streetAddressLine;

    /**
     * The city.
     */
    private String city;

    /**
     * The state code.
     */
    private String stateCode;

    /**
     * The postal code.
     */
    @Pattern(regexp = "\\d{5}(?:[-\\s]\\d{4})?")
    private String postalCode;

    /**
     * The country code.
     */
    private String countryCode;
}
