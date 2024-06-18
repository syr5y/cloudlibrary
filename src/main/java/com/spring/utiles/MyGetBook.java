package com.spring.utiles;


import com.mainpage.bean.Book;
import com.mainpage.bean.Record;

public class MyGetBook {
    private String name;       //图书名称
    private String press;      //图书出版社
    private String author;     //图书作者

    public MyGetBook(Book book) {
        this.name = book.getName();
        this.press = book.getPress();
        this.author = book.getAuthor();
    }
    public MyGetBook(Record record) {
        this.name = record.getBookname();
        this.author=record.getBorrower();
        this.press = "a";
    }
    public Boolean Name() {
        return this.name == null||this.name== "";
    }
    public boolean Press() {
        return this.press==null||this.press=="";
    }
    public Boolean Author() {
        return this.author==null||this.author=="";
    }
    public Boolean allNoNull() {
        int count =0;
        if(!Author())
            count++;
        if(!Name())
            count++;
        if(!Press())
            count++;
        if(count==3)
            return true;
        else return false;
    }
    public Boolean  getsTowp()
    {
        int count =0;
        if(!Press())
            count++;
        if(!Name())
            count++;
        if(!Author())
            count++;
        if (count==2)
        {
            return true;
        }else{
            return false;
        }
    }
    public String NullName()
    {
        int count =0;
        int press=0;
        int name=0;
        if(!Press())
            press++;
        if(!Name())
            name++;
        if(!Author())
            count++;
        if (press==0)
        return "press";
        if(name==0)
            return "name";
        if(count==0)
            return "author";
        return "No";
    }
}
