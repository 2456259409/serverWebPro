package com.renjian.controller;

import cn.hutool.json.JSONObject;
import com.renjian.model.Paper;
import com.renjian.model.Question;
import com.renjian.service.PaperService;
import com.renjian.service.QuestionService;
import com.renjian.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/api/paper")
public class PaperController {


    @Resource
    private QuestionService questionService;
    @Resource
    private PaperService paperService;


    @PostMapping("/addPaper")
    public Object addPaper(@RequestBody JSONObject jsonObject){
        Paper paper = jsonObject.toBean(Paper.class);
        paper.setCreateTime(new Date());
        paperService.save(paper);
        for (Question question:paper.getQuestion()){
            question.setPaperId(paper.getId());
            question.answerToString();
        }
        if(paper.getQuestion().size()>0){
            questionService.saveBatch(paper.getQuestion());
        }
        return new CommonResult().success("提交成功");
    }
}
