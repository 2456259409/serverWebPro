package com.renjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjian.model.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PaperMapper extends BaseMapper<Paper> {

    @Update("update rj_paper set status=#{status} where id = #{id}")
    boolean update(Long id,int status);
}
