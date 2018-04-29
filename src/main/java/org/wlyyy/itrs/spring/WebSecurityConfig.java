package org.wlyyy.itrs.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.session.SessionManagementFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/flow").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                // .addFilterAfter(new CsrfGrantingFilter(), SessionManagementFilter.class)
        ;
    }

    public class CsrfGrantingFilter implements Filter {

        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
                throws IOException, ServletException {
            CsrfToken csrf = (CsrfToken) servletRequest.getAttribute(CsrfToken.class.getName());
            String token = csrf.getToken();
            if (token != null && isAuthenticating(servletRequest)) {
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                Cookie cookie = new Cookie("CSRF-TOKEN", token);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }

        private boolean isAuthenticating(ServletRequest servletRequest) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            return request.getRequestURI().equals("/login");
        }

        @Override
        public void destroy() {
        }
    }
}
