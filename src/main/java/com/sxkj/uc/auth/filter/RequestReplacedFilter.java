package com.sxkj.uc.auth.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "requestReplacedFilter", urlPatterns = "/*")
public class RequestReplacedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest wrapper = null;
        if (servletRequest instanceof HttpServletRequest) {
            wrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if (wrapper == null) {
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            filterChain.doFilter(wrapper,servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
