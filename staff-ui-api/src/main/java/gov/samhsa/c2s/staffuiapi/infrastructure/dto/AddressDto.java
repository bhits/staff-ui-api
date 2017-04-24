package gov.samhsa.c2s.staffuiapi.infrastructure.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddressDto {

    /**
     * The street address line1.
     */
    private String streetAddressLine1;

    /**
     * The street address line2.
     */
    private String streetAddressLine2;

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
