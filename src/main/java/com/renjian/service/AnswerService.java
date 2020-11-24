package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.AnswerMapper;
import com.renjian.model.params.SubmitPaper;
import org.springframework.stereotype.Service;

@Service
public class AnswerService extends ServiceImpl<AnswerMapper, SubmitPaper> {
}
