package com.mainpage.controller;

import com.mainpage.bean.Book;
import com.mainpage.bean.Record;
import com.mainpage.service.BookService;
import com.spring.entity.PageResult;
import com.spring.entity.Result;
import com.spring.utiles.MyGetBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Controller
public class RecordController {
    @Autowired
    private BookService bookService;

    @ResponseBody
    @GetMapping("/record/searchRecords")
    public ModelAndView getRecords(HttpServletRequest request)
    {
        List<Record> record = bookService.getRecord();
        ModelAndView modelAndView = new ModelAndView();
        if(record.size()>0){
            modelAndView.addObject("pageResult",new PageResult(record.size(), record));
            modelAndView.addObject("search",record.get(0));
            modelAndView.addObject("gourl",request.getRequestURI());
            modelAndView.addObject("pageNum",1);
        }
        else modelAndView.addObject("pageResult","");
        modelAndView.setViewName("record");
        return modelAndView;
    }
    @ResponseBody
    @PostMapping("/record/searchRecords")
    public ModelAndView searchRecords(Record record, HttpServletRequest request)
    {
        List<Record> records = bookService.getRecord();
        List<Record> records2=accurateSearch(records,record);
        ModelAndView modelAndView = new ModelAndView();
        if(records2.size()>0){
            modelAndView.addObject("pageResult",new PageResult(records2.size(), records2));
            modelAndView.addObject("search",records2.get(0));
            modelAndView.addObject("gourl",request.getRequestURI());
            modelAndView.addObject("pageNum",1);
        }
        else modelAndView.addObject("pageResult","");
        modelAndView.setViewName("record");
        return modelAndView;
    }
    //
    public List<Record> accurateSearch(List<Record> books, Record book)
    {
        List<Integer> listId = new ArrayList<>();
        MyGetBook getBook = new MyGetBook(book);
        for(Record bk : books)
        {
          if(getBook.Name()) {//
              if (bk.getBorrower().contains(book.getBorrower()))
                  listId.add(bk.getId());
          }
          else if (getBook.Author()) {
              if(bk.getBookname().contains(book.getBookname()))
                  listId.add(bk.getId());
          }
          else if (getBook.allNoNull()) {//三个全有
                if(bk.getBorrower().contains(book.getBorrower())&&
                        bk.getBookname().contains(book.getBookname()))
                    listId.add(bk.getId());
          }
        }
        List<Integer> distinctList = new ArrayList<>(new LinkedHashSet<>(listId));
        List<Record> books2 = new ArrayList<>();
        System.out.println(listId);
        for(Integer id : distinctList)
        {
            books2.add(bookService.getRecord(id).get(0));
        }
        return books2;
    }
}
