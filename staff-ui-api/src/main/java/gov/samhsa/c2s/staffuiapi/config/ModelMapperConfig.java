package gov.samhsa.c2s.staffuiapi.config;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.TelecomDto;
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
    private static final String SYSTEM_EMAIL = "email";
    private static final String SYSTEM_PHONE = "phone";

    @Bean
    public ModelMapper modelMapper(List<PropertyMap> propertyMaps) {
        final ModelMapper modelMapper = new ModelMapper();
        propertyMaps.stream().filter(Objects::nonNull).forEach(modelMapper::addMappings);
        return modelMapper;
    }

    // PropertyMaps
    @Component
    static class UmsUserDtoToUserDtoMap extends PropertyMap<UmsUserDto, UserDto> {
        @Autowired
        private TelecomToEmailConverter telecomToEmailConverter;

        @Autowired
        private TelecomToPhoneConverter telecomToPhoneConverter;

        @Override
        protected void configure() {
            using(telecomToEmailConverter).map(source).setEmail(null);
            using(telecomToPhoneConverter).map(source).setPhone(null);
        }
    }

    @Component
    static class UserDtoToUmsUserDtoMap extends PropertyMap<UserDto, UmsUserDto> {
        @Autowired
        private EmailPhoneToTelecomConverter emailPhoneToTelecomConverter;

        @Override
        protected void configure() {
            using(emailPhoneToTelecomConverter).map(source).setTelecom(null);
        }
    }

    // Converters
    @Component
    static class TelecomToEmailConverter extends AbstractConverter<UmsUserDto, String> {

        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecom().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(SYSTEM_EMAIL))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class TelecomToPhoneConverter extends AbstractConverter<UmsUserDto, String> {
        @Override
        protected String convert(UmsUserDto source) {
            return source.getTelecom().stream()
                    .filter(telecomDto -> telecomDto.getSystem().equalsIgnoreCase(SYSTEM_PHONE))
                    .map(TelecomDto::getValue)
                    .findFirst()
                    .orElse(null);
        }
    }

    @Component
    static class EmailPhoneToTelecomConverter extends AbstractConverter<UserDto, List<TelecomDto>> {
        @Override
        protected List<TelecomDto> convert(UserDto source) {
            TelecomDto emailTelecomDto = TelecomDto.builder()
                    .system(SYSTEM_EMAIL)
                    .value(source.getEmail())
                    .build();
            TelecomDto phoneTelecomDto = TelecomDto.builder()
                    .system(SYSTEM_PHONE)
                    .value(source.getPhone())
                    .build();
            return Arrays.asList(emailTelecomDto, phoneTelecomDto);
        }
    }
}
