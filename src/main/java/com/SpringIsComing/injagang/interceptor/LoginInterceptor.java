package com.SpringIsComing.injagang.interceptor;

import com.SpringIsComing.injagang.session.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();


        log.info("개새기들1 ={}",request.getMethod());
        log.info("개새기들2={}",request.getRequestURL());

        log.info("URI={}", requestURI);

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute(SessionConst.LOGIN_SESSION) == null) {
//            response.sendRedirect("/login?redirectURL=" + requestURI);
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('로그인을 해 주세요.'); location.href='/login?redirectURL=" + requestURI + "';</script>");
            out.flush();


            return false;
        }

        return true;
    }

}
