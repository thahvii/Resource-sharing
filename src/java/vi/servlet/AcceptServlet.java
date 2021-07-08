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

/**
 *
 * @author VI
 */
@WebServlet(name = "AcceptServlet", urlPatterns = {"/AcceptServlet"})
public class AcceptServlet extends HttpServlet {

    private final static String HOME_PAGE = "homePage";
    private final static String ERROR_PAGE = "errorPage";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Properties map = (Properties) getServletContext().getAttribute("map");
        String url = map.getProperty(HOME_PAGE);
        String id = request.getParameter("txtItemID");
        ItemDAO dao = new ItemDAO();
        try {
            boolean check = dao.ActionAccept(id);
            if (check) {
                List<ItemDTO> list = dao.getAllItemByStatus("Waiting");
//                dao.ChangeStatus(id);
                HttpSession session = request.getSession();
                request.setAttribute("SEARCHRESULT", list);
                url = map.getProperty(HOME_PAGE);
            } else {
                request.setAttribute("NULL", "No item");
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
