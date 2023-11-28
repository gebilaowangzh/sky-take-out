package com.sky.aspect;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

/**
 * 自动填充切面 实现公共字段的自动填充
 */
@Aspect // 切面
@Component // 组件
@Slf4j//日志
public class AutoFillAspect {

    /**
     * 自动填充切点 拦截这些类
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut() {
    }
/*
这段代码是使用Java代码生成器（如MyBatis-Plus）生成的代码，用于定义一个pointcut，用于拦截某个包下的所有方法，并满足某个注解（如@AutoFill）的条件。
@Pointcut：这是一个Java注解，用于定义一个pointcut。
execution(* com.sky.mapper.*.*(..))：这是一个表达式，表示要拦截的mapper接口和方法。
其中，execution表示拦截方法，*表示拦截所有方法，com.sky.mapper.*.*(..)表示拦截该包下的所有方法，(..)表示拦截方法的参数。
&& @annotation(com.sky.annotation.AutoFill)：这是一个条件，表示要满足@AutoFill注解的条件。
其中，@annotation表示检查方法上是否具有某个注解，com.sky.annotation.AutoFill表示要检查的注解。
总之，这段代码定义了一个pointcut，用于拦截某个包下的所有方法，并满足某个注解（如@AutoFill）的条件。
 */

    /**
     * 自动填充 前置通知(就是方法增强
     *
     * @param joinPoint 切点
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始自动填充");
        // 获取操作类型
        // 从切点获取方法签名对象
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 从方法签名中获取 AutoFill 注解对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        // 从 AutoFill 注解中获取 value 值，这里假设 AutoFill 注解中有一个名为 value 的属性，类型为 OperationType
        OperationType operationType = autoFill.value();


        // 获取当前被拦截的方法的参数--实体对象 约定第一个参数是被拦截的方法的参数对象
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];


        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();


        // 根据不同的操作类型，通过反射进行不同的赋值
        if (operationType == OperationType.INSERT) {
            try {
                // 获取setCreateTime方法
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME,
                        LocalDateTime.class);
                // 获取setCreateUser方法
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER,
                        Long.class);
                // 获取setUpdateTime方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,
                        LocalDateTime.class);
                // 获取setUpdateUser方法
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,
                        Long.class);

                // 调用setCreateTime方法
                setCreateTime.invoke(entity, now);
                // 调用setCreateUser方法
                setCreateUser.invoke(entity, currentId);
                // 调用setUpdateTime方法
                setUpdateTime.invoke(entity, now);
                // 调用setUpdateUser方法
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (operationType == OperationType.UPDATE) {
            try {
                // 获取setUpdateTime方法
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME,
                        LocalDateTime.class);
                // 获取setUpdateUser方法
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER,
                        Long.class);

                // 调用setUpdateTime方法
                setUpdateTime.invoke(entity, now);
                // 调用setUpdateUser方法
                setUpdateUser.invoke(entity, currentId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}