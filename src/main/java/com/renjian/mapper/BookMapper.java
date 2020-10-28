package com.renjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjian.model.Book;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
}
