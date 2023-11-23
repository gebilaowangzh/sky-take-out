package com.sky.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/*
Serializable 是一个 Java 接口，它表示一个类可以被序列化。
序列化是指将一个对象的状态（例如属性值和实例变量）
保存到磁盘或者通过网络传输到另一个计算机的过程。通过序列化，
我们可以将一个对象保存为字节序列，然后通过反序列化过程将该序列转换回原来的对象。
在 Java 中，所有实现了 Serializable 接口的类都可以被序列化。
 */
public class PageResult implements Serializable {

    private long total; //总记录数

    private List records; //当前页数据集合

}
