package org.wlyyy.itrs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wlyyy.common.domain.BaseRestResponse;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.ServletUtils;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthorizationService;
import org.wlyyy.itrs.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/auth")
@Controller
public class AuthorizationController {

    @Autowired
    private AuthorizationService authService;

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public BaseRestResponse<String> login(String userName, String password, HttpServletRequest req) {
        final String ip = ServletUtils.getClientIp(req);
        final BaseServiceResponse<UserAgent> loginResult = authService.login(userName, password, ip);
        final UserAgent userAgent = loginResult.getData();
        if (loginResult.isSuccess()) {
            return new BaseRestResponse<>(true, "登录成功", userAgent.getSessionKey());
        } else {
            return new BaseRestResponse<>(false, "登录失败", null);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseServiceResponse<User> asdf(String userName, String password) {
        final User user = new User();
        user.setId(1L);
        user.setUserName(userName);
        user.setEmail("wlyyy@163.com");
        user.setPassword(password);
        user.setSalt("salt");
        user.setSex(1);
        user.setDepartmentId(1L);
        user.setRealName("realName");

        final BaseServiceResponse<User> user1 = userService.createUser(user);
        return user1;
    }

    // TODO logout
    // TODO isLogin
    // TODO getToken?
}
