package com.renjian.controller;

import com.renjian.utils.CommonResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

    @GetMapping("/get_all")
    public Object getAllBook(){

        return new CommonResult().success("ok123");
    }
}
