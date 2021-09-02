package com.smallbell.classreader.interceptor;

import java.io.File;

/**
 * 拦截器接口
 */
public interface BaseInterceptor {

    void pre(File file);

    void post(File file);

}
