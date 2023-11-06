package com.ztbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztbu.common.Result;
import com.ztbu.entity.Dish;
import com.ztbu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param dish
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String dish){
        Page<Dish> page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(Dish::getName,dish);
        dishService.page(page1);
        return Result.success(page1);
    }

}
