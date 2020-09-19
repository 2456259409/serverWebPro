package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.UserMapper;
import com.renjian.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
}
