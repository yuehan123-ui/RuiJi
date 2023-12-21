package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.User;
import com.ztbu.mapper.UserMapper;
import com.ztbu.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
