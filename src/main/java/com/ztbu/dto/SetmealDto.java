package com.ztbu.dto;


import com.ztbu.entity.Setmeal;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    //private List<SetmealDish> setmealDishes;

    private String categoryName;
}
