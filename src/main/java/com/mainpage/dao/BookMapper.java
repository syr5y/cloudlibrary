package com.mainpage.dao;
import com.mainpage.bean.Book;
import com.mainpage.bean.Record;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 图书接口
 */
public interface BookMapper {
   //@Select("SELECT * FROM book where book_status !='3' order by book_uploadtime DESC")
   @Select("<script>" +
           "SELECT * FROM book" +
           "<where>"+
           "<if test=\"status != null and status!=''\">book_status = #{status}</if>" +
           "<if test=\"id != null\">AND book_id = #{id}</if>" +
           "<if test=\"name != null and name!=''\">AND book_name = #{name}</if>" +
           "<if test=\"author!=null and author!=''\">AND book_author=#{author}</if>"+
           "<if test=\"press!=null and press!=''\">AND book_press=#{press}</if>"+
           "<if test=\"isbn!=null and isbn!=''\">AND book_isbn=#{isbn}</if>"+
           "<if test=\"pagination!=null and pagination!=''\">AND book_pagination=#{pagination}</if>"+
           "</where>"+
           "</script>")
    @Results(id = "bookMap",value = {
            //id字段默认为false，表示不是主键
            //column表示数据库表字段，property表示实体类属性名。
            @Result(id = true,column = "book_id",property = "id"),
            @Result(column = "book_name",property = "name"),
            @Result(column = "book_isbn",property = "isbn"),
            @Result(column = "book_press",property = "press"),
            @Result(column = "book_author",property = "author"),
            @Result(column = "book_pagination",property = "pagination"),
            @Result(column = "book_price",property = "price"),
            @Result(column = "book_uploadtime",property = "uploadTime"),
            @Result(column = "book_status",property = "status"),
            @Result(column = "book_borrower",property = "borrower"),
            @Result(column = "book_borrowtime",property = "borrowTime"),
            @Result(column = "book_returntime",property = "returnTime")
    })
    List<Book> selectNewBooks(Book book);
   @Update(("<script>"+
           "update book" +
           "<set>"+
           "<if test=\"name!=null and name!=''\">,book_name=#{name}</if>"+
           "<if test=\"isbn!=null and isbn!=''\">,book_isbn=#{isbn}</if>"+
           "<if test=\"press!=null and press!=''\">,book_press=#{press}</if>"+
           "<if test=\"author!=null and author!=''\">,book_author=#{author}</if>"+
           "<if test=\"pagination!=null and pagination!=''\">,book_pagination=#{pagination}</if>"+
           "<if test=\"price!=null and price!=''\">,book_price=#{price}</if>"+
           "<if test=\"uploadTime!=null\">,book_uploadtime=#{uploadTime}</if>"+
           "<if test=\"status!=null and status!=''\">,book_status=#{status}</if>"+
           "<if test=\"borrower!=null\">,book_borrower=#{borrower}</if>"+
           "<if test=\"borrowTime!=null\">,book_borrowtime=#{borrowTime}</if>"+
           "<if test=\"returnTime!=null\">,book_returntime=#{returnTime}</if>" +
           "</set>"+
           "where book_id =#{id}" +
           "</script>"))
   int updateBook(Book book);

   @Insert(
           "insert into book " +
           "(book_name, book_isbn,book_press,book_author,book_pagination,book_price,book_uploadtime,book_status)"+
           "values (#{name}, #{isbn}, #{press},#{author}, #{pagination},#{price},#{uploadTime},#{status})"
   )
   int insertBook(Book book);

   @Select("<script>" +
           "select * from record" +
           "<if test=\"id>0\">where record_id=#{id}</if>" +
           "</script>")
   @Results(id = "recordMap",value = {
           //id字段默认为false，表示不是主键
           //column表示数据库表字段，property表示实体类属性名。
           @Result(id = true,column = "record_id",property = "id"),
           @Result(column = "record_bookname",property = "bookname"),
           @Result(column = "record_bookisbn",property = "bookisbn"),
           @Result(column = "record_borrower",property = "borrower"),
           @Result(column = "record_borrowtime",property = "borrowTime"),
           @Result(column = "record_remandtime",property = "remandTime")
   })
    List<Record> getRecord(Integer id);

   @Update("<update>")
    int updaterecord(Integer id);
}
