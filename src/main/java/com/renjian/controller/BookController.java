package com.renjian.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.model.Book;
import com.renjian.service.BookService;
import com.renjian.utils.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

    @Resource
    BookService bookService;

    @GetMapping("/get_all")
    public Object getAllBook(){

        return new CommonResult().success("ok123");
    }

    @PostMapping("/add_book")
    public Object addABook(@RequestBody JSONObject jsonObject){

        Book book=JSONUtil.toBean(jsonObject, Book.class);
        if(book==null){
            return new CommonResult().failed("请传入书籍");
        }
        if(bookService.getOne(new QueryWrapper<Book>().eq("creator_id",book.getCreatorId()).eq("author",book.getAuthor()).eq("book_name",book.getBookName()))!=null){
            return new CommonResult().failed("该书籍已经在库中");
        }
        book.setStatus(1);
        book.setCreateTime(new Date());
        book.setTypesToStr();
        if(book.getBookNumber()==null){
            book.setBookNumber(RandomUtil.randomNumbers(15));
        }
        boolean b = bookService.saveOrUpdate(book);
        if(b){
            return new CommonResult().success("添加成功");
        }else{
            return new CommonResult().failed("保存失败!!!");
        }
    }
}
