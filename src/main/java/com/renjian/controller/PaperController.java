package com.renjian.controller;

import com.renjian.model.Paper;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/paper")
public class PaperController {

    @PostMapping("/addPaper")
    public Object addPaper(@RequestBody Paper paper){

        System.out.println(paper);
        return null;
    }
}
