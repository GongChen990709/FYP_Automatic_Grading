package FYP19.Filter;

import FYP19.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//Restrict direct access to system files without login
public class systemFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Object user = req.getSession().getAttribute(Constants.USER_SESSION);

        if(req.getRequestURI().contains("StudentSetPwd.html")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(req.getRequestURI().contains("teacherSetPwd.html")){
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else if(user==null){
            resp.sendRedirect(req.getContextPath()+"/status/notLogin.html");
        }
        else{
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    public void destroy() {
    }
}
