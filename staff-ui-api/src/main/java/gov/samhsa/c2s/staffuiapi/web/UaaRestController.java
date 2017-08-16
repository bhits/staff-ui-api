package gov.samhsa.c2s.staffuiapi.web;

import gov.samhsa.c2s.staffuiapi.service.UaaService;
import gov.samhsa.c2s.staffuiapi.service.dto.LoginRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/uaa")
public class UaaRestController {

    @Autowired
    private UaaService uaaService;

    @PostMapping("/login")
    Object login(@Valid @RequestBody LoginRequestDto requestDto) {
        return uaaService.login(requestDto);
    }
}