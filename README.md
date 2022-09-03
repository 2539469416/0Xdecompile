## 0Xdecompile

0Xdecompile是基于cfr的多线程反编译工具，主要用于多线程反编译class文件，生成源码.java文件，进行修改代码以及替换。
0Xdecompile is a multi-thread decompiler tool based on CFR, mainly used for multi-thread decompiler class file, source code generation. Java file, code modification and replacement.

目前 V1.0只支持反编译文件夹下的.class文件

### 依赖环境

jdk 9 或 jdk9 以上（jdk ZipFile多线程会上锁，目前没找到解决方案）



### **使用方式**

java -jar -s D:\input -d D:\output_path -t threatcount

-s	反编译文件路径（必须）

-d	输出文件路径（必须）

-t	线程数，默认4线程