package com.renjian.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.renjian.model.ClientUser;
import com.renjian.model.KeyWord;
import com.renjian.model.User;
import com.renjian.service.ClientUserService;
import com.renjian.utils.CommonResult;
import com.renjian.utils.RUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @PostMapping("/get_users")
    public Object getUsers(KeyWord keyWord){
        if(keyWord.getUserId()!=10){
            return new CommonResult().failed("您没有操作权限");
        }
        Integer word=Integer.valueOf(keyWord.getKeyword());
        QueryWrapper<ClientUser> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("id").last(RUtil.limitStr(keyWord.getPageSize(), keyWord.getPageNum()));
        if(word==2){
            wrapper.eq("status",0);
        }else if(word==3){
            wrapper.eq("status",1);
        }

        List<ClientUser> list = clientUserService.list(wrapper);
        list.forEach(item->{
            item.setPassword("");
            item.setSalt("");
        });
        return new CommonResult().success(list);
    }

    @PostMapping("/disable_user")
    public Object disableUser(KeyWord keyWord){

        if(keyWord.getMasterUserId()!=10){
            return new CommonResult().failed("没有操作权限");
        }
        clientUserService.update(new UpdateWrapper<ClientUser>().eq("id",keyWord.getUserId()).set("status",0));
        return new CommonResult().success("禁用成功");
    }

    @PostMapping("/able_user")
    public Object ableUser(KeyWord keyWord){

        if(keyWord.getMasterUserId()!=10){
            return new CommonResult().failed("没有操作权限");
        }
        clientUserService.update(new UpdateWrapper<ClientUser>().eq("id",keyWord.getUserId()).set("status",1));
        return new CommonResult().success("解禁成功");
    }


}
