package com.renjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjian.model.params.SubmitPaper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnswerMapper extends BaseMapper<SubmitPaper> {

}
