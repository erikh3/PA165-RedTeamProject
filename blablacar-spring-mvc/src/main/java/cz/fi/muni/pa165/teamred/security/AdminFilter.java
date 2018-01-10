package cz.fi.muni.pa165.teamred.security;

import cz.fi.muni.pa165.teamred.config.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO: LIST URL WITH ONLY ADMIN ACCESS
@WebFilter(urlPatterns = {"/comment/manage","/comment/delete"})
public class AdminFilter implements Filter {

    @Autowired
    private UserSession userSession;

    private WebApplicationContext appContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        appContext = WebApplicationContextUtils.
                getRequiredWebApplicationContext(filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        userSession = appContext.getBean(UserSession.class);

        if (userSession.getUserId() == null || !userSession.getIsAdmin()) {
            response401((HttpServletResponse) servletResponse);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1>You do not have ADMIN permissions to access...</body></html>");
    }
}
