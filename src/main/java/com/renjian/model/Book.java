package com.renjian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("rj_book")
public class Book {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;
    private String author;
    private Integer count;
    private Date createTime;
    /**
     * 0已经被删除；1待审核；2审核通过
     */
    private Integer status;
    private String bookNumber;
    private String introduce;
}
