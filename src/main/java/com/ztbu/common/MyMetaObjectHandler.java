package com.ztbu.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ztbu.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  元数据对象处理器 需实现MetaObjectHandler接口
 *  在此类中统一为公共字段赋值
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充 insert{}",metaObject.toString());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充 update{}",metaObject.toString());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}
