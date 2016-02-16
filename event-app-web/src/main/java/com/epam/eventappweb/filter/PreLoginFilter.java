package com.epam.eventappweb.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * filter that intercepts request and if target URL of request is login.html page saves referer header to session as
 * loginReferer attribute
 */
public class PreLoginFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreLoginFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        LOGGER.debug("doFilter started.");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String loginReferer;
        HttpSession session;
        if ("/event-app/login.html".equals(request.getRequestURI().toString())) {
            loginReferer = request.getHeader("referer");
            if (loginReferer!= null && !loginReferer.endsWith("/event-app/login.html")) {
                session = request.getSession();
                if (session != null) {
                    session.setAttribute("loginReferer", loginReferer);
                    LOGGER.debug("doFilter finished. LoginReferer = {} added to session", loginReferer);
                }
            }
        }
        LOGGER.debug("doFilter finished.");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
