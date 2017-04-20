package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UserDto;

public interface UmsService {
    Object getAllUsers(Integer page, Integer size);

    void registerUser(UserDto userDto);
}