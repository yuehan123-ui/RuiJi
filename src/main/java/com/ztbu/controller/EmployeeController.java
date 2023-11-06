package com.ztbu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ztbu.common.Result;
import com.ztbu.entity.Employee;
import com.ztbu.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request,@RequestBody Employee employee){
        //将页面提交的密码进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名username查询数据库
        String username = employee.getUsername();
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,username);
        Employee emp = employeeService.getOne(lqw);
        //如果没有查询到则返回登录失败结果
        if (emp == null){
            return Result.error("登录失败");
        }
        //密码比对，如果不一致则返回登录结果
        if (!emp.getPassword().equals(password)){
            return Result.error("登陆失败");
        }
        //查看员工状态，如果已为禁用状态，则返回员工已禁用结果
        if (emp.getStatus()==0){
            return Result.error("员工已禁用");
        }
        //登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        request.getSession().setAttribute("employee_user",emp.getUsername());
        return Result.success(emp);
    }

    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功");
    }

    /**
     * 添加员工
     * @param request
     * @param employee  接受前端输入的json数据 并封装成emp对象
     * @return
     */
    @PostMapping()
    public Result<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("添加员工{}",employee.getName());
        //添加用户时默认添加密码123456
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        Long id = (Long) request.getSession().getAttribute("employee");
/*        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        employee.setCreateUser(id);
        employee.setUpdateUser(id);*/
        employeeService.save(employee);
        return Result.success("添加成功");
    }

    /**
     * 分页查询
     * @param page  显示多少页
     * @param pageSize  一页多少行数据
     * @param name    按姓名查找
     * @return
     */
    @GetMapping("/page")
    public Result<Page> pageResult(int page,int pageSize,String name){
        log.info("page = {},pageSize = {},name = {}",page,pageSize,name);
        //构造分页构造器
        Page<Employee> pageInfo = new Page<>(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        if (name!=null){
            queryWrapper.like(Employee::getName,name);
        }
        //添加排序添加
        //queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageInfo,queryWrapper);
        return Result.success(pageInfo);
    }


    /**
     * 修改员工账号状态（禁用）
     * Long类型数据返回前端可能会出现精度丢失问题
     * 解决：在实体类->属性 上添加@JsonFormat(shape = JsonFormat.Shape.STRING) 注解
     * @param request
     * @param employee
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info("修改员工信息：{}",employee.getUsername());
        Long id = (Long) request.getSession().getAttribute("employee");
/*        employee.setUpdateUser(id);
        employee.setUpdateTime(LocalDateTime.now());*/
        employeeService.updateById(employee);
        return Result.success("员工信息修改成功");
    }

    @GetMapping("/get/{id}")
    public Result<Employee> selectById(@PathVariable Long id){
        Employee emp = employeeService.getById(id);
        log.info("根据id查询：{}",id);
        if (emp!=null){
            return Result.success(emp);
        }
        return Result.error("没有找到该用户信息");
    }
}
