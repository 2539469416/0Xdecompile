package com.chenxingX;

import com.chenxingX.service.CreateJavaFile;
import org.apache.commons.cli.*;

import java.io.File;

public class Main {
    public static void main(String[] args) throws ParseException {
        System.out.println("请输入反编译文件夹，输出路径如 java -jar 0Xdecompile.jar -s D:/input/decompile -d D:/output/path");
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();
        options.addOption("s", true, "反编译文件文件路径");
        options.addOption("d", true, "文件输出路径");
        options.addOption("t", true, "线程数，默认4线程");
        CommandLine cmd = commandLineParser.parse(options, args);
        if (!cmd.hasOption("s")) {
            System.out.println("请输入反编译文件");
            return;
        }
        if (!cmd.hasOption("d")) {
            System.out.println("请输入文件输出路径");
            return;
        }
        String output = cmd.getOptionValue("s");
        String input = cmd.getOptionValue("d");
        Integer corePoolSize = 4;
        if (cmd.hasOption("t")) {
            corePoolSize = Integer.valueOf(cmd.getOptionValue("t"));
        }
        CreateJavaFile createJavaFile = new CreateJavaFile();
        createJavaFile.scanDir(new File(output), input, corePoolSize);

    }
}

