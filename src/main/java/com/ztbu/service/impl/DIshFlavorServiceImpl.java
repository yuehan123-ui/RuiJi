package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.DishFlavor;
import com.ztbu.mapper.DishFlavorMapper;
import com.ztbu.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DIshFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
