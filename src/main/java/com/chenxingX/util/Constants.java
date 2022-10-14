package com.chenxingX.util;

import java.io.File;

/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */
public class Constants {

    private Constants() {
    }

    /**
     * 中断提示
     */
    public static final String Q_OR_CTRL_C_ABORT_MSG = "Press Q or Ctrl+C to abort.";

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";

    /**
     * 命令提示符
     */
    public static final String DEFAULT_PROMPT = "$ ";

    /**
     * 带颜色命令提示符
     * raw string: "[33m$ [m"
     */
    public static final String COLOR_PROMPT = Ansi.ansi().fg(Ansi.Color.YELLOW).a(DEFAULT_PROMPT).reset().toString();

    /**
     * 方法执行耗时
     */
    public static final String COST_VARIABLE = "cost";

    public static final String CMD_HISTORY_FILE = System.getProperty("user.home") + File.separator + ".arthas" + File.separator + "history";

    /**
     * 当前进程PID
     */
    public static final String PID = PidUtils.currentPid();

}