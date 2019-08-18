package cn.mbdoge.blog.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author jyx
 */
@Slf4j
public class CustomParamFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            long time = Long.parseLong(request.getParameter("timestamp"));
            log.debug("time = {}", time);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            log.debug("");
        }
        chain.doFilter(request, response);
    }
}
