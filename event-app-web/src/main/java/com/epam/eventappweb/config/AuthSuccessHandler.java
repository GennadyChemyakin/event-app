package com.epam.eventappweb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * class that representing auth success handler.
 * changed determineTargetUrl from AbstractAuthenticationTargetUrlRequestHandler
 * to determine targetURL of redirect after success login to url that was true referer to login.html
 * extends SavedRequestAwareAuthenticationSuccessHandler to represent standard behaviour when
 * request is intercepted and requires authentication. Then the request data is stored to record the original destination
 * before the authentication process commenced, and to allow the request to be reconstructed when a redirect to the same URL occurs.
 */
public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthSuccessHandler.class);

    /**
     * determine targetURL of redirect after success login
     * repeat code from AbstractAuthenticationTargetUrlRequestHandler except part that looking for attribute in session
     * that represent true login.html referer
     *
     * @param request
     * @param response
     * @return targetUrl - destination of redirect
     */
    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.debug("determineTargetUrl started.");
        String targetUrl;
        String targetUrlAsParam = getTargetUrlAsParameter(request);
        String targetUrlFromSession = getTargetUrlFromSession(request);
        if (this.isAlwaysUseDefaultTargetUrl() || StringUtils.isBlank(targetUrlAsParam) && StringUtils.isBlank(targetUrlFromSession)) {
            targetUrl = this.getDefaultTargetUrl();
        } else if (StringUtils.isNotBlank(targetUrlAsParam)) {
            targetUrl = targetUrlAsParam;
        } else {
            targetUrl = targetUrlFromSession;
        }
        LOGGER.debug("determineTargetUrl finished. Redirect to URL = {}", targetUrl);
        return targetUrl;
    }

    /**
     * method that searches targetUrl in request params
     *
     * @param request
     * @return target url
     */
    private String getTargetUrlAsParameter(HttpServletRequest request) {
        String targetUrl = null;
        if (this.getTargetUrlParameter() != null) {
            targetUrl = request.getParameter(this.getTargetUrlParameter());
        }
        return targetUrl;
    }

    /**
     * method that searches session attribute loginReferer to use it as url for redirect
     *
     * @param request
     * @return target url
     */
    private String getTargetUrlFromSession(HttpServletRequest request) {
        String targetUrl = null;
        HttpSession session = request.getSession();
        if (session != null && session.getAttribute("loginReferer") != null) {
            targetUrl = session.getAttribute("loginReferer").toString();
            session.removeAttribute("loginReferer");
        }
        return targetUrl;
    }
}
