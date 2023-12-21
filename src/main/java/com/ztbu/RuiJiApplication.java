package com.ztbu;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@MapperScan(basePackages = "com.ztbu.**.mapper") 扫描mapper接口 与 @Mapper 二选一 接口较多可不写@Mapper 写MapperScan
@Slf4j
@ServletComponentScan  // 扫描过滤器
@EnableTransactionManagement // 事务
@SpringBootApplication
@EnableCaching // 开启SpringCache缓存注解功能
/**
 * springBootApplication = SpringBootConfiguration + @ComponentScan + @EnableAutoConfiguration
 *     启动类    =                    配置类        + 组件自动扫描      +    自动配置
 */
/**
 * favicon 配置（标题栏图标） 制作一个favicon文件 将其放在static目录下即可生效
 */
public class RuiJiApplication {

    public static void main(String[] args) {
        SpringApplication.run(RuiJiApplication.class, args);
        log.info("项目启动成功");
    }

}
