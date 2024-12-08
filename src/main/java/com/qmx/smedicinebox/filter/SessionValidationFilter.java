//package com.qmx.smedicinebox.filter;
//
//import com.qmx.smedicinebox.common.CurrentAdmin;
//import com.qmx.smedicinebox.common.CurrentUser;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//
//import java.io.IOException;
//
//@Slf4j
//@Component
//public class SessionValidationFilter extends GenericFilterBean {
//
//    private static final String LOGIN_PATH = "/pc/user/logIn"; // 登录接口的路径
//    private static final String SEND_EMAIL_RESET_PATH = "/pc/user/register";
//    private static final String VERIFICATION_PATH = "/pc/user/verification";
//
//
//    //硬件接口
//    private static final String MEDICINE_PICTURE_PATH = "/pc/medicationpicture/bin";
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        log.info("注册过滤器...");
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        HttpSession sessionq = httpRequest.getSession(false);
//        System.out.println("OPTIONS放行的session:"+sessionq);
//
//        if(httpRequest.getMethod().equals("OPTIONS")){
//            chain.doFilter(request, response);
//            return;
//        }
//        String requestUri = httpRequest.getRequestURI();
//
//        if (requestUri.startsWith("/pc")) {
//            chain.doFilter(request, response);
//            log.info(" /pc 的接口直接放行！！");
//            return;
//        }
//
//        // 如果是登录接口，则直接放行
//        if (requestUri.startsWith(LOGIN_PATH) || SEND_EMAIL_RESET_PATH.equals(requestUri) ||  VERIFICATION_PATH.equals(requestUri)||
//                MEDICINE_PICTURE_PATH.equals(requestUri)){
//            chain.doFilter(request, response);
//            log.info("直接放行");
//            return;
//        }
//
//        // 检查 session 的有效性
//        HttpSession session = httpRequest.getSession(false);
//        System.out.println("未放行的session:"+session);
//        if (session == null || (session.getAttribute("userid") == null && session.getAttribute("admin")==null)) {
//            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
//            return;
//        }
//
//        if (session.getAttribute("admin") != null) {
//            log.info("管理员登陆");
//            log.info("当前管理员账号id：{}", (Integer) session.getAttribute("userid"));
//            log.info("当前管理员账号：{}", (String) session.getAttribute("admin"));
//            CurrentAdmin.setCurrentAdmin((String) session.getAttribute("admin"));
//            CurrentUser.setCurrentUserId((Integer) session.getAttribute("userid"));
//            chain.doFilter(request, response);
//            CurrentAdmin.removeCurrentAdmin();
//            CurrentUser.removeCurrentUserId();
//        }else{
//            log.info("普通用户登陆");
//            log.info("当前用户id：{}", (Integer) session.getAttribute("userid"));
//            CurrentUser.setCurrentUserId((Integer) session.getAttribute("userid"));
//            chain.doFilter(request, response);
//            CurrentUser.removeCurrentUserId();
//        }
//    }
//
//}
