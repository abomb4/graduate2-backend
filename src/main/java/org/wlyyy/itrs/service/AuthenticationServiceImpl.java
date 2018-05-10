package org.wlyyy.itrs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.dao.UserRepository;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.domain.User;
import org.wlyyy.itrs.domain.UserAgent;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * 认证服务实现，既实现了管理系统的认证接口，又实现了Spring Security认证服务接口，
 * 可以直接被Spring Securf0ity调用。
 *
 * @author wly
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService, AuthenticationProvider, UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    /**
     * 登录验证方法。
     *
     * @param userName 用户名
     * @param password 密码，明文
     * @return 登录用户信息对象
     */
    @Override
    public BaseServiceResponse<UserAgent> login(String userName, String password) {
        // 1. 校验参数
        // 2. 验证密码
        final BaseServiceResponse<User> validateResponse = userService.validateUser(userName, password);
        if (validateResponse.isSuccess()) {
            final User user = validateResponse.getData();

            // final String sessionKey = SecurityUtils.generateSessionKey(user, clientIp, LocalDateTime.now());
            final Set<Role> roles = roleService.findRoleIdsByUserId(user.getId()).getData();
            final UserAgent userAgent = new UserAgent()
                    .setId(user.getId())
                    .setEmail(user.getEmail())
                    .setRoles(roles)
                    .setUserName(user.getUserName())
                    .setSex(user.getSex())
                    .setDepartmentId(user.getDepartmentId())
                    .setDepartmentName(departmentService.findById(user.getDepartmentId()).getDepartmentName())
                    .setRealName(user.getRealName())
                    .setLoginTime(LocalDateTime.now())
                    .setRefreshTime(LocalDateTime.now());

            // Put to distributed cache
            // cache.put(sessionKey, userAgent, UserAgent.class);
            return new BaseServiceResponse<>(true, "Login successfully", userAgent, null);

        } else {
            return new BaseServiceResponse<>(false, "Validate failed", null, null);
        }
    }

    @Override
    public BaseServiceResponse<UserAgent> isLogin() {

        final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        final Object principal = auth.getPrincipal();
        if (principal instanceof UserDetails) {
            final UserDetails userDetail = (UserDetails) principal;

            if (auth.isAuthenticated() && !"anonymousUser".equals(userDetail.getUsername())) {
                return new BaseServiceResponse<UserAgent>(true, "You are logged in.", (UserAgent) userDetail, null);
            } else {
                return new BaseServiceResponse<UserAgent>(false, "You are NOT logged in.", null, null);
            }
        } else {
            return new BaseServiceResponse<UserAgent>(false, "You are NOT logged in.", null, null);
        }
    }

    @Override
    public boolean logout() {
        return true;
    }

    /**
     * 实现Spring AuthenticationProvider接口，提供认证服务
     *
     * @param authentication 认证信息
     * @return 认证结果信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String userName = authentication.getPrincipal().toString();
        final WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        final String ip = details.getRemoteAddress();
        final String sessionKey = details.getSessionId();
        final String password = authentication.getCredentials().toString();

        final BaseServiceResponse<UserAgent> login = login(userName, password);
        if (login.isSuccess()) {
            final UserAgent userAgent = login.getData();
            userAgent.setSessionKey(sessionKey);

            final RememberMeAuthenticationToken authenticationToken = new RememberMeAuthenticationToken(
                    userAgent.getSessionKey(),
                    userAgent,
                    userAgent.getRoles()
            );
            return authenticationToken;
        } else {
            // throw new RememberMeAuthenticationException("Cannot authenticate " + authentication.getPrincipal());
            return null;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final User user = this.userRepository.findFullByUserName(username);
        if (user == null) {
            return null;
        } else {
            final BaseServiceResponse<Set<Role>> roleIdsByUserId = roleService.findRoleIdsByUserId(user.getId());
            final Optional<Set<Role>> roles = Optional.ofNullable(roleIdsByUserId.getData());
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), roles.orElse(new HashSet<>(0)));
        }
    }
}
