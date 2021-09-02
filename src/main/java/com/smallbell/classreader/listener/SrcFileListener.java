package com.smallbell.classreader.listener;

import com.smallbell.classreader.Application;
import com.smallbell.classreader.utils.CompileUtil;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;

import java.io.File;

/**
 * 源文件目录监听
 */
public class SrcFileListener extends BaseListener {

    @Override
    public void fileChangeCallback(File file) {
        CompileUtil.compileClass(Application.rootPath, file.getPath());
    }

}
