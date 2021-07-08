/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vi.item.ItemDAO;
import vi.item.ItemDTO;
import vi.user.UserDAO;
import vi.user.UserDTO;

/**
 *
 * @author VI
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final static String LOGIN_PAGE = "loginPage";
    private final static String HOME_PAGE = "homePage";
//    private final static String ADMIN = "admin";
//    private final static String USER = "user";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userID = request.getParameter("txtUserID");
        String pw = request.getParameter("txtPassword");
        Properties map = (Properties) getServletContext().getAttribute("map");
        String url = map.getProperty(LOGIN_PAGE);
        try {
            UserDAO dao = new UserDAO();
            boolean check = dao.checkLogin(userID, pw);
            ItemDAO idao = new ItemDAO();
            List<ItemDTO> list = null;

            if (check == true) {
                UserDTO obj = dao.checkUser(userID);
                if (obj.getRole() != 1) {
                    list = idao.getAllItem();
                } else if (obj.getRole() == 1) {
                    list = idao.getAllItemByStatus("Waiting");
                } else {
                    request.setAttribute("NULL", "No item");
                }

                HttpSession session = request.getSession();
                request.setAttribute("SEARCHRESULT", list);
                session.setAttribute("USER", obj);
                url = map.getProperty(HOME_PAGE);

            } else {
                request.setAttribute("ERROR", "Login failed (userID and password incorrect)");
                url = map.getProperty(LOGIN_PAGE);
            }

        } catch (SQLException e) {
            log("LoginServlet error: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
