package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UserDto;

public interface UmsService {
    Object getAllUsers(Integer page, Integer size);

    void registerUser(UserDto userDto);

    Object searchUsersByFirstNameAndORLastName(String term);

    Object getUser(Long userId);

    void updateUser(Long userId, UserDto userDto);

    Object initiateUserActivation(Long userId);
}