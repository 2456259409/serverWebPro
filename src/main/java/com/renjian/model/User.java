package com.renjian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("rj_user")
public class User implements Serializable {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String icon;
    private String usermail;
    private String phone;
    private String salt;
    @TableField(exist = false)
    private String keyword;

    //0表示禁用，1表示正常使用
    private Integer status;
}
