package com.renjian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@TableName("rj_paper")
@Data
public class Paper {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String title;
    private Date createTime;
    private Long userId;
    private String createUsername;
    @TableField(exist = false)
    private List<Question> question;

}
