package com.renjian.interceptor;


import com.renjian.model.User;
import com.renjian.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }else{
            String token = request.getHeader("token");
            if(token!=null&&!"".equals(token)){
                try{
                    String[] params=token.split("<->");
                    Long userId=Long.valueOf(params[0]);
                    String salt=params[1];
                    User user = userService.getById(userId);
                    if(salt.equals(user.getSalt())){
                        return true;
                    }else{
                        return false;
                    }
                }catch (Exception e){
                    return false;
                }
            }else{
                return false;
            }
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}

