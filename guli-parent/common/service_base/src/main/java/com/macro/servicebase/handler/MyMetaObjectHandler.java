package com.macro.servicebase.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @auther macro
 * @description
 * @date 2023/11/22 18:29
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler
{
    @Override
    public void insertFill(final MetaObject metaObject)
    {
        // fieldName传递的是属性名称,不是字段名称
        setFieldValByName("gmtCreate", new Date(), metaObject);
        setFieldValByName("gmtModified", new Date(), metaObject);
    }

    @Override
    public void updateFill(final MetaObject metaObject)
    {
        setFieldValByName("gmtModified", new Date(), metaObject);
    }
}
