package com.ztbu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ztbu.entity.Category;

public interface CategoryService extends IService<Category> {

    void remove(Long id);

}
