package gov.samhsa.c2s.staffuiapi.service.mapping;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;
import gov.samhsa.c2s.staffuiapi.service.mapping.customconverter.*;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UmsUserDtoToUserDtoMap extends PropertyMap<UmsUserDto, UserDto> {

    private TelecomsToHomeEmailConverter telecomsToHomeEmailConverter;

    private TelecomsToWorkEmailConverter telecomsToWorkEmailConverter;

    private TelecomsToHomePhoneConverter telecomsToHomePhoneConverter;

    private TelecomsToWorkPhoneConverter telecomsToWorkPhoneConverter;

    private UmsAddressesToHomeAddressConverter umsAddressesToHomeAddressConverter;

    private UmsAddressesToWorkAddressConverter umsAddressesToWorkAddressConverter;

    private MultiRoleCodesToPatientRoleConverter multiRoleCodesToPatientRoleConverter;

    @Autowired
    public UmsUserDtoToUserDtoMap(TelecomsToHomeEmailConverter telecomsToHomeEmailConverter, TelecomsToWorkEmailConverter telecomsToWorkEmailConverter, TelecomsToHomePhoneConverter telecomsToHomePhoneConverter, TelecomsToWorkPhoneConverter telecomsToWorkPhoneConverter, UmsAddressesToHomeAddressConverter umsAddressesToHomeAddressConverter, UmsAddressesToWorkAddressConverter umsAddressesToWorkAddressConverter, MultiRoleCodesToPatientRoleConverter multiRoleCodesToPatientRoleConverter) {
        this.telecomsToHomeEmailConverter = telecomsToHomeEmailConverter;
        this.telecomsToWorkEmailConverter = telecomsToWorkEmailConverter;
        this.telecomsToHomePhoneConverter = telecomsToHomePhoneConverter;
        this.telecomsToWorkPhoneConverter = telecomsToWorkPhoneConverter;
        this.umsAddressesToHomeAddressConverter = umsAddressesToHomeAddressConverter;
        this.umsAddressesToWorkAddressConverter = umsAddressesToWorkAddressConverter;
        this.multiRoleCodesToPatientRoleConverter = multiRoleCodesToPatientRoleConverter;
    }

    @Override
    protected void configure() {
        using(telecomsToHomeEmailConverter).map(source).setHomeEmail(null);
        using(telecomsToWorkEmailConverter).map(source).setWorkEmail(null);
        using(telecomsToHomePhoneConverter).map(source).setHomePhone(null);
        using(telecomsToWorkPhoneConverter).map(source).setWorkPhone(null);
        using(umsAddressesToHomeAddressConverter).map(source).setHomeAddress(null);
        using(umsAddressesToWorkAddressConverter).map(source).setWorkAddress(null);
        using(multiRoleCodesToPatientRoleConverter).map(source).setRoles(null);
    }
}