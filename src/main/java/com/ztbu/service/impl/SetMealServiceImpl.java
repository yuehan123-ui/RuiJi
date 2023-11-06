package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.Setmeal;
import com.ztbu.mapper.SetMealMapper;
import com.ztbu.service.SetMealService;
import org.springframework.stereotype.Service;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService{
}
