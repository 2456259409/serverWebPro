package com.renjian.model;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

@TableName("rj_question")
@Data
public class Question {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long paperId;
    private String title;
    private String type;
    private Integer code;

    private String textarea;

    private String allAnswer;
    @TableField(exist = false)
    private List<Answer> answer;

    public void answerToString(){
        allAnswer= JSONUtil.toJsonStr(answer);
    }

}
