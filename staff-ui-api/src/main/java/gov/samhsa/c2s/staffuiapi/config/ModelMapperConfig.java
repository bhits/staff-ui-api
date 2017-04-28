package gov.samhsa.c2s.staffuiapi.config;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.TelecomDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsAddressDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.AddressDto;
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

        @Override
        protected void configure() {
            using(telecomsToHomeEmailConverter).map(source).setHomeEmail(null);
            using(telecomsToWorkEmailConverter).map(source).setWorkEmail(null);
            using(telecomsToHomePhoneConverter).map(source).setHomePhone(null);
            using(telecomsToWorkPhoneConverter).map(source).setWorkPhone(null);
            using(umsAddressesToHomeAddressConverter).map(source).setHomeAddress(null);
            using(umsAddressesToWorkAddressConverter).map(source).setWorkAddress(null);
        }
    }

    @Component
    static class UserDtoToUmsUserDtoMap extends PropertyMap<UserDto, UmsUserDto> {
        @Autowired
        private EmailPhoneToTelecomsConverter emailPhoneToTelecomsConverter;

        @Autowired
        private HomeWorkAddressToAddressesConverter homeWorkAddressToAddressesConverter;

        @Override
        protected void configure() {
            using(emailPhoneToTelecomsConverter).map(source).setTelecoms(null);
            using(homeWorkAddressToAddressesConverter).map(source).setAddresses(null);
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
            TelecomDto workEmailTelecomDto = TelecomDto.builder()
                    .system(System.EMAIL.toString())
                    .value(source.getWorkEmail())
                    .use(Use.WORK.toString())
                    .build();
            TelecomDto workPhoneTelecomDto = TelecomDto.builder()
                    .system(System.PHONE.toString())
                    .value(source.getWorkPhone())
                    .use(Use.WORK.toString())
                    .build();
            return Arrays.asList(homeEmailTelecomDto, homePhoneTelecomDto, workEmailTelecomDto, workPhoneTelecomDto);
        }
    }

    @Component
    static class UmsAddressesToHomeAddressConverter extends AbstractConverter<UmsUserDto, AddressDto> {
        @Override
        protected AddressDto convert(UmsUserDto source) {
            return source.getAddresses().stream()
                    .filter(umsAddressDto -> umsAddressDto.getUse().equalsIgnoreCase(Use.HOME.toString()))
                    .map(this::mapToAddressDto)
                    .findFirst()
                    .orElse(null);
        }

        private AddressDto mapToAddressDto(UmsAddressDto umsAddressDto) {
            return AddressDto.builder()
                    .line1(umsAddressDto.getLine1())
                    .line2(umsAddressDto.getLine2())
                    .city(umsAddressDto.getCity())
                    .state(umsAddressDto.getStateCode())
                    .postalCode(umsAddressDto.getPostalCode())
                    .country(umsAddressDto.getCountryCode())
                    .build();
        }
    }

    @Component
    static class UmsAddressesToWorkAddressConverter extends AbstractConverter<UmsUserDto, AddressDto> {
        @Override
        protected AddressDto convert(UmsUserDto source) {
            return source.getAddresses().stream()
                    .filter(umsAddressDto -> umsAddressDto.getUse().equalsIgnoreCase(Use.WORK.toString()))
                    .map(this::mapToAddressDto)
                    .findFirst()
                    .orElse(null);
        }

        private AddressDto mapToAddressDto(UmsAddressDto umsAddressDto) {
            return AddressDto.builder()
                    .line1(umsAddressDto.getLine1())
                    .line2(umsAddressDto.getLine2())
                    .city(umsAddressDto.getCity())
                    .state(umsAddressDto.getStateCode())
                    .postalCode(umsAddressDto.getPostalCode())
                    .country(umsAddressDto.getCountryCode())
                    .build();
        }
    }

    @Component
    static class HomeWorkAddressToAddressesConverter extends AbstractConverter<UserDto, List<UmsAddressDto>> {
        @Override
        protected List<UmsAddressDto> convert(UserDto source) {
            UmsAddressDto homeAddressDto = UmsAddressDto.builder()
                    .line1(source.getHomeAddress().getLine1())
                    .line2(source.getHomeAddress().getLine2())
                    .city(source.getHomeAddress().getCity())
                    .stateCode(source.getHomeAddress().getState())
                    .postalCode(source.getHomeAddress().getPostalCode())
                    .countryCode(source.getHomeAddress().getCountry())
                    .use(Use.HOME.toString())
                    .build();
            UmsAddressDto workAddressDto = UmsAddressDto.builder()
                    .line1(source.getWorkAddress().getLine1())
                    .line2(source.getWorkAddress().getLine2())
                    .city(source.getWorkAddress().getCity())
                    .stateCode(source.getWorkAddress().getState())
                    .postalCode(source.getWorkAddress().getPostalCode())
                    .countryCode(source.getWorkAddress().getCountry())
                    .use(Use.WORK.toString())
                    .build();
            return Arrays.asList(homeAddressDto, workAddressDto);
        }
    }
}