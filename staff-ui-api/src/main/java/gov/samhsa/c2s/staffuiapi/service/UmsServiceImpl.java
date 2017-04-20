package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.UmsClient;
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
}
