package com.users.service.impl;

import com.github.pagehelper.PageHelper;
import com.users.bean.User;
import com.users.dao.UserMapper;
import com.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;
    @Override
    public User selectByIdAndName(User user) {
        return mapper.selectByIdAndName(user);
    }
    @Override
    public int insertUser(User user){
        return mapper.insertUser(user);
    }
    @Override
    public int updateUser(User user){
        return mapper.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return mapper.deleteUser(id);
    }

}
