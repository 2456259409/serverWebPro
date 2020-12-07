package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.ClientUserMapper;
import com.renjian.model.ClientUser;
import org.springframework.stereotype.Service;

@Service
public class ClientUserService extends ServiceImpl<ClientUserMapper, ClientUser> {
}
