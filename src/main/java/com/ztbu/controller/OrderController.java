package com.ztbu.controller;

import com.ztbu.common.Result;
import com.ztbu.entity.Orders;
import com.ztbu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders){
        log.info("orders={}",orders);

        orderService.submit(orders);
        return Result.success("用户下单成功");
    }

}
