package com.sky.annotation;

/*
 解决大量create_time update_time 字段填充代码冗余不便修改的问题
 自定义注解AutoFill，用于标识需要进行公共字段自动填充的方法
 自定义切面类AutoFillAspect，统一拦截加入了AutoFill注解的方法，通过反射为公共字段赋值
 在Mapper的方法上加入AutoFill注解
 */

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 */
@Target(ElementType.METHOD)//指定这个注解只能加在方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //定义一个枚举类，用于标识自动填充的字段
    //数据库操作类型:UPDATE INSERl
    OperationType  value();
}


/*

@Target
ElementType.TYPE：用于描述类、接口(包括注解类型)或enum声明
ElementType.TYPE：可以应用在类、接口（包括注解类型）或枚举声明。
ElementType.FIELD：可以应用在字段（包括枚举常量）声明。
ElementType.METHOD：可以应用在方法声明。
ElementType.PARAMETER：可以应用在参数声明。
ElementType.CONSTRUCTOR：可以应用在构造方法声明。
ElementType.LOCAL_VARIABLE：可以应用在局部变量声明。
ElementType.ANNOTATION_TYPE：可以应用在注解类型声明。
ElementType.PACKAGE：可以应用在包声明。

@Retention
RetentionPolicy.SOURCE：这种类型的注解只在源代码级别保留，编译时会被丢弃。
RetentionPolicy.CLASS：这种类型的注解在编译时会被保留到 class 文件中，但在运行时不可获取。
RetentionPolicy.RUNTIME：这种类型的注解在运行时会被保留，因此可以通过反射机制读取到。

 */