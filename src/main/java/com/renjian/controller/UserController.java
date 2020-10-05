package com.renjian.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.model.KeyWord;
import com.renjian.model.User;
import com.renjian.service.UserService;
import com.renjian.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RestTemplate restTemplate;

    private String resoleSalt="|-T-|";

    @PostMapping("/login")
    public Object login(User user){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq(user.getUsername()!=null,"username",user.getUsername());
//        wrapper.eq(user.getPassword()!=null,"password",SecureUtil.md5(resoleSalt.replace("T",user.getPassword())));
        User u = userService.getOne(wrapper);
        if(u==null){
            return new CommonResult().failed("登录失败，请检查用户名和密码");
        }
        if(u.getPassword().equals(SecureUtil.md5(resoleSalt.replace("T",u.getSalt()+user.getPassword())))){
            u.setPassword("");
            return new CommonResult().success(u);
        }
        return new CommonResult().failed("登录失败，请检查用户名和密码");
    }

    @PostMapping("/register")
    public Object register(User user){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq(user.getUsername()!=null,"username",user.getUsername());
        User u = userService.getOne(wrapper);
        if(u==null){
            String salt = RandomUtil.randomString(15);
            user.setSalt(salt);
            String s=resoleSalt.replace("T",salt+user.getPassword());
            String pass = SecureUtil.md5(s);
            user.setPassword(pass);
            userService.saveOrUpdate(user);
            return new CommonResult().success("注册成功");
        }
        return new CommonResult().failed("用户名已经存在");
    }

    @PostMapping("/updateUser")
    public Object updateUser(User user){
        QueryWrapper<User> wrapper=new QueryWrapper<>();
        wrapper.eq("id",user.getId());
        User one = userService.getOne(wrapper);
        if(one!=null){
            if(user.getPassword()!=null){
                String s=resoleSalt.replace("T",user.getSalt()+user.getPassword());
                String pass = SecureUtil.md5(s);
                user.setPassword(pass);
            }
            userService.updateById(user);
            return new CommonResult().success("修改成功");
        }
        return new CommonResult().failed("用户不存在");
    }

    @PostMapping("/search")
    public Object searchMusic(KeyWord keyWord){
        String url="https://c.y.qq.com/soso/fcgi-bin/client_search_cp?ct=24&qqmusic_ver=1298&new_json=1&remoteplace=txt.yqq.song&searchid=71047993326431519&t=0&aggr=1&cr=1&catZhida=1&lossless=0&flag_qc=0&p=pageNum&n=pageSize&w=keyword&g_tk_new_20200303=916960263&g_tk=916960263&loginUin=2456259409&hostUin=0&format=json&inCharset=utf8&outCharset=utf-8&notice=0&platform=yqq.json&needNewCode=0";
        url=url.replace("pageSize",String.valueOf(keyWord.getPageSize())).replace("pageNum",String.valueOf(keyWord.getPageNum())).replace("keyword",keyWord.getKeyword());
        String result=restTemplate.getForObject(url,String.class);
        return new CommonResult().success(result);
    }
}
