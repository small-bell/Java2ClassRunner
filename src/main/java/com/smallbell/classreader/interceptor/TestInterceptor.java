package com.smallbell.classreader.interceptor;

import java.io.File;

public class TestInterceptor implements BaseInterceptor {

    @Override
    public void pre(File file) {
        System.out.println("pre 文件改变前置处理");
    }


    @Override
    public void post(File file) {
        System.out.println("post 文件改变后置处理");
    }
}

