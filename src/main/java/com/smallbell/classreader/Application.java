package com.smallbell.classreader;

import com.smallbell.classreader.classloader.MyClassLoader;
import com.smallbell.classreader.common.CommonValues;
import com.smallbell.classreader.listener.ClassPathFileListener;
import com.smallbell.classreader.listener.SrcFileListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;
import java.util.List;

public class Application {

    public static String rootPath;
    public static String srcPath;
    /**
     * 启动应用程序和使用MyClassLoader加载的类
     * @param clazz
     * @throws Exception
     */
    public static void run(Class<?> clazz, String srcPath) throws Exception {
        Application.srcPath = srcPath;
        String rootPath = MyClassLoader.class.getResource("/").getPath().replaceAll("%20", " ");
        rootPath = new File(rootPath).getPath();
        Application.rootPath = rootPath;
        MyClassLoader myClassLoader = new MyClassLoader(rootPath, rootPath);
        List<String> clazzs = myClassLoader.getClazzs();
        CommonValues.clazzs = clazzs;
        startClassPathFileListener(rootPath);
        startSrcFileListener(srcPath);
        start0(myClassLoader);
    }

    /**
     * 启动src监听器
     * @param srcPath
     * @throws Exception
     */
    private static void startSrcFileListener(String srcPath) throws Exception {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(srcPath);
        fileAlterationObserver.addListener(new SrcFileListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }

    /**
     * 启动classpath监听器
     * @param rootPath
     * @throws Exception
     */
    public static void startClassPathFileListener(String rootPath) throws Exception {
        FileAlterationObserver fileAlterationObserver = new FileAlterationObserver(rootPath);
        fileAlterationObserver.addListener(new ClassPathFileListener());
        FileAlterationMonitor fileAlterationMonitor = new FileAlterationMonitor(500);
        fileAlterationMonitor.addObserver(fileAlterationObserver);
        fileAlterationMonitor.start();
    }

    /**
     * 系统退出
     */
    public static void stop() {
        System.out.println("stop project complete! --------------------------");
        System.gc();
        System.runFinalization();
    }

    /**
     * 以MyClassLoader加载启动
     * @param classLoader
     * @throws Exception
     */
    public static void start0(MyClassLoader classLoader) throws Exception {
        Class<?> aClass = classLoader.loadClass("com.smallbell.classreader.Application");
        aClass.getMethod("start").invoke(aClass.newInstance());
    }

    /**
     * 这个方法由反射执行
     */
    public static void start(){
        System.out.println("start project complete! ----------------------------");
        new ServiceLogicApplication().test();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("------------------------------------");
        Application.run(MyClassLoader.class, "C:\\Users\\Administrator\\Desktop\\class-reader\\src\\main\\java\\com\\smallbell\\classreader");
    }


}
