package com.ztbu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.common.CustomException;
import com.ztbu.entity.Category;
import com.ztbu.entity.Dish;
import com.ztbu.entity.Setmeal;
import com.ztbu.mapper.CategoryMapper;
import com.ztbu.service.CategoryService;
import com.ztbu.service.DishService;
import com.ztbu.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    DishService dishService;

    @Autowired
    SetMealService setmealService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加dish查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count1 =(int) dishService.count(dishLambdaQueryWrapper);

        //查看当前分类是否关联了菜品，如果已经关联，则抛出异常
        if (count1 > 0){
            //已关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        LambdaQueryWrapper<Setmeal> setMealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加dish查询条件，根据分类id进行查询
        setMealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count2 =(int) setmealService.count(setMealLambdaQueryWrapper);

        //查看当前分类是否关联了套餐，如果已经关联，则抛出异常
        if (count2 > 0){
            //已关联套餐，抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }

        //正常删除
        super.removeById(id);
    }

}
