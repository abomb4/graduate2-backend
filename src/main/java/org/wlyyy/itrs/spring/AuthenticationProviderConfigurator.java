package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthorizationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * AbstractAuthenticationProcessingFilter 调用 UsernamePasswordAuthenticationFilter 中的attemptAuthentication方法，
 * 将表单请求的信息（用户、密码等信息）赋值给UsernamePasswordAuthenticationToken（authRequest），
 * 然后调用AbstractAuthenticationProcessingFilter 中的AuthenticationManager类中的（默认是ProviderManager类）
 * 方法——getAuthenticationManager().authenticate(authRequest)对用户密码的正确性进行验证，
 * 认证失败就抛出异常，成功就返回Authentication对象。
 *
 * @author wly
 */
@Configuration
public class AuthenticationProviderConfigurator {

    /**
     * 这东西是校验入口，继承了AbstractAuthenticationProcessingFilter
     *
     * @param manager 密码管理服务，boot默认生成的
     * @return UsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter(AuthenticationManager manager) {
        final UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(manager);
        filter.setFilterProcessesUrl("/login");
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.getOutputStream().print("{ \"status\": 200 }");
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
        });
        return filter;
    }

    /**
     * 创建一个AuthenticationProvider，用于校验登录信息
     *
     * @param authService 我们自己的校验服务
     * @return AuthenticationProvider
     */
    @Bean
    public UserServiceSpringProvider springAuthenticationProvider(AuthorizationService authService) {
        return new UserServiceSpringProvider(authService);
    }

    @Bean
    public CookieCsrfTokenRepository getCookieCsrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

    /**
     * 用户登录校验Service Spring适配
     */
    public static class UserServiceSpringProvider implements AuthenticationProvider {

        private AuthorizationService authService;

        public UserServiceSpringProvider(AuthorizationService authService) {
            this.authService = authService;
        }

        @Override
        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
            final String userName = authentication.getPrincipal().toString();
            final String ip = authentication.getDetails().toString();
            final String password = authentication.getCredentials().toString();

            final BaseServiceResponse<UserAgent> login = authService.login(userName, password, ip);
            if (login.isSuccess()) {
                final UserAgent userAgent = login.getData();
                final User.UserBuilder userBuilder = User.withUsername(userAgent.getUserName());

                final List<SimpleGrantedAuthority> roles = userAgent.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList());

                userBuilder.password(password);
                userBuilder.roles(roles.stream().map(SimpleGrantedAuthority::getAuthority).toArray(String[]::new));

                return new RememberMeAuthenticationToken(
                        userAgent.getSessionKey(),
                        userBuilder.build(),
                        roles
                );
            } else {
                throw new RememberMeAuthenticationException("Cannot authenticate " + authentication.getPrincipal());
            }
        }

        @Override
        public boolean supports(Class<?> authentication) {
            return true;
        }
    }
}
