package gov.samhsa.c2s.staffuiapi.config;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.RoleDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.TelecomDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsAddressDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(List<PropertyMap> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        propertyMaps.stream().filter(Objects::nonNull).forEach(modelMapper::addMappings);
        return modelMapper;
    }

    private enum Use {
        HOME,
        WORK
    }

    private enum System {
        PHONE,
        EMAIL
    }

    // PropertyMaps
    @Component
    static class UmsUserDtoToUserDtoMap extends PropertyMap<UmsUserDto, UserDto> {
        @Autowired
        private TelecomsToHomeEmailConverter telecomsToHomeEmailConverter;

        @Autowired
        private TelecomsToWorkEmailConverter telecomsToWorkEmailConverter;

        @Autowired
        private TelecomsToHomePhoneConverter telecomsToHomePhoneConverter;

        @Autowired
        private TelecomsToWorkPhoneConverter telecomsToWorkPhoneConverter;

        @Autowired
        private UmsAddressesToHomeAddressConverter umsAddressesToHomeAddressConverter;

        @Autowired
        private UmsAddressesToWorkAddressConverter umsAddressesToWorkAddressConverter;

        @Autowired
        private MultiRolesToPatientRoleConverter multiRolesToPatientRoleConverter;

        @Override
        protected void configure() {
            using(telecomsToHomeEmailConverter).map(source).setHomeEmail(null);
            using(telecomsToWorkEmailConverter).map(source).setWorkEmail(null);
            using(telecomsToHomePhoneConverter).map(source).setHomePhone(null);
            using(telecomsToWorkPhoneConverter).map(source).setWorkPhone(null);
            using(umsAddressesToHomeAddressConverter).map(source).setHomeAddress(null);
            using(umsAddressesToWorkAddressConverter).map(source).setWorkAddress(null);
            //ToDO: One user has multiple roles
            using(multiRolesToPatientRoleConverter).map(source).setRoles(null);
        }
    }

    @Component
    static class UserDtoToUmsUserDtoMap extends PropertyMap<UserDto, UmsUserDto> {
        @Autowired
        private EmailPhoneToTelecomsConverter emailPhoneToTelecomsConverter;

        @Autowired
        private HomeWorkAddressToAddressesConverter homeWorkAddressToAddressesConverter;

        @Autowired
        private PatientRoleToMultiRolesConverter patientRoleToMultiRolesConverter;

        @Override
        protected void configure() {
            using(emailPhoneToTelecomsConverter).map(source).setTelecoms(null);
            using(homeWorkAddressToAddressesConverter).map(source).setAddresses(null);
            //ToDO: One user has multiple roles
            using(patientRoleToMultiRolesConverter).map(source).setRoles(null);
        }
    }

    // Converters
    @Component
    static class TelecomsToHomeEmailConverter extends AbstractConverter<UmsUserDto, String> {
        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecoms().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(System.EMAIL.toString())
                            && telecomDto.getUse().equalsIgnoreCase(Use.HOME.toString()))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class TelecomsToWorkEmailConverter extends AbstractConverter<UmsUserDto, String> {
        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecoms().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(System.EMAIL.toString())
                            && telecomDto.getUse().equalsIgnoreCase(Use.WORK.toString()))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class TelecomsToHomePhoneConverter extends AbstractConverter<UmsUserDto, String> {
        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecoms().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(System.PHONE.toString())
                            && telecomDto.getUse().equalsIgnoreCase(Use.HOME.toString()))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class TelecomsToWorkPhoneConverter extends AbstractConverter<UmsUserDto, String> {
        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecoms().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(System.PHONE.toString())
                            && telecomDto.getUse().equalsIgnoreCase(Use.WORK.toString()))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    //Todo: Will support multiple Telecoms
    @Component
    static class EmailPhoneToTelecomsConverter extends AbstractConverter<UserDto, List<TelecomDto>> {
        @Override
        protected List<TelecomDto> convert(UserDto source) {
            TelecomDto homeEmailTelecomDto = TelecomDto.builder()
                    .system(System.EMAIL.toString())
                    .value(source.getHomeEmail())
                    .use(Use.HOME.toString())
                    .build();
            TelecomDto homePhoneTelecomDto = TelecomDto.builder()
                    .system(System.PHONE.toString())
                    .value(source.getHomePhone())
                    .use(Use.HOME.toString())
                    .build();
            return Arrays.asList(homeEmailTelecomDto, homePhoneTelecomDto);
        }
    }

    @Component
    static class UmsAddressesToHomeAddressConverter extends AbstractConverter<UmsUserDto, UmsAddressDto> {
        @Override
        protected UmsAddressDto convert(UmsUserDto source) {
            return source.getAddresses().stream()
                    .filter(umsAddressDto -> umsAddressDto.getUse().equalsIgnoreCase(Use.HOME.toString()))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class UmsAddressesToWorkAddressConverter extends AbstractConverter<UmsUserDto, UmsAddressDto> {
        @Override
        protected UmsAddressDto convert(UmsUserDto source) {
            return source.getAddresses().stream()
                    .filter(umsAddressDto -> umsAddressDto.getUse().equalsIgnoreCase(Use.WORK.toString()))
                    .findFirst()
                    .orElse(null);
        }
    }

    //Todo: Will support multiple address
    @Component
    static class HomeWorkAddressToAddressesConverter extends AbstractConverter<UserDto, List<UmsAddressDto>> {
        @Override
        protected List<UmsAddressDto> convert(UserDto source) {
            UmsAddressDto homeAddressDto = source.getHomeAddress();
            homeAddressDto.setUse(Use.HOME.toString());
            return Arrays.asList(homeAddressDto);
        }
    }

    //Todo: Will remove when One user has multiple roles
    @Component
    static class MultiRolesToPatientRoleConverter extends AbstractConverter<UmsUserDto, String> {

        @Override
        protected String convert(UmsUserDto source) {
            return source.getRoles().stream()
                    .filter(roleDto -> roleDto.getCode().equalsIgnoreCase("patient"))
                    .map(RoleDto::getCode)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class PatientRoleToMultiRolesConverter extends AbstractConverter<UserDto, List<RoleDto>> {
        @Override
        protected List<RoleDto> convert(UserDto source) {
            RoleDto roleDto = new RoleDto();
            roleDto.setCode(source.getRoles());
            return Arrays.asList(roleDto);
        }
    }
}