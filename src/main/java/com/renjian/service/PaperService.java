package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.PaperMapper;
import com.renjian.model.Paper;
import org.springframework.stereotype.Service;

@Service
public class PaperService extends ServiceImpl<PaperMapper, Paper> {
}
