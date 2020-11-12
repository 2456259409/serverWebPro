package com.renjian.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.renjian.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface BookMapper extends BaseMapper<Book> {
    @Update("update rj_book set status=#{status} where id=#{id}")
    boolean updateBookStatus(Long id,Integer status);
}
