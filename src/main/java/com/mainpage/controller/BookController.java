package com.mainpage.controller;


import com.mainpage.bean.Book;
import com.mainpage.service.BookService;
import com.spring.entity.PageResult;
import com.spring.entity.Result;
import com.spring.utiles.MyGetBook;
import com.users.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    //首页
    @GetMapping("/selectNewbooks")
    public ModelAndView getBook(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("pageResult", getBooks("0"));
        modelAndView.setViewName("books_new");
        return modelAndView;
    }
    //图书借阅
    @ResponseBody
    @GetMapping ("/search")
    public  ModelAndView showSerchBook(HttpServletRequest request)
    {
        ModelAndView model = new ModelAndView();
        PageResult pageResult = getBooks();
        model.setViewName("books");
        model.addObject("pageResult",pageResult);
        model.addObject("search",pageResult.getRows().get(0));
        model.addObject("gourl",request.getRequestURI());
        model.addObject("pageNum",1);
        //request.setAttribute("pageResult", getBooks("0"));
        return model;
    }
    //图书借阅查询
    @PostMapping("/search")
    public String selectBook(Book book,HttpServletRequest request)
    {
        List<Book> books=getBooks("0").getRows();
        List<Book> bks = this.accurateSearch(books,book);
        request.setAttribute("pageResult",new PageResult(bks.size(), bks));
       // request.setAttribute("pageResult", getBooks(book));
        return "books";
    }
    //当前借阅
    @GetMapping("/searchBorrowed")
    @ResponseBody
    public ModelAndView getBrrowBook(HttpServletRequest request)
    {
        long pageNumber=getBooks("1").getTotal()+getBooks("2").getTotal();
        List borrowBook=getBooks("1").getRows();
        List returnBook=getBooks("2").getRows();
        if(borrowBook!=null && returnBook!=null)
        borrowBook.addAll(returnBook);
        ModelAndView returnView=new ModelAndView();
        returnView.addObject("pageResult",new PageResult(pageNumber,borrowBook));
        returnView.addObject("search",borrowBook.get(0));
        returnView.addObject("pageNum",1);
        returnView.addObject("gourl",request.getRequestURI());
        returnView.setViewName("book_borrowed");
        //request.setAttribute("pageResult",new PageResult(pageNumber,borrowBook));
        return returnView;
    }
    //当前借阅查询
    @PostMapping("/searchBorrowed")
    public String selectBrrowBook(Book book,HttpServletRequest request)
    {
        List<Book> books=getBooks("1").getRows();
        List<Book> bks = this.accurateSearch(books,book);
        request.setAttribute("pageResult",new PageResult(bks.size(), bks));
        return "book_borrowed";
    }
    //编辑/更新图书
    @ResponseBody
    @PostMapping("/book/editBook")
    public Result editBook(Book book)
    {
        if(book!=null)
        {
            int result=bookService.updataBook(book);
            if(result>0)
                return new Result(true,"success");
            else return new Result(false,"false");
        }
        else return new Result(true,"未获取图书信息");
    }
    //添加图书
    @ResponseBody
    @PostMapping("/book/addBook")
    public Result addBook(Book book)
    {
        if(book!=null)
        {
            // 获取当前时间
            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDate currentDate = currentDateTime.toLocalDate();
            book.setUploadTime(currentDate.toString());
            int a=bookService.insertBook(book);//新书更新时间
            if(a>0) return  new Result(true,"success");
            else return new Result(false,"false");
        }
        else return new Result(false,"获取图书信息失败！");
    }

    //-图书信息
    @ResponseBody
    @GetMapping("/book/findById")
    public Result getBookById(@RequestParam("id") Integer id)
    {
        PageResult pageResult = getBooks(id);
        Book book = (Book) pageResult.getRows().get(0);
        if(book!=null)
            return new Result(true,"susscfull",book);
        else return new Result(false,"failed");
    }
    //借书
    @ResponseBody
    @PostMapping("/book/borrowBook")
    public Result borrowBook(@RequestParam("id") Integer id, @RequestParam("returnTime") String time,
                             HttpServletRequest request)
    {
       Book book= new Book();
       book.setId(id);
       User user=(User)request.getSession().getAttribute("USER_SESSION");
       book.setBorrower(user.getName());
       book.setStatus("1");
        // 获取当前时间
        LocalDateTime currentDateTime = LocalDateTime.now();
        // 从LocalDateTime中获取LocalDate对象
        LocalDate currentDate = currentDateTime.toLocalDate();
        book.setBorrowTime(currentDate.toString());
        book.setReturnTime(time);//借书时间
       if(bookService.updataBook(book)>0)
       {
          return  new Result(true,"success");
       }
       else
       {
           return new Result(false,"false");
       }
    }
    //还书
    @ResponseBody
    @GetMapping("/book/returnBook")
    public Result returnBook(@RequestParam("id") Integer id)
    {
        Book book= new Book();
        book.setId(id);
        book.setStatus("2");
        if(bookService.updataBook(book)>0)
        {
            return  new Result(true,"success");
        }
        else
        {
            return new Result(false,"false");
        }
    }
    //管理员确认还书
    @ResponseBody
    @GetMapping("/book/returnConfirm")
    public Result returnConfirm(@RequestParam("id")Integer id)
    {
        Book book= new Book();
        book.setId(id);
        book.setStatus("0");
        book.setReturnTime("");
        book.setBorrowTime("");
        book.setBorrower("");
        if(bookService.updataBook(book)>0)
        {
            return  new Result(true,"susscful");
        }
        else
        {
            return new Result(false,"false");
        }
    }
    //精准查询
    public List<Book> accurateSearch(List<Book> books,Book book)
    {
        List<Integer> listId = new ArrayList<>();
        MyGetBook getBook = new MyGetBook(book);
        for(Book bk : books)
        {
            if(!getBook.getsTowp()&&!getBook.allNoNull()) {//两个为空,只有一个
                if(bk.getName().contains(book.getName())&&!getBook.Name())
                    listId.add(bk.getId());
                if(bk.getAuthor().contains(book.getAuthor())&&!getBook.Author())
                    listId.add(bk.getId());
                if(bk.getPress().contains(book.getPress())&&!getBook.Press())
                    listId.add(bk.getId());
            }
            else if(getBook.getsTowp()){//
                String name=getBook.NullName();
                if(name.equals("name"))
                {
                    if(bk.getAuthor().contains(book.getAuthor())&&
                            bk.getPress().contains(book.getPress()))
                        listId.add(bk.getId());
                }
                if(name.equals("author"))
                {
                    if(bk.getName().contains(book.getName())&&
                            bk.getPress().contains(book.getPress()))
                        listId.add(bk.getId());
                }
                if(name.equals("press"))
                {
                    if(bk.getAuthor().contains(book.getAuthor())&&
                            bk.getName().contains(book.getName()))
                        listId.add(bk.getId());
                }
            }
            else if (getBook.allNoNull()) {//三个全有
                if(bk.getAuthor().contains(book.getAuthor())&&
                        bk.getName().contains(book.getName())&&
                                bk.getPress().contains(book.getPress()))
                    listId.add(bk.getId());
            }
        }
        List<Integer> distinctList = new ArrayList<>(new LinkedHashSet<>(listId));
        List<Book> books2 = new ArrayList<>();
        System.out.println(listId);
        for(Integer id : distinctList)
        {
            books2.add((Book) getBooks(id).getRows().get(0));
        }
        return books2;
    }
    //连接数据库
    public PageResult getBooks() {
        List<Book> books=bookService.selectNewBooks();
        return new PageResult(books.size(), books);
    }
    public PageResult getBooks(String status) {
        List<Book> books=bookService.selectNewBooks(status);
        return new PageResult(books.size(), books);
    }
    public PageResult getBooks(Book book) {
        List<Book> books=bookService.selectNewBooks(book);
        return new PageResult(books.size(), books);
    }
    public PageResult getBooks(Integer id) {
        List<Book> books=bookService.selectNewBooks(id);
        return new PageResult(books.size(), books);
    }
}
