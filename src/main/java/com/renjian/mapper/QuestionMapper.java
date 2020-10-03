package com.renjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjian.model.Question;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper extends BaseMapper<Question> {
}
