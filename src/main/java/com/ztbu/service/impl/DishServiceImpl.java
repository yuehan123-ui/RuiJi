package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.Dish;
import com.ztbu.mapper.DishMapper;
import com.ztbu.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
