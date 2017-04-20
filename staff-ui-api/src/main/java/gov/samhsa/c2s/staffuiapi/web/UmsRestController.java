package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.infrastructure.dto.UserDto;
import gov.samhsa.c2s.staffuiapi.service.UmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("ums/users")
public class UmsRestController {

    @Autowired
    private UmsService umsService;

    @GetMapping
    public Object getUsers(@RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size) {
        return umsService.getAllUsers(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody UserDto userDto) {
        umsService.registerUser(userDto);
    }
}
