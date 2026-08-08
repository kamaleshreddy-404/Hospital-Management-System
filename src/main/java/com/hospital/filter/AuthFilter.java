package com.hospital.filter;

import com.hospital.model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/admin/*", "/doctor/*", "/receptionist/*", "/pharmacist/*", "/patient/*"})
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        User user = (session != null) ? (User) session.getAttribute("user") : null;
        String uri = httpRequest.getRequestURI();

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Please login to access this section");
            return;
        }

        int roleId = user.getRoleId();

        // Role-based Access Control checks
        if (uri.contains("/admin/") && roleId != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Unauthorized access (Admin required)");
            return;
        }
        if (uri.contains("/doctor/") && roleId != 2 && roleId != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Unauthorized access (Doctor required)");
            return;
        }
        if (uri.contains("/receptionist/") && roleId != 3 && roleId != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Unauthorized access (Receptionist required)");
            return;
        }
        if (uri.contains("/pharmacist/") && roleId != 4 && roleId != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Unauthorized access (Pharmacist required)");
            return;
        }
        if (uri.contains("/patient/") && roleId != 5 && roleId != 1) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login?error=Unauthorized access (Patient required)");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
