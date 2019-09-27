package controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/app/*")
public class LoginFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        this.context = fConfig.getServletContext();
        this.context.log("AuthenticationFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String loginURI = request.getContextPath() + "/signIn.jsp";

        response.setHeader("Cache-Control","no-cache, no-store, must-revalidate, post-check=0, pre-check=0, max-age=0");
        response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
        response.setDateHeader("Expires", 0); // Proxies.

        HttpSession session = request.getSession(false);

        if (session == null) {   //checking whether the session exists
            this.context.log("Unauthorized access request");
            response.sendRedirect(request.getContextPath() + loginURI);
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }
}
