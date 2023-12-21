package com.ztbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztbu.common.Result;
import com.ztbu.dto.DishDto;
import com.ztbu.entity.Category;
import com.ztbu.entity.Dish;
import com.ztbu.entity.DishFlavor;
import com.ztbu.service.CategoryService;
import com.ztbu.service.DishFlavorService;
import com.ztbu.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishFlavorService dishFlavorService;


    /**
     * 菜品 分页查询
     * @param page
     * @param pageSize
     * @param dish
     * @return
     */
    @GetMapping("/page")
    public Result<Page> page(int page,int pageSize,String dish){
        Page<Dish> page1 = new Page<>(page,pageSize);
        Page<DishDto> page2 = new Page<>();
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(dish != null,Dish::getName,dish);
        dishService.page(page1,lqw);
        /**
         * 对象拷贝 page1 到 page2 recodes属性为List<DishDto> 存储的菜品信息集合
         * 不需要拷贝 records属性 需要做进一步处理
         */
        BeanUtils.copyProperties(page1,page2,"records");
        //获取records数据
        List<Dish> records = page1.getRecords();
        List<DishDto> dishDtos = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Long categoryId = item.getCategoryId();
            Category categoory = categoryService.getById(categoryId);
            if (categoory != null) {
                dishDto.setCategoryName(categoory.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        page2.setRecords(dishDtos);


        return Result.success(page2);
    }

    /**
     * 添加菜品
     * @param dishDto 菜品信息
     * @return
     */
    @PostMapping
    public Result<String> save(@RequestBody DishDto dishDto){
        log.info("添加菜品信息{}",dishDto);
        dishService.saveWithFlavor(dishDto);
        return Result.success("添加成功");
    }

    /**
     * 回显菜品信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<DishDto> get(@PathVariable Long id){
        DishDto dishServiceByIdWithFlavor = dishService.getByIdWithFlavor(id);

        return Result.success(dishServiceByIdWithFlavor);
    }

    /**
     * 修改菜品信息
     * @param dishDto
     * @return
     */
    @PutMapping
    public Result<String> update(@RequestBody DishDto dishDto){
        log.info("修改菜品信息{}",dishDto);
        dishService.updateWithFlavor(dishDto);
        return Result.success("修改成功");
    }

    /**
     * 起售停售
     * @param dishDto
     * @return
     */
    @PostMapping("/status/{}")
    public Result<String> updateStatus(@RequestBody DishDto dishDto){
        log.info("修改菜品状态{}",dishDto);
        dishService.save(dishDto);
        return Result.success("修改成功");
    }

    /**
     * 查询该分类下的所有菜品 且查询的菜品必须为起售状态（status = 1）
     * 前端页面的需求：如果菜品设置了口味信息，需要展示选择规格按钮,否则显示+按钮
     * 返回的Dish未包含菜品的口味信息 需修改部分代码让返回值既包含菜品的基本信息，也包含了口味的信息
     * @param dish
     * @return
     */
/*
    @GetMapping("/list")
    public Result<List<Dish>> list(Dish dish){
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId()!= null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        List<Dish> list = dishService.list(queryWrapper);
        return Result.success(list);
    }
*/

    @GetMapping("/list")
    public Result<List<DishDto>> list(Dish dish){

        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        //添加条件，查询状态为1（1为起售，0为停售）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            //对象拷贝（每一个list数据）
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();  //分类id
            //通过categoryId查询到category内容
            Category category = categoryService.getById(categoryId);
            //判空
            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //获取当前菜品id
            Long dishId = item.getId();

            //构造条件构造器
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper= new LambdaQueryWrapper<>();
            //添加查询条件
            dishFlavorLambdaQueryWrapper.eq(dishId != null,DishFlavor::getDishId,dishId);
            //select * from dish_flavors where dish_id = ?
            List<DishFlavor> dishFlavors = dishFlavorService.list(dishFlavorLambdaQueryWrapper);

            dishDto.setFlavors(dishFlavors);

            return dishDto;
        }).collect(Collectors.toList());

        return Result.success(dishDtoList);
    }

}
