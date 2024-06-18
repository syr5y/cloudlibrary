package com.users.dao;

import com.users.bean.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Results(id = "userMap",value = {
            //id字段默认为false，表示不是主键
            //column表示数据库表字段，property表示实体类属性名。
            @Result(id = true,column = "user_id",property = "id"),
            @Result(column = "user_name",property = "name"),
            @Result(column = "user_password",property = "password"),
            @Result(column = "user_email",property = "email"),
            @Result(column = "user_role",property = "role"),
            @Result(column = "user_status",property = "status")
    })
    @Select("<script>" +
            "SELECT * FROM user"+
            "<where>"+
            "<if test=\"email!=null and email!=''\">user_email=#{email}</if>" +
            "<if test=\"password!=null and password!=''\">and user_password=#{password}</if>" +
            "</where>"+
            "</script>")
    User selectByIdAndName(User user);

    @Insert("insert into user(user_name,user_password,user_email,user_role) values(#{name},#{password},#{email},#{role})")
    int insertUser(User user);
    @Update("update user set user_name=#{name}," +
            "user_password=#{password},user_email=#{email},user_role=#{role} where user_id=#{id}")
    int updateUser(User user);
    @Delete("delete from user where user_id=#{id}")
    int deleteUser(int id);
}
