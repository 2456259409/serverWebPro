package com.renjian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@TableName("rj_borrow_book")
@Data
public class BorrowBook {

    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

//    @DateTimeFormat(pattern = "yy-MM-dd HH:mm")
    private Timestamp returnTime;
//    @DateTimeFormat(pattern = "yy-MM-dd HH:mm")
    private Timestamp borrowTime;
    private Long userId;

    private Long bookId;

    private String bookName;

    //状态 0借阅状态 1正常归还（规定时间期内）2超时归还 3损坏 4丢弃 5待审核
    private Integer status;

    //是否逾期 0逾期，1未逾期
    private Integer isOutTime;

    @TableField(exist = false)
    private Integer borrowCount;

}
