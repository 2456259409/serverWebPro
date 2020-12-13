package com.renjian.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.model.ClientUser;
import com.renjian.service.ClientUserService;
import com.renjian.utils.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/client_user")
@CrossOrigin
public class ClientUserController {

    @Resource
    ClientUserService clientUserService;

    private String  BASE_STR="<m-m>";

    @PostMapping("/login")
    public Object Login(@RequestBody ClientUser user){
        ClientUser client = clientUserService.getOne(new QueryWrapper<ClientUser>().eq("username", user.getUsername()));
        if(client==null){
            return new CommonResult().failed("用户名或密码错误");
        }
        if(client.getPassword().equals(SecureUtil.sha1(client.getSalt()+BASE_STR.replace("-",user.getPassword())))){
            client.setPassword("");
            return new CommonResult().success(client);
        }else {
            return new CommonResult().failed("用户名或密码错误");
        }
    }

    @PostMapping("/register")
    public Object Register(@RequestBody ClientUser user){
        ClientUser client = clientUserService.getOne(new QueryWrapper<ClientUser>().eq("username", user.getUsername()));
        if(client!=null){
            return new CommonResult().failed("用户名已经存在");
        }
        String salt = RandomUtil.randomString(15);
        user.setSalt(salt);
        user.setPassword(SecureUtil.sha1(salt+BASE_STR.replace("-",user.getPassword())));
        boolean b = clientUserService.saveOrUpdate(user);
        if(b){
            return new CommonResult().success("注册成功");
        }else{
            return new CommonResult().failed("注册失败");
        }
    }


}
