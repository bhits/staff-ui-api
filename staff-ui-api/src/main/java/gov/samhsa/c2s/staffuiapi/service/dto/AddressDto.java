package gov.samhsa.c2s.staffuiapi.service.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class AddressDto {

    /**
     * The street address line1.
     */
    private String line1;

    /**
     * The street address line2.
     */
    private String line2;

    /**
     * The city.
     */
    private String city;

    /**
     * The state code.
     */
    private String state;

    /**
     * The postal code.
     */
    @Pattern(regexp = "\\d{5}(?:[-\\s]\\d{4})?")
    private String postalCode;

    /**
     * The country code.
     */
    private String country;
}
