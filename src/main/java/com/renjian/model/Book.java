package com.renjian.model;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("rj_book")
public class Book {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;
    private Long creatorId;
    private String author;
    private Integer count;
    private String bookName;
    private Date createTime;
    private String icon;
    /**
     * 0已经被删除；1待审核；2审核通过
     */
    private Integer status;

    /**
     * 创建者是否推荐
     */
    private Integer creatorRecommond;
    /**
     * 管理员是否推荐
     */
    private Integer mannagerRecommond;
    private String bookNumber;
    private String introduce;

    private String strTypes;

    private Date deadline;

    @TableField(exist = false)
    private User creator;

    public void setDeadLineTime(){
        deadline= DateUtil.parse(deadDay+" "+deadTime);
    }

    public void setTypesToStr(){
        strTypes= JSONUtil.toJsonStr(types);
    }

    @TableField(exist = false)
    private String deadDay;

    @TableField(exist = false)
    private String deadTime;

    @TableField(exist = false)
    private Long[] types;
}
