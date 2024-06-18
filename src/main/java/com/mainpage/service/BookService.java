package com.mainpage.service;

import com.github.pagehelper.Page;
import com.mainpage.bean.Book;
import com.mainpage.bean.Record;

import java.util.List;

public interface BookService {
    List<Book> selectNewBooks(String status);
    List<Book> selectNewBooks();
    List<Book> selectNewBooks(Book book);
    List<Book> selectNewBooks(Integer  id);
    int updataBook(Book book);
    int insertBook(Book book);

    List<Record> getRecord(Integer id);
    List<Record> getRecord();
}
