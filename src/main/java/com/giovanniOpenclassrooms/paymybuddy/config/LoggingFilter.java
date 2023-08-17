package com.giovanniOpenclassrooms.paymybuddy.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Component
@Log4j2
public class LoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(requestWrapper, responseWrapper);


        log.info("STARTING PROCESSING : METHOD={}; REQUEST_URI={}; PARAMETERS={}; ", request.getMethod(), request.getRequestURI(), getParameters(request));
        log.info("FINISHED PROCESSING : RESPONSE CODE={}; ", response.getStatus());

        responseWrapper.copyBodyToResponse();
    }

    private String getParameters(final HttpServletRequest request) {
        final StringBuilder posted = new StringBuilder();
        final Enumeration<?> e = request.getParameterNames();
        if (e != null) {
            posted.append("?");
            while (e.hasMoreElements()) {
                if (posted.length() > 1) {
                    posted.append("&");
                }
                final String curr = (String) e.nextElement();
                posted.append(curr)
                        .append("=")
                        .append(request.getParameter(curr));
            }
        }
        return posted.toString();
    }

}