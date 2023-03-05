package edu.poly.shop.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("pre hendler of request" + request.getRequestURI());   //request.getRequestURI() with return the URL we input by hand in URL parameter. exg: /login
        if (session.getAttribute("username") != null){
        return true;  // If the 'username' attribute exists, the user is authenticated, so return true to proceed with the request
        }
        // If the 'username' attribute does not exist, the user is not authenticated, so save the request URI in the 'redirect-uri' attribute of the session object, after login, will redirect to this uri
        session.setAttribute("redirect-uri", request.getRequestURI());
        // Redirect the user to the login page
        response.sendRedirect("/alogin");
        return false;
        }

        /*Get username rồi kiểm tra xem có tồn tại trong sesstion không, nếu có nghĩa là đã login -> trả về true... Còn nếu không thì là chưa login -> trả về false, redirect đến màn login*/
        }
