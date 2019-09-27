package view;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Redirects the user to the page they need to see.
 */
public class SelectView {
    private RequestDispatcher requestDispatcher;

    /**
     * Redirects the user to the {@code signIn.jsp} page.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    public void signIn(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        requestDispatcher = request.getRequestDispatcher("signIn.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Redirects the user to the {@code account.jsp} page.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    public void account(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        requestDispatcher = request.getRequestDispatcher("app/account.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Redirects the user to the {@code signOut.jsp} page.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        requestDispatcher = request.getRequestDispatcher("app/signOut.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * Redirects the user to the {@code error.jsp} page.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    public void error(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        requestDispatcher = request.getRequestDispatcher("error.jsp");
        requestDispatcher.forward(request, response);
    }
}
