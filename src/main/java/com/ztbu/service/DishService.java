package com.ztbu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ztbu.dto.DishDto;
import com.ztbu.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品 同时插入菜品的口味数据  需要操作两张表 Dish 和  DishFlavor
    void saveWithFlavor(DishDto dishDto);

    //根据id查询菜品 同时查询菜品的口味数据  需要操作两张表 Dish 和  DishFlavor
    DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
