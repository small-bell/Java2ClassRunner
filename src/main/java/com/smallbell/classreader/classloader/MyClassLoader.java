package com.smallbell.classreader.classloader;


import com.smallbell.classreader.Application;
import com.smallbell.classreader.common.CommonValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyClassLoader extends ClassLoader {

    /**
     * 项目的根路径
     */
    public String rootPath;

    public List<String> getClazzs() {
        return clazzs;
    }

    /**
     * 需要由这个类加载器加载的类集合
     */
    public List<String> clazzs;


    public MyClassLoader(String rootPath, String... classPaths) throws Exception {

        this.rootPath = rootPath;
        this.clazzs = new ArrayList<>();

        for (String classPath : classPaths) {
            scanClassPath(new File(classPath));
        }
    }

    /**
     * 扫描项目里面传进来的一些class
     * @param file
     * @throws Exception
     */
    public void scanClassPath(File file) throws Exception {
        if (file.isDirectory()) {
            for (File file1 : file.listFiles()) {
                scanClassPath(file1);
            }
        } else {
            String fileName = file.getName();
            String filePath = file.getPath();
            String endName = fileName.substring(fileName.lastIndexOf(".") + 1);
            if (endName.equals("class")) {
                InputStream inputStream = new FileInputStream(file);
                byte[] bytes = new byte[(int) file.length()];
                inputStream.read(bytes);

                String className = fileNameToClassName(filePath);
                defineClass(className, bytes, 0, bytes.length);
                clazzs.add(className);
            }
        }
    }

    /**
     * 替换/为.
     * @param filePath
     * @return
     */
    public String fileNameToClassName(String filePath) {
        String className = filePath.replace(rootPath, "").replaceAll("\\\\", ".");
        className = className.substring(1, className.lastIndexOf("."));
        return className;
    }

    /**
     * 加载类
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        Class<?> loadClass = findLoadedClass(name);
        if (loadClass == null) {
            if (!clazzs.contains(name)) {
                loadClass = getSystemClassLoader().loadClass(name);
            } else {
                throw new ClassNotFoundException("没有加载到类");
            }
        }
        return loadClass;
    }


}
