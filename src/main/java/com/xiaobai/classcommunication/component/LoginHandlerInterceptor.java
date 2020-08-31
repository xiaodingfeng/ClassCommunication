package com.xiaobai.classcommunication.component;

import com.xiaobai.classcommunication.bean.User;
import com.xiaobai.classcommunication.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//拦截器，未登录不能看其他页面
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    UserSevice userSevice;
    //目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUser = request.getSession().getAttribute("LoginUser");
        if (loginUser==null){
//            request.getRequestDispatcher("/user/loginhtml").forward(request,response);
            return true;
        }
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
