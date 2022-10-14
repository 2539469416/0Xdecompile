package com.chenxingX.service;

import com.chenxingX.config.ExecutorFactory;
import com.chenxingX.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */


public class CreateJavaFile {


    private static ExecutorService executor = null;

    private Future task;

    public void scanDir(File file, String path, int corePoolSize) {
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
        if (file.getAbsolutePath().endsWith(".jar")||file.getAbsolutePath().endsWith(".war")){
            scanJar(file,file.getAbsolutePath(),path);
        }else {
            scanDir(file, file.getAbsolutePath(), path);
        }
        try {
            task.get();
            Thread.sleep(100L);
            executor.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sw.stop();
        System.out.println("Time taken for tests :" + sw.getLastTaskTimeMillis() + "ms");
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
                        task = threadDecompiler(file.getAbsolutePath(), copyPath,false);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void scanJar(File file, String filePath, String path) {
        path = path.endsWith("/") ? path : path + "/";
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file, 1);
            Enumeration<? extends ZipEntry> dir = zipFile.entries();
            while (dir.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) dir.nextElement();
                if (entry.getName().contains("/")){
                    String dirs = entry.getName().substring(0,entry.getName().lastIndexOf("/"));
                    new File(path + dirs).mkdirs();
                }
                if (entry.isDirectory()) {
                    String name = entry.getName();
                    File fileObject = new File(path + name);
                    fileObject.mkdir();
                }else {
                    String copyPath = path+entry.getName();
                    InputStream inputStream = new BufferedInputStream(zipFile.getInputStream(entry));
                    OutputStream outputStream = new FileOutputStream(copyPath);
                    IOUtils.copy(inputStream,outputStream);
                    if (copyPath.endsWith(".class")){
                        task = threadDecompiler(copyPath, copyPath,true);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Future threadDecompiler(String classFile, String output,boolean delete) {
        Future future = executor.submit(new MakeJavaFile(classFile, output,delete));
        return future;
    }
}
