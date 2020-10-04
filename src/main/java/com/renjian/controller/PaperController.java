package com.renjian.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.mapper.PaperMapper;
import com.renjian.model.Paper;
import com.renjian.model.Question;
import com.renjian.service.PaperService;
import com.renjian.service.QuestionService;
import com.renjian.utils.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/paper")
public class PaperController {


    @Resource
    private QuestionService questionService;
    @Resource
    private PaperService paperService;

    @Resource
    private PaperMapper paperMapper;


    @PostMapping("/addPaper")
    public Object addPaper(@RequestBody JSONObject jsonObject){
        Paper paper = jsonObject.toBean(Paper.class);
        paper.setCreateTime(new Date());
        paper.setStatus(2);
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

    @GetMapping("/get_paper_byId/{userId}")
    public Object getPaperByUserId(@PathVariable Long userId){
        QueryWrapper<Paper> wrapper=new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        wrapper.in("status",1,2);
        List<Paper> papers = paperService.list(wrapper);
        return new CommonResult().success(papers);
    }

    @GetMapping("/deletePaper/{id}")
    public Object deletePaperById(@PathVariable Long id){

        QueryWrapper<Paper> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        boolean is = paperMapper.update(id, 0);
        if(is){
            return  new CommonResult().success("删除成功");
        }
        return  new CommonResult().failed("删除失败");
    }
}
