package com.renjian.model.params;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rj_answer")
public class SubmitPaper {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    private Long questionId;

    private Long paperId;

    private Long userId;
    private String name;
    private Integer type;
    @TableField(exist = false)
    private Integer[] codes;

    private String codesJson;
    private String content;

    public void toCodesJson(){
        codesJson= JSONUtil.toJsonStr(codes);
    }

}
