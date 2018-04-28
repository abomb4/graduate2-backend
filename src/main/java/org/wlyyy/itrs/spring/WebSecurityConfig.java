package org.wlyyy.itrs.spring;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.wlyyy.common.domain.BaseServiceResponse;
import org.wlyyy.common.utils.StringTemplateUtils;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.domain.UserAgent;
import org.wlyyy.itrs.service.AuthorizationService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableRedisHttpSession
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .antMatchers("/myprofile/*").authenticated()
                .and().csrf().disable()

                // .addFilterAfter(new CsrfGrantingFilter(), SessionManagementFilter.class)
        ;
    }

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
            final int keyHash = ((RememberMeAuthenticationToken) authentication).getKeyHash();
            response.getOutputStream().print(St.r("{ \"status\": 200, \"sessionKey\": \"{}\" }", keyHash));
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

    // Session management

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

//    @Bean
//    public CookieSerializer cookieSerializer(@Value("${web.secirity.cookie.domain:0}") String value) {
//        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//        serializer.setCookieName("JSESSIONID");
//        serializer.setCookiePath("/");
//        if (!StringUtils.isEmpty(value) && !"0".equals(value)) {
//            serializer.setDomainNamePattern(value);
//        }
//        return serializer;
//    }
}

