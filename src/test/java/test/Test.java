package test;

import com.github.pagehelper.Page;
import com.mainpage.bean.Book;
import com.mainpage.service.BookService;
import com.spring.config.SpringConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class Test {
    @Autowired
    private BookService bookService;
    @org.junit.Test
    public void test()
    {
//        Page<Book> books = bookService.selectNewBooks();
//        System.out.println(books.size());
//        books.forEach(book -> System.out.println(book));
    }
}
