package com.ztbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztbu.common.Result;
import com.ztbu.dto.SetmealDto;
import com.ztbu.entity.Setmeal;
import com.ztbu.service.SetMealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    /**
     * 用户端：通过套餐种类id和套餐对应的状态查询出符合条件的套餐
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache",key = "#setmeal.categoryId+'_'+#setmeal.status")//动态参数
    public Result<List<Setmeal>> list(Setmeal setmeal){
        //创建条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getStatus,setmeal.getStatus());

        //排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setMealService.list(queryWrapper);

        return Result.success(list);
    }

    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    @CacheEvict(value = "setmealCache",allEntries = true)//删除setmealCache分类下的所有缓存数据
    public Result<String> save(@RequestBody SetmealDto setmealDto){
        return null;
    }
    // 新增 修改 删除 都需要上述注解 ↑

}
