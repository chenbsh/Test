package com.coding.jdbc.domain;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 定义基础数据信息传输JavaBean;
 * <p>
 * 本系统所有用于数据传输的JavaBean都必须继承BasicBean
 * 
 * @Copyright MacChen
 * 
 * @Project CodeGenerationTool
 * 
 * @Author MacChen
 * 
 * @timer 2017-12-01
 * 
 * @Version 1.0.0
 * 
 * @JDK version used 8.0
 * 
 * @Modification history none
 * 
 * @Modified by none
 */
public class BasicBean implements Serializable {

    /**
     * 序列化
     */
    private static final long serialVersionUID = 609392925037215528L;

    public BasicBean() {
        super();
    }

    /**
     * 根据实例对象属性名称获取属性值
     *
     * @param  propertyName = 属性名称
     * @return Object 属性值
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @Author MacChen
     * @time 2018-03-28
     */
    public Object getPropertyValue(String propertyName) {
        try {
            // 获取属性变量
            Field field = this.getClass().getDeclaredField(propertyName);
            // 安全性检查
            field.setAccessible(true);
            // 获取属性值
            return field.get(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据实例对象属性名称设置属性值
     *
     * @param  propertyName = 属性名称
     * @param  propertyValue = 属性值
     * @return void
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @Author MacChen
     * @time 2018-03-28
     */
    public void setPropertyValue(String propertyName, Object propertyValue) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        // 获取属性设置方法名称
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        // 获取Set方法
        Method method = this.getClass().getDeclaredMethod(methodName, new Class[]{propertyValue.getClass()});
        // 设值
        method.invoke(this, propertyValue);
    }
}
