package com.users.service;


import com.users.bean.User;

public interface UserService{
    User selectByIdAndName(User user);
    int insertUser(User user);
    int updateUser(User user);
    int deleteUser(int id);
}
