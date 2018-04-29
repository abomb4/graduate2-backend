package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthorizationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizationController {

    @Autowired
    private AuthorizationService authService;

    @ResponseBody
    @RequestMapping(value = "/auth/check-status", method = RequestMethod.GET)
    public BaseServiceResponse<UserAgent> asdf(HttpServletRequest request) {
        return authService.isLogin();
    }

}
