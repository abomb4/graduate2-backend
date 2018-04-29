package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.wlyyy.common.utils.StringTemplateUtils.St;
import org.wlyyy.itrs.service.AuthorizationServiceImpl;

@Configuration
@EnableRedisHttpSession
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/myprofile/*").authenticated()
                .antMatchers("/auth/check-status").anonymous()
                .anyRequest().permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and().csrf().disable()
        // .addFilterAfter(new CsrfGrantingFilter(), SessionManagementFilter.class)
        ;
    }

    /**
     * 这东西是校验入口，继承了AbstractAuthenticationProcessingFilter。
     * 它会调用{@link AuthenticationManager#authenticate} 来进行验证。
     * 一般情况下AuthenticationManager 由{@link ProviderManager}来提供。
     * ProviderManager会找到{@link AuthenticationProvider} 来进行真正的身份验证。
     * <p>
     * 我们的{@link AuthorizationServiceImpl} 实现了AuthenticationProvider，可以验证。
     *
     * @param manager 密码管理服务，boot默认生成的
     * @return UsernamePasswordAuthenticationFilter
     */
    @Bean
    public UsernamePasswordAuthenticationFilter getUsernamePasswordAuthenticationFilter(AuthenticationManager manager) {
        final UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(manager);
        filter.setFilterProcessesUrl("/auth/login");
        filter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            final int keyHash = ((RememberMeAuthenticationToken) authentication).getKeyHash();
            response.getOutputStream().print(St.r("{ \"status\": 200, \"sessionKey\": \"{}\" }", keyHash));
            response.setStatus(200);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
        });
        return filter;
    }

    @Bean
    public CookieCsrfTokenRepository getCookieCsrfTokenRepository() {
        return new CookieCsrfTokenRepository();
    }

}

