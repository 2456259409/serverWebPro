package com.renjian.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.renjian.mapper.BookMapper;
import com.renjian.model.Book;
import com.renjian.model.params.SubmitPaper;
import com.renjian.service.BookService;
import com.renjian.utils.CommonResult;
import com.renjian.utils.RUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

    @Resource
    BookService bookService;

    @Resource
    BookMapper bookMapper;

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

    @GetMapping("/get_books")
    public Object getBooks(int keyword,Long userId,int pageNum,int pageSize){
        QueryWrapper<Book> wrapper=new QueryWrapper<>();
        if(userId!=10){
            wrapper.eq("creator_id",userId);
        }
        if(keyword==1){
            wrapper.in("status",1,2);
        }else if(keyword==2){
            wrapper.eq("status",1);
        }else if(keyword==3){
            wrapper.eq("status",2);
        }

        StringBuffer sb=new StringBuffer();
        sb.append("limit ");
        sb.append(pageNum*pageSize);
        sb.append(",");
        sb.append((pageNum+1)*pageSize);
        wrapper.last(sb.toString());
        List<Book> list = bookService.list(wrapper);
        return new CommonResult().success(list);
    }

    @GetMapping("/get_client_books")
    public Object getBooks(int pageNum,int pageSize,String keyword){
        QueryWrapper<Book> wrapper=new QueryWrapper<>();
        wrapper.eq("status",2);
        wrapper.like(keyword!=null&&!"".equals(keyword),"book_name",keyword);
        String s = RUtil.limitStr(pageSize, pageNum);
        wrapper.last(s);
        List<Book> list = bookService.list(wrapper);
        return new CommonResult().success(list);
    }

    @GetMapping("/delete_book")
    public Object deleteBook(Long id){

        boolean b = bookMapper.updateBookStatus(id, 0);
        if(b){
            return new CommonResult().success("删除成功");
        }else{
            return new CommonResult().failed("删除失败");
        }
    }

    @PostMapping("/update_book")
    public Object updateBook(@RequestBody JSONObject json){
        Book book = JSONUtil.toBean(json, Book.class);
        boolean b = bookService.saveOrUpdate(book);
        if(b){
            return new CommonResult().success("修改成功");
        }else{
            return new CommonResult().failed("修改失败");
        }
    }

    @GetMapping("/through_book")
    public Object throughBook(Long id,Long userId){
        if(userId!=10){
            return new CommonResult().failed("您无权审核该图书");
        }
        boolean b = bookMapper.updateBookStatus(id, 2);
        if(b){
            return new CommonResult().success("审核成功");
        }else{
            return new CommonResult().failed("审核失败");
        }
    }
}
