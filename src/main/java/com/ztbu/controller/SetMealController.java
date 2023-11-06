package com.ztbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztbu.common.Result;
import com.ztbu.entity.Setmeal;
import com.ztbu.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RequestMapping("setmeal")
@Slf4j
@RestController
public class SetMealController {
    @Autowired
    private SetMealService setMealService;

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param setMeal
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String setMeal){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.like(Setmeal::getName,setMeal);
        setMealService.page(pageInfo);
        return Result.success(pageInfo);
    }
}
