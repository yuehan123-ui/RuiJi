package com.ztbu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ztbu.entity.Employee;
import com.ztbu.mapper.EmployeeMapper;
import com.ztbu.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeMapperImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
