package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.PageableDto;
import gov.samhsa.c2s.staffuiapi.service.UmsService;
import gov.samhsa.c2s.staffuiapi.service.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static gov.samhsa.c2s.staffuiapi.infrastructure.UmsClient.*;

@RestController
@RequestMapping("ums/users")
public class UmsRestController {

    @Autowired
    private UmsService umsService;

    @GetMapping
    public PageableDto<UserDto> getUsers(@RequestParam(value = "page", required = false) Integer page,
                                         @RequestParam(value = "size", required = false) Integer size) {
        return umsService.getAllUsers(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserDto userDto) {
        umsService.registerUser(userDto);
    }

    @GetMapping("/search")
    public List<UserDto> searchUsersByFirstNameAndORLastName(@RequestParam("term") String term) {
        return umsService.searchUsersByFirstNameAndORLastName(term);
    }

    @GetMapping("/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return umsService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public void editUser(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        umsService.updateUser(userId, userDto);
    }

    @PostMapping(value = "/{userId}/activation")
    @ResponseStatus(HttpStatus.CREATED)
    public Object initiateUserActivation(@PathVariable Long userId,
                                         @RequestHeader(X_FORWARDED_PROTO) String xForwardedProto,
                                         @RequestHeader(X_FORWARDED_HOST) String xForwardedHost,
                                         @RequestHeader(X_FORWARDED_PORT) String xForwardedPort) {
        return umsService.initiateUserActivation(userId, xForwardedProto, xForwardedHost, xForwardedPort);
    }

    @GetMapping(value = "/{userId}/activation")
    public Object getCurrentUserCreationInfo(@PathVariable Long userId) {
        return umsService.getCurrentUserCreationInfo(userId);
    }

    @PutMapping("/{userId}/disabled")
    public void disableUser(@PathVariable Long userId) {
        umsService.disableUser(userId);
    }

    @PutMapping("/{userId}/enabled")
    public void enableUser(@PathVariable Long userId) {
        umsService.enableUser(userId);
    }
}
