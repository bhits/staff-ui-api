package gov.samhsa.c2s.staffuiapi.service.mapping.customconverter;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.TelecomDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.mapping.System;
import gov.samhsa.c2s.staffuiapi.service.mapping.Use;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class TelecomsToWorkPhoneConverter extends AbstractConverter<UmsUserDto, String> {
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
