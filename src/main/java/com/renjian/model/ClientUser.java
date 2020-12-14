package com.renjian.model;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("rj_client_user")
@Data
public class ClientUser {
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    private String username;

    private String password;

    private String icon;

    private String nickName;

    private String phone;

    private String email;

    private String salt;

    private Integer borrowCount;

    private Integer status;

//    private String MD5Password(){
//        SecureUtil.des();
//        return
//    }
}
