package FYP19.Filter;

import FYP19.Entities.Students;
import FYP19.Entities.Teacher;
import FYP19.util.Constants;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class roleFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Object user = req.getSession().getAttribute(Constants.USER_SESSION);
        if(user instanceof Students){
            if(req.getRequestURI().contains("/teacher/") || req.getRequestURI().contains("/admin/")){
                resp.sendRedirect(req.getContextPath()+"/status/notLogin.html");
            }
            else {
                chain.doFilter(request,response);
            }
        }
        else if(user instanceof Teacher){
            if(req.getRequestURI().contains("/student/") || req.getRequestURI().contains("/admin/")){
                resp.sendRedirect(req.getContextPath()+"/status/notLogin.html");
            }
            else {
                chain.doFilter(request,response);
            }
        }
        else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {

    }
}
