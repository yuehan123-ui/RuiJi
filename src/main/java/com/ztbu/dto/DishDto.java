package com.ztbu.dto;


import com.ztbu.entity.Dish;
import com.ztbu.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;//菜品分类名称

    private Integer copies;
}
