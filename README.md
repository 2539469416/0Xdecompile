## 0Xdecompile

0Xdecompile是基于cfr的多线程反编译工具，主要用于反编译class文件，生成源码.java文件，进行修改代码以及替换。
0Xdecompile is a multi-thread decompiler tool based on CFR, mainly used for multi-thread decompiler class file, source code generation. Java file, code modification and replacement.

支持jar包，war包，文件夹形式进行编译

### 依赖环境

jdk 9 或 jdk9 以上



### **使用方式**

java -jar -s D:\input -d D:\output_path -t threatcount

java -jar -s D:\input -d D:\output_path.jar -t threatcount 

-s	反编译文件路径（必须）

-d	输出文件路径（必须）

-t	线程数，默认4线程
