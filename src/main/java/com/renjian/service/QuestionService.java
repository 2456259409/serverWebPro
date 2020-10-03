package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.QuestionMapper;
import com.renjian.model.Question;
import org.springframework.stereotype.Service;

@Service
public class QuestionService extends ServiceImpl<QuestionMapper, Question> {
}
