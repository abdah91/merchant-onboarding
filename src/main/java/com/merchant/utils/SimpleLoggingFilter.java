package com.merchant.utils;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleLoggingFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(SimpleLoggingFilter .class);

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        String url = request.getServletPath();
        logger.info(url + " - " + request.getMethod());
        chain.doFilter(req, res);
        logger.info(url + " - " + response.getStatus());
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

}