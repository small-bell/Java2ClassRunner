package com.smallbell.classreader.listener;

import com.smallbell.classreader.Application;
import com.smallbell.classreader.classloader.MyClassLoader;
import com.smallbell.classreader.common.CommonValues;
import com.smallbell.classreader.interceptor.BaseInterceptor;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;
import java.util.*;

public abstract class BaseListener extends FileAlterationListenerAdaptor {

    public Map<String, BaseInterceptor> interceptors;

    public BaseListener() {
        interceptors = new HashMap<>();
    }

    @Override
    public void onFileChange(File file) {
        refresh();
        if (this instanceof ClassPathFileListener) {
            for (BaseInterceptor interceptor : interceptors.values()) {
                interceptor.pre(file);
            }

            fileChangeCallback(file);

            for (BaseInterceptor interceptor : interceptors.values()) {
                interceptor.post(file);
            }
        } else {
            fileChangeCallback(file);
        }
    }

    public abstract void fileChangeCallback(File file);

    /**
     * 刷新interceptors列表
     */
    public void refresh() {
        synchronized (interceptors) {
            for (String clazz : CommonValues.clazzs) {
                try {
                    Class<?> aClass = Class.forName(clazz);
                    Object o = aClass.newInstance();
                    if (o instanceof BaseInterceptor ) {
                        if (!(interceptors.containsValue((BaseInterceptor)o))) {
                            interceptors.put(o.getClass().getName(), (BaseInterceptor)o);
                        }
                    }
                } catch (Exception e) {

                }
            }
        }
    }

}
