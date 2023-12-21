package com.ztbu.service;

import com.ztbu.entity.Orders;
import org.springframework.core.annotation.Order;

import com.baomidou.mybatisplus.extension.service.IService;
public interface OrderService extends IService<Orders>{
    public void submit(Orders orders);
}
