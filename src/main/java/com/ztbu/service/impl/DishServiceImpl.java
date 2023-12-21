package com.ztbu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.dto.DishDto;
import com.ztbu.entity.Category;
import com.ztbu.entity.Dish;
import com.ztbu.entity.DishFlavor;
import com.ztbu.mapper.DishMapper;
import com.ztbu.service.CategoryService;
import com.ztbu.service.DishFlavorService;
import com.ztbu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;


    /**
     * 新增菜品 同时插入菜品的口味数据  需要操作两张表 Dish 和  DishFlavor
     * @param dishDto
     */
    @Override
    @Transactional  //开启事务
    public void saveWithFlavor(DishDto dishDto) {

        //保存菜品的基本信息到菜品表
        super.save(dishDto);
        //获取菜品id
        Long dishId = dishDto.getId();
        //获取菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();

        //将每条flavor的dishId赋上值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());

        //保存菜品口味数据到菜品口味表
        dishFlavorService.saveBatch(flavors);// Batch 形参为集合 批量保存菜品的口味

    }

    /**
     * 修改菜品时回显 根据id查询菜品 同时查询菜品的口味数据  需要操作两张表 Dish 和  DishFlavor
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

            //通过id查询菜品基本信息
            Dish dish = this.getById(id);

            //创建dto对象
            DishDto dishDto = new DishDto();

            //对象拷贝
            BeanUtils.copyProperties(dish,dishDto);

            //条件查询flavor
            LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DishFlavor::getDishId,dish.getId());
            List<DishFlavor> list = dishFlavorService.list(queryWrapper);


            //将查询到的flavor赋值到dto对象中
            dishDto.setFlavors(list);

            return dishDto;


    }

    @Override
    public void updateWithFlavor(DishDto dishDto) {
        //根据id修改菜品的基本信息
        super.updateById(dishDto);

        //通过dish_id,删除菜品的flavor
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        //获取前端提交的flavor数据
        List<DishFlavor> flavors = dishDto.getFlavors();

        //将每条flavor的dishId赋值
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());


        //将数据批量保存到dish_flavor数据库
        dishFlavorService.saveBatch(flavors);

    }


}
