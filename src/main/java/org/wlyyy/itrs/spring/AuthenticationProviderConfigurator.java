package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.itrs.domain.Role;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthorizationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class AuthenticationProviderConfigurator {

    @Bean
    public UserServiceSpringProvider springAuthenticationProvider(AuthorizationService authService) {
        return new UserServiceSpringProvider(authService);
    }

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
