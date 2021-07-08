/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vi.filter;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import vi.user.UserDTO;

/**
 *
 * @author VI
 */
@WebFilter(filterName = "FilterDispatcher", urlPatterns = {"/*"})
public class FilterDispatcher implements Filter {

//    private final static String ADMIN_SERVLET = "adminServlet";
//    private final static String USER_SERVLET = "userServlet";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        try {
            HttpSession session = req.getSession();
            UserDTO user = (UserDTO) session.getAttribute("USER");
            String uri = req.getRequestURI();
            String url = null;

            int lastIndex = uri.lastIndexOf("/");
            String resource = uri.substring(lastIndex + 1);
            ServletContext contex = req.getServletContext();
            Properties map = (Properties) contex.getAttribute("map");
            url = map.getProperty(resource);

//            if (!resource.isEmpty() && user != null) {
//                if (user.getRole() == 1) {
//                    url = map.getProperty(ADMIN_SERVLET);
//                }
//                if (user.getRole() != 1) {
//                    url = map.getProperty(USER_SERVLET);
//                }
//            }

            if (url != null) {
                req.getRequestDispatcher(url).forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    @Override
    public void init(FilterConfig filterConfig) {

    }

}
