package com.renjian.interceptor;


import cn.hutool.json.JSONUtil;
import com.renjian.model.User;
import com.renjian.service.UserService;
import com.renjian.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }else{
            String token = request.getHeader("token");
            String type = request.getHeader("type");
            if("back-end".equals(type)){
                if(token!=null&&!"".equals(token)){
                    try{
                        String[] params=token.split("<->");
                        Long userId=Long.valueOf(params[0]);
                        String salt=params[1];
                        User user = userService.getById(userId);
                        if(user.getStatus()==0){
                            responseStr(response,"您没有操作权限",200);
                            return false;
                        }
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

            if("client".equals(type)){
                if(token!=null&&!"".equals(token)){
                    try{
                        String[] params=token.split("<->");
                        Long userId=Long.valueOf(params[0]);
                        if(userId==16){
                            return true;
                        }
                    }catch (Exception e){
                        return false;
                    }
                }
                return false;
            }
            return false;

        }

    }


    public void responseStr(HttpServletResponse response,String content,int code) throws IOException {
        response.setStatus(code);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.print(JSONUtil.toJsonStr(new CommonResult().failed(content)));
        writer.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}

