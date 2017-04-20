package gov.samhsa.c2s.staffuiapi.infrastructure;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UserDto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ums")
public interface UmsClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Object getAllUsers(@RequestParam(value = "page", required = false) Integer page,
                       @RequestParam(value = "size", required = false) Integer size);

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    void registerUser(@RequestBody UserDto userDto);
}