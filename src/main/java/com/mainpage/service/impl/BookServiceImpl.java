package com.mainpage.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mainpage.bean.Book;
import com.mainpage.bean.Record;
import com.mainpage.dao.BookMapper;
import com.mainpage.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookMapper bookMapper;
    @Override
    public List<Book> selectNewBooks()
    {
        Book book = new Book();
        return bookMapper.selectNewBooks(book);
    }

    @Override
    public List<Book> selectNewBooks(Book book) {
        return bookMapper.selectNewBooks(book);
    }

    @Override
    public List<Book> selectNewBooks(Integer id) {
        Book book = new Book();
        book.setId(id);
        return bookMapper.selectNewBooks(book);
    }

    @Override
    public int updataBook(Book book) {
        return bookMapper.updateBook(book);
    }

    @Override
    public int insertBook(Book book) {
        return bookMapper.insertBook(book);
    }

    @Override
    public List<Record> getRecord(Integer id) {
        return bookMapper.getRecord(id);
    }
    @Override
    public List<Record> getRecord() {
        return bookMapper.getRecord(0);
    }

    @Override
    public List<Book> selectNewBooks(String status)
    {
        Book book = new Book();
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(status);
        boolean isNumeric = matcher.matches();
        if(isNumeric)
        {
            book.setStatus(status);
        }
        else
        {
            book.setName(status);
        }
        return bookMapper.selectNewBooks(book);
    }
}
