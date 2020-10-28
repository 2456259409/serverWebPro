package com.renjian.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renjian.mapper.BookMapper;
import com.renjian.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService extends ServiceImpl<BookMapper, Book> {
}
