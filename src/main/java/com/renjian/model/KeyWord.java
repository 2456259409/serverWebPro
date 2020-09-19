package com.renjian.model;

import lombok.Data;

@Data
public class KeyWord {
    private String keyword;
    private Integer pageSize=10;
    private Integer pageNum=1;
}
