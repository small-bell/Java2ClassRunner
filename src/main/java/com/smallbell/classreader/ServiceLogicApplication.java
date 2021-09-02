package com.smallbell.classreader;

import com.smallbell.classreader.classloader.MyClassLoader;
import com.smallbell.classreader.common.CommonValues;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 这个是真实的主类
 */
public class ServiceLogicApplication {

    public void test(){
        try {
            Class<?> aClass = Class.forName("com.smallbell.classreader.testcompile.TestClass");
            Method testClass = aClass.getMethod("getTest", null);
            Object o = aClass.newInstance();
            testClass.invoke(o, null);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        for (String clazz : CommonValues.clazzs) {
//            System.out.println(clazz);
//        }
    }

//    public static void main(String[] args) {
//        String rootPath = MyClassLoader.class.getResource("/").getPath().replaceAll("%20"," ");
//        rootPath = new File(rootPath).getPath();
//        System.out.println(rootPath + "/com");
//    }

}
