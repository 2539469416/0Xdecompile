package com.chenxingX.service;

import com.chenxingX.util.Decompiler;
import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.FileWriter;


/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */

public class MakeJavaFile implements Runnable {

    private String classFile;
    private String outputPath;

    public MakeJavaFile() {
    }

    public MakeJavaFile(String classFile, String outputPath) {
        this.classFile = classFile;
        this.outputPath = outputPath;
    }

    public Long makeJavaFile(String classPath, String outputPath) {
        StopWatch stopWatch = new StopWatch();
        try {
            stopWatch.start();
            outputPath = outputPath.replace(".class", ".java");
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputPath));
            bw.write(Decompiler.decompile(classPath, ""));
            bw.close();
            stopWatch.stop();
            System.out.println(outputPath + " decompile done" + "elapsed time " + stopWatch.getLastTaskTimeMillis() + "ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stopWatch.getLastTaskTimeMillis();
    }


    public void run() {
            makeJavaFile(classFile, outputPath);
    }
}
