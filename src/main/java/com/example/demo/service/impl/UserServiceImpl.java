package com.example.demo.service.impl;

import com.example.demo.dao.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User getUserByLoginName(String loginName) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(loginName.trim());
        List<User> users = userMapper.selectByExample(example);
        return CollectionUtils.isEmpty(users)?null:users.get(0);
    }

    @Override
    public List<User> getUserList() {
        UserExample example = new UserExample();
        example.setOrderByClause(" id desc");
        return userMapper.selectByExample(example);
    }
}
