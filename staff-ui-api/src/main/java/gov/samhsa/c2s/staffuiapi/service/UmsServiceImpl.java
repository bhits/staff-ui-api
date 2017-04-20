package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UmsServiceImpl implements UmsService {
    @Autowired
    private UmsClient umsClient;

    @Override
    public Object getAllUsers(Integer page, Integer size) {
        return umsClient.getAllUsers(page, size);
    }

    @Override
    public void registerUser(UserDto userDto) {
        umsClient.registerUser(userDto);
    }

    @Override
    public Object searchUsersByFirstNameAndORLastName(String term) {
        return umsClient.searchUsersByFirstNameAndORLastName(term);
    }

    @Override
    public Object getUser(Long userId) {
        return umsClient.getUser(userId);
    }
}
