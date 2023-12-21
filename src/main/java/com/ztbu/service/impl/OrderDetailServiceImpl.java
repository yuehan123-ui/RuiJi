package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.OrderDetail;
import com.ztbu.mapper.OrderDetailMapper;
import com.ztbu.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService{
}
