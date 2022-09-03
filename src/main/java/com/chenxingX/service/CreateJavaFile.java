package com.chenxingX.service;

import com.chenxingX.config.ExecutorFactory;
import com.chenxingX.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */


public class CreateJavaFile {

    private static final Logger logger = LoggerFactory.getLogger(CreateJavaFile.class);

    private static ExecutorService executor = null;

    private Future task;

    public void scanDir(File file, String path,int corePoolSize) {
        StopWatch sw = new StopWatch();
        sw.start();
        try {
            File root = new File(path);
            if (!root.exists()) {
                root.mkdir();
            }
          executor = new ExecutorFactory().createThreadPoolInstance(corePoolSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanDir(file, file.getAbsolutePath(), path);
        try {
            task.get();
            Thread.sleep(100L);
            executor.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
        sw.stop();
        logger.info("Time taken for tests :" + sw.getLastTaskTimeMillis() + "ms");
    }

    public void scanDir(File file, String filePath, String path) {
        try {
            if (file.canRead()) {
                String copyPath = file.getAbsoluteFile().toString().replace(filePath, path);
                if (file.isDirectory()) {
                    File copyDir = new File(copyPath);
                    if (!copyDir.exists()) {
                        copyDir.mkdir();
                    }
                    String[] files = file.list();
                    if (files != null) {
                        for (String childDir : files) {
                            scanDir(new File(file, childDir), filePath, path);
                        }
                    }
                } else {
                    String filename = file.getName();
                    if (StringUtils.isEmpty(filename) && !filename.endsWith(".class")) {
                        InputStream inputStream = new FileInputStream(file);
                        OutputStream outputStream = new FileOutputStream(copyPath);
                        IOUtils.copy(inputStream, outputStream);
                    } else {
                        //多线程任务
                        task = threadDecompiler(file.getAbsolutePath(), copyPath);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Future threadDecompiler(String classFile, String output) {
        Future future = executor.submit(new MakeJavaFile(classFile, output));
        return future;
    }
}
