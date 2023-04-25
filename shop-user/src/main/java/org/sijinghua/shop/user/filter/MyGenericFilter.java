package org.sijinghua.shop.user.filter;

import brave.Span;
import brave.Tracer;
import org.springframework.cloud.sleuth.instrument.web.SleuthWebProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 6)
public class MyGenericFilter extends GenericFilterBean {

    private Pattern skipPattern = Pattern.compile(SleuthWebProperties.DEFAULT_SKIP_PATTERN);

    private final Tracer tracer;

    MyGenericFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest) || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("只支持HTTP访问");
        }
        Span currentSpan = tracer.currentSpan();
        if (currentSpan == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        boolean skipFlag = skipPattern.matcher(httpServletRequest.getRequestURI()).matches();
        if (!skipFlag) {
            String traceId = currentSpan.context().traceIdString();
            httpServletRequest.setAttribute("traceId", traceId);
            httpServletResponse.addHeader("SLEUTH-HEADER", traceId);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
