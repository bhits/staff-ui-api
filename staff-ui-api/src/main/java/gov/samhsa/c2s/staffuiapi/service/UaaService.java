package gov.samhsa.c2s.staffuiapi.service;


import gov.samhsa.c2s.staffuiapi.service.dto.LoginRequestDto;

public interface UaaService {
    Object login(LoginRequestDto requestDto);
}
