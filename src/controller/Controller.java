package controller;

import model.*;
import view.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "Controller", urlPatterns = {"/"})
public class Controller extends HttpServlet {
    private SelectView page;
    private Facade facade;

    @Override
    public void init() {
        page = new SelectView();
        facade = new Facade();
    }

    /**
     * Main function of the Controller servlet. receives requests and responses and acts in accordance to their contents:
     * <ul>
     *     <li>Logs the user in by calling the {@code signUserIn()} function;</li>
     *     <li>Signs the user up by calling the {@code signUserUp()} function;</li>
     *     <li>Logs the user out by calling the {@code signUserOut()} function;</li>
     *     <li>Updates the user's last access to the application.</li>
     * </ul>
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        switch (action) {
            case "signIn":
                /* User came from signIn.jsp. User clicked on the signIn button. */
                signUserIn(request, response);
                break;
            case "signUp":
                /* User came from signIn.jsp. User clicked on the signUp button. */
                signUserUp(request, response);
                break;
            case "Logout":
                /* User came from account.jsp. User clicked on the Logout button. */
                signUserOut(request, response);
                break;
            case "Add":
            case "Remove":
                /* User came from account.jsp. User clicked on the Remove button. */
                /* OR */
                /* User came from account.jsp. User clicked on the Add button. */
                addOrRemoveSet(request, response, action);
                break;
            default:
                /* The web page was changed somehow. */
                handleError(request, response, null, null);
                break;
        }
    }

    /**
     * Decides on the first page the user sees. Will show the {@code signIn.jsp} page by default.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        page.signIn(request, response);
    }

    /**
     * Signs the user in by authenticating them using the {@code DataAccess.authenticateUser()} function.
     * Calls the {@code showPages()} function.
     * @param request the servlet request.
     * @param response the servlet response.
     */
    private void signUserIn(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");
        // Authenticate the user.
        boolean result = facade.authenticateUser(email, password);
        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(5*60);
        showPages(request, response, email, result, true, "LogIn");
    }

    /**
     * Signs the user up by authenticating them using the {@code DataAccess.newUser()} function.
     * Calls the {@code showPages()} function.
     * @param request the servlet request.
     * @param response the servlet response.
     */
    private void signUserUp(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("passwordConfirmation");
        // Create a new user.
        boolean result = facade.newUser(email, password, passwordConfirmation);
        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(5*60);
        showPages(request, response, email, result, true, "SignUp");
    }

    /**
     * Signs the user out. Will check if the user wished to delete their account and act on it.
     * @param request the servlet request.
     * @param response the servlet response.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    private void signUserOut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("check");
        // "check" is the name of the input checkbox on the account page for the Delete User on Logout option.
        request.getSession().setAttribute("check", action);
        page.signOut(request, response);
        HttpSession oldSession = request.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        try {
            // If the "Delete User on Logout" checkbox was checked, action will be equal to "delete".
            if(action.equals("delete")) {
                String email = request.getParameter("email").toLowerCase();
                facade.deleteUser(email);
            }
        } catch (NullPointerException ex) {
            /* Will be caught if the user did not check the "Delete User on Logout" box. */
            /* Comparing a String with null using String.equals() raises this exception. */
            /* Do nothing. */
        }
    }

    /**
     * Adds or removes a Key/Value pair depending on the {@code action} parameter.
     * @param request the servlet request.
     * @param response the servlet response.
     * @param action will be either "Add" or "Remove" here.
     */
    private void addOrRemoveSet(HttpServletRequest request, HttpServletResponse response, String action) {
        String email = request.getParameter("email").toLowerCase();
        String key = request.getParameter("Key");
        String value = request.getParameter("Value");
        String error = null;
        boolean result = false;
        // Add or remove set
        switch (action) {
            case "Add":
                result = facade.userAddSet(email, key, value);
                break;
            case "Remove":
                result = facade.userRemoveSet(email, key, value);
                break;
        }
        if(!result) {
            // User attempted to add a duplicate key, or to remove one that doesn't exist.
            // Page will be reloaded and a warning will appear.
            error = "AddRemove";
        }
        showPages(request, response, email, result, false, error);
    }

    /**
     * Shows either the {@code account.jsp} page or the  ({@code error.jsp} page depending on the {@code result} parameter.
     * Sends {@code user} to the requested page as an attribute for further use.
     * @param request the servlet request.
     * @param response the servlet response.
     * @param email the user's email. Used to get an instance of the user to send to the pages.
     * @param result will be {@code True} if the user was authenticated or created successfully. {@code False} otherwise.
     * @param login will be {@code True} if the user is logging in. {@code False} otherwise.
     * @param error the error code of the page, in case an error was detected.
     */
    private void showPages(HttpServletRequest request, HttpServletResponse response,
                           String email, boolean result, boolean login, String error) {
        User user = facade.getUserByEmail(email, login);
        try {
            if(result) {
                // Send an instance of the user over to the page for further use.
                request.getSession().setAttribute("user", user);
                // In the case where the add or remove operations failed in account.jsp
                request.getSession().setAttribute("error", error);
                page.account(request, response);
            }
            else {
                // User either doesn't exist or typed the wrong password.
                handleError(request, response, email, error);
            }
        } catch (IOException | ServletException e) {
            e.printStackTrace();
        }
    }

    /**
     * Error handling. Showing The correct page upon weird behaviour.
     * @param request the servlet request.
     * @param response the servlet response.
     * @param email the user's email. Used to get an instance of the user to send to the pages.
     * @param error the error code of the page, in case an error was detected.
     * @throws IOException I/O Exception.
     * @throws ServletException Servlet Exception.
     */
    private void handleError(HttpServletRequest request, HttpServletResponse response, String email, String error)
            throws IOException, ServletException {
        // Where did this error originate from?
        request.getSession().setAttribute("error", error);
        try {
            if(error.equals("AddRemove")) {
                // No need to quit the Account page, just update it.
                showPages(request, response, email, true, false, error);
            } else {
                page.error(request, response);
            }
        } catch (NullPointerException ex) {
            /* Comparing a String with null using String.equals() raises this exception. */
            page.error(request, response);
        }
    }
}
