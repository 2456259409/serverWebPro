package com.renjian.model.params;

import lombok.Data;

@Data
public class BorrowBookParam {
    private Integer pageNum;
    private Integer pageSize;
    private Integer key;
    private Long userId;
    private Long borrowId;
    private Integer isOutTime;
}
