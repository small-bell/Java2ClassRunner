package com.smallbell.classreader.listener;

import com.smallbell.classreader.Application;
import com.smallbell.classreader.classloader.MyClassLoader;
import com.smallbell.classreader.common.CommonValues;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * ClassPath文件监听 如果发生改变重启项目
 */
public class ClassPathFileListener extends BaseListener {

    @Override
    public void fileChangeCallback(File file) {
        if (file.getName().indexOf(".class")!= -1){
            try {
                MyClassLoader myClassLoader = new MyClassLoader(Application.rootPath, Application.rootPath + "/com");
                Application.stop();
                Application.start0(myClassLoader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
