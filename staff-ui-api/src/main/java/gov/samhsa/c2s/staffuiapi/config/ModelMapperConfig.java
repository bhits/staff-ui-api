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
import java.util.stream.Collectors;

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
        private MultiRoleCodesToPatientRoleConverter multiRoleCodesToPatientRoleConverter;

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

    @Component
    static class UserDtoToUmsUserDtoMap extends PropertyMap<UserDto, UmsUserDto> {
        @Autowired
        private EmailPhoneToTelecomsConverter emailPhoneToTelecomsConverter;

        @Autowired
        private HomeWorkAddressToAddressesConverter homeWorkAddressToAddressesConverter;

        @Autowired
        private PatientRoleToMultiRoleCodesConverter patientRoleToMultiRoleCodesConverter;

        @Override
        protected void configure() {
            using(emailPhoneToTelecomsConverter).map(source).setTelecoms(null);
            using(homeWorkAddressToAddressesConverter).map(source).setAddresses(null);
            using(patientRoleToMultiRoleCodesConverter).map(source).setRoles(null);
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

    @Component
    static class MultiRoleCodesToPatientRoleConverter extends AbstractConverter<UmsUserDto, List<String>> {

        @Override
        protected List<String> convert(UmsUserDto source) {
            return source.getRoles().stream()
                    .map(RoleDto::getCode)
                    .collect(Collectors.toList());
        }
    }

    @Component
    static class PatientRoleToMultiRoleCodesConverter extends AbstractConverter<UserDto, List<RoleDto>> {
        @Override
        protected List<RoleDto> convert(UserDto source) {
            return source.getRoles().stream()
                    .map(roleCode -> RoleDto.builder().code(roleCode).build())
                    .collect(Collectors.toList());
        }
    }
}