package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;

import java.util.List;

public interface UmsService {
    PageableDto<UserDto> getAllUsers(Integer page, Integer size);

    void registerUser(UserDto userDto);

    List<UserDto> searchUsersByFirstNameAndORLastName(String term);

    UserDto getUser(Long userId);

    void updateUser(Long userId, UserDto userDto);

    Object initiateUserActivation(Long userId);
}