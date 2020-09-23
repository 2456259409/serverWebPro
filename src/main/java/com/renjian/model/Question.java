package com.renjian.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
import java.util.Set;

//@TableName("rj_question")
@Data
public class Question {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long paperId;
    private String title;
    private String type;
    private Integer code;

    private String allAnswer;
    private List<Answer> answer;

}
