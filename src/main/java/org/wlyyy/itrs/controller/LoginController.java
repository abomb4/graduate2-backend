package org.wlyyy.itrs.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class LoginController {

    @RequestMapping(value = "/login")
    public Map<String, Object> login(Authentication auth) {
        return Collections.singletonMap("user", auth.getName());
    }

    @RequestMapping(value = "/login2")
    public Map<String, Object> login2(Authentication auth) {
        return Collections.singletonMap("user", auth.getName());
    }
}