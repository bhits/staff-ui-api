package gov.samhsa.c2s.staffuiapi.infrastructure;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ums")
public interface UmsClient {
    public static final String X_FORWARDED_PROTO = "X-Forwarded-Proto";
    public static final String X_FORWARDED_HOST = "X-Forwarded-Host";
    public static final String X_FORWARDED_PORT = "X-Forwarded-Port";

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    PageableDto<UmsUserDto> getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    void registerUser(@RequestBody UmsUserDto umsUserDto);

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    List<UmsUserDto> searchUsersByFirstNameAndORLastName(@RequestParam("term") String term);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    UmsUserDto getUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    void updateUser(@PathVariable("userId") Long userId, @RequestBody UmsUserDto umsUserDto);

    @RequestMapping(value = "/users/{userId}/activation", method = RequestMethod.POST)
    Object initiateUserActivation(@PathVariable("userId") Long userId,
                                  @RequestParam(value = "lastUpdatedBy") String lastUpdatedBy,
                                  @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                                  @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                                  @RequestHeader(X_FORWARDED_PORT) String xForwardedPort);

    @RequestMapping(value = "/users/{userId}/activation", method = RequestMethod.GET)
    Object getCurrentUserCreationInfo(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}/disabled", method = RequestMethod.PUT)
    void disableUser(@PathVariable("userId") Long userId,
                     @RequestParam(value = "lastUpdatedBy") String lastUpdatedBy);

    @RequestMapping(value = "/users/{userId}/enabled/", method = RequestMethod.PUT)
    void enableUser(@PathVariable("userId") Long userId,
                    @RequestParam(value = "lastUpdatedBy") String lastUpdatedBy);
}