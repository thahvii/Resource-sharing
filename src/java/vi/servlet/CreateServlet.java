/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "CreateServlet", urlPatterns = {"/CreateServlet"})
public class CreateServlet extends HttpServlet {

    private static final String CREATE_PAGE = "createPage";
    private static final String HOME_PAGE = "homePage";
    private static final String LOGIN_PAGE = "loginPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Properties map = (Properties) getServletContext().getAttribute("map");
        String url = map.getProperty(CREATE_PAGE);
        try {

            String userID = request.getParameter("txtUserID");
            String password = request.getParameter("txtPassword");
            String name = request.getParameter("txtName");
            String phone = request.getParameter("txtPhone");
            String address = request.getParameter("txtAddress");
            String date = request.getParameter("txtDate");
            boolean valid = true;

            UserDAO dao = new UserDAO();
            if (dao.checkUser(userID) != null) {
                request.setAttribute("EXIST", "UserID is existed - Create new account failed!");
                valid = false;
            }
            UserDTO user = new UserDTO(userID, password, name, phone, address, date, 3, "New");
            if (valid) {
                if (dao.createNewAccount(user)) {
                    ItemDAO idao = new ItemDAO();
                    List<ItemDTO> list = idao.getAllItem();
                    HttpSession session = request.getSession();
                    request.setAttribute("SEARCHRESULT", list);
                    url = map.getProperty(HOME_PAGE);
                } else {
                    request.setAttribute("ERROR", "Create new account failed!");
                }
            } else {
                request.setAttribute("ERROR", "Create new account failed!");
            }

        } catch (Exception e) {
            log(e.getMessage());
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
