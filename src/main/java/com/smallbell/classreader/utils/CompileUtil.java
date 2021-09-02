package com.smallbell.classreader.utils;

import com.smallbell.classreader.classloader.MyClassLoader;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java2class 工具
 */
public class CompileUtil {

    /**
     * 把java文件转换成class文件到classpath
     * @param rootPath
     * @param javaFiles
     */
    public static void compileClass(String rootPath, String... javaFiles) {
        String sOutputPath = rootPath;
        List<String> paths = new ArrayList<String>();
        for (String javaFile : javaFiles) {
            paths.add(javaFile);
        }
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                diagnostics, null, null);
        JavaFileManager.Location oLocation = StandardLocation.CLASS_OUTPUT;
        try {
            fileManager.setLocation(oLocation,
                    Arrays.asList(new File[] { new File(sOutputPath) }));
            Iterable<? extends JavaFileObject> compilationUnits = fileManager
                    .getJavaFileObjectsFromStrings(paths);
            JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager,
                    diagnostics, null, null, compilationUnits);
            boolean result = task.call();
            fileManager.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) throws IOException {
//        String rootPath = MyClassLoader.class.getResource("/").getPath().replaceAll("%20"," ");
//        rootPath = new File(rootPath).getPath();
//        Application.rootPath = rootPath;
//
//        compileClass(Application.rootPath, "C:\\Users\\Administrator\\Desktop\\class-reader\\src\\main\\java\\com\\smallbell\\classreader\\testcompile\\TestClass.java");
//    }
}
