package gov.samhsa.c2s.staffuiapi.infrastructure;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UmsUserDto;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("ums")
public interface UmsClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    PageableDto<UmsUserDto> getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                                        @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    void registerUser(@RequestBody UserDto userDto);

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    List<UmsUserDto> searchUsersByFirstNameAndORLastName(@RequestParam("term") String term);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.GET)
    UmsUserDto getUser(@PathVariable("userId") Long userId);

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.PUT)
    void updateUser(@PathVariable("userId") Long userId, @RequestBody UserDto userDto);

    @RequestMapping(value = "/users/{userId}/activation", method = RequestMethod.POST)
    Object initiateUserActivation(@PathVariable("userId") Long userId);
}