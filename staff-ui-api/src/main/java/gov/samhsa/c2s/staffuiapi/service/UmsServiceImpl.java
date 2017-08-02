package gov.samhsa.c2s.staffuiapi.service;

import gov.samhsa.c2s.staffuiapi.infrastructure.UmsClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.UmsLookupClient;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.BaseUmsLookupDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.JwtTokenKey;
import gov.samhsa.c2s.staffuiapi.service.dto.ProfileResponse;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UmsServiceImpl implements UmsService {

    @Autowired
    private final JwtTokenExtractor jwtTokenExtractor;
    @Autowired
    private UmsClient umsClient;
    @Autowired
    private UmsLookupClient umsLookupClient;
    @Autowired
    private ModelMapper modelMapper;

    public UmsServiceImpl(JwtTokenExtractor jwtTokenExtractor) {
        this.jwtTokenExtractor = jwtTokenExtractor;
    }

    @Override
    public PageableDto<UserDto> getAllUsers(Integer page, Integer size) {
        //Mapping of generic parameterized types
        Type pageableUserDtoType = new TypeToken<PageableDto<UserDto>>() {
        }.getType();

        PageableDto<UmsUserDto> pageableUmsUserDto = umsClient.getAllUsers(page, size);
        List<UserDto> userDtos = pageableUmsUserDto.getContent().stream()
                .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                .collect(Collectors.toList());

        PageableDto<UserDto> pageableUserDto = modelMapper.map(pageableUmsUserDto, pageableUserDtoType);
        pageableUserDto.setContent(userDtos);

        return pageableUserDto;
    }

    @Override
    public void registerUser(UserDto userDto) {
        userDto.setCreatedBy(getLastUpddatedBy());
        userDto.setLastUpdatedBy(getLastUpddatedBy());
        umsClient.registerUser(modelMapper.map(userDto, UmsUserDto.class));
    }

    @Override
    public List<UserDto> searchUsersByFirstNameAndORLastName(String term) {
        return umsClient.searchUsersByFirstNameAndORLastName(term).stream()
                .map(umsUserDto -> modelMapper.map(umsUserDto, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(Long userId) {
        return modelMapper.map(umsClient.getUser(userId), UserDto.class);
    }

    @Override
    public void updateUser(Long userId, UserDto userDto) {
        userDto.setLastUpdatedBy(getLastUpddatedBy());
        umsClient.updateUser(userId, modelMapper.map(userDto, UmsUserDto.class));
    }

    @Override
    public Object initiateUserActivation(Long userId, String xForwardedProto, String xForwardedHost, String xForwardedPort) {
        return umsClient.initiateUserActivation(userId, getLastUpddatedBy(), xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @Override
    public Object getCurrentUserCreationInfo(Long userId) {
        return umsClient.getCurrentUserCreationInfo(userId);
    }

    @Override
    public void disableUser(Long userId) {
        umsClient.disableUser(userId, getLastUpddatedBy());
    }

    @Override
    public void enableUser(Long userId) {
        umsClient.enableUser(userId, getLastUpddatedBy());
    }

    @Override
    public ProfileResponse getProviderProfile() {
        //Get system supported Locales
        List<BaseUmsLookupDto> supportedLocales = umsLookupClient.getLocales();
        // TODO Implement get staff profile from DB
        return ProfileResponse.builder()
                .userLocale("en")
                .supportedLocales(supportedLocales)
                .username("")
                .firstName("Admin")
                .lastName("Staff")
                .build();
    }

    private String getLastUpddatedBy() {
        return jwtTokenExtractor.getValueByKey(JwtTokenKey.USER_ID);
    }
}
