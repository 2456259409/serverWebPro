package com.renjian.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.mapper.PaperMapper;
import com.renjian.model.Answer;
import com.renjian.model.Paper;
import com.renjian.model.Question;
import com.renjian.model.params.SubmitPaper;
import com.renjian.service.AnswerService;
import com.renjian.service.PaperService;
import com.renjian.service.QuestionService;
import com.renjian.utils.CommonResult;
import com.renjian.utils.RUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Resource
    private AnswerService answerService;


    @PostMapping("/addPaper")
    public Object addPaper(@RequestBody JSONObject jsonObject){
        Paper paper = jsonObject.toBean(Paper.class);
        paper.setCreateTime(new Date());
        paper.setStatus(2);
        paperService.save(paper);
        int i=1;
        for (Question question:paper.getQuestion()){
            question.setSortInPaper(i++);
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

    @GetMapping("/get_papers")
    public Object getPapers(int pageSize,int pageNum,String keyword){
        QueryWrapper<Paper> wrapper=new QueryWrapper<Paper>().eq("status",2);
        wrapper.like(keyword!=null&&!"".equals(keyword),"title",keyword);
        String limit= RUtil.limitStr(pageSize,pageNum);
        wrapper.orderByDesc("create_time");
        wrapper.last(limit);
        List<Paper> list = paperService.list(wrapper);
        return new CommonResult().success(list);
    }


    @GetMapping("/getById/{id}")
    public Object getPaperById(@PathVariable Long id){
        QueryWrapper<Paper> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        Paper paper = paperService.getOne(wrapper);
        List<Question> questions = questionService.list(new QueryWrapper<Question>().eq("paper_id", id).orderByAsc("sort_in_paper"));
        for(Question q:questions){
            List<Answer> answers = JSONUtil.parseArray(q.getAllAnswer()).toList(Answer.class);
            q.setAnswer(answers);
        }
        paper.setQuestion(questions);
        return new CommonResult().success(paper);
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

    @PostMapping("/updatePaper")
    public Object updatePaper(@RequestBody JSONObject jsonObject){
        Paper paper=JSONUtil.toBean(jsonObject,Paper.class);
        try{
            paperService.updateById(paper);
            int i=1;
            for(Question question:paper.getQuestion()){
                question.setSortInPaper(i++);
                question.setAllAnswer(JSONUtil.toJsonStr(question.getAnswer()));
            }
            questionService.saveOrUpdateBatch(paper.getQuestion());
//            questionService.updateBatchById(paper.getQuestion());
            return new CommonResult().success("修改成功");
        }catch (Exception e){
            e.printStackTrace();
        }

        return new CommonResult().failed("修改失败");
    }

    @PostMapping("/submit_paper")
    public Object submitPaper(@RequestBody List<SubmitPaper> submit){
//        for (SubmitPaper paper : submit) {
//            paper.toCodesJson();
//        }
        answerService.saveOrUpdateBatch(submit);
        return new CommonResult().success();
    }


    @PostMapping("/get_fill_result")
    public Object getFillResult(SubmitPaper paper){
        List<Map<String, Object>> maps = answerService.listMaps(new QueryWrapper<SubmitPaper>()
                .eq(paper.getPaperId() == null, "paper_id", paper.getPaperId())
                .eq(paper.getUserId() == null, "user_id", paper.getUserId())
                .groupBy("question_id"));
        return new CommonResult().success(maps);
    }
}
