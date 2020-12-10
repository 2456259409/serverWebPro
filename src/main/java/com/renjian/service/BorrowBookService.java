package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.BorrowBookMapper;
import com.renjian.model.BorrowBook;
import org.springframework.stereotype.Service;

@Service
public class BorrowBookService extends ServiceImpl<BorrowBookMapper, BorrowBook> {
}
