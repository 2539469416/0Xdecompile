package com.chenxingX.util;

import java.lang.management.ManagementFactory;
import java.util.Map;

/**
 * Created by 0XFA_CHENXINGX on 2022/8/30
 */
public class PidUtils {
    private static String PID = "-1";
    private static long pid = -1;

    private static String MAIN_CLASS = "";

    static {
        // https://stackoverflow.com/a/7690178
        try {
            String jvmName = ManagementFactory.getRuntimeMXBean().getName();
            int index = jvmName.indexOf('@');

            if (index > 0) {
                PID = Long.toString(Long.parseLong(jvmName.substring(0, index)));
                pid = Long.parseLong(PID);
            }
        } catch (Throwable e) {
            // ignore
        }

        try {
            for (final Map.Entry<String, String> entry : System.getenv().entrySet()) {
                if (entry.getKey().startsWith("JAVA_MAIN_CLASS")) // like JAVA_MAIN_CLASS_13328
                    MAIN_CLASS = entry.getValue();
            }
        } catch (Throwable e) {
            // ignore
        }

    }

    private PidUtils() {
    }

    public static String currentPid() {
        return PID;
    }

    public static long currentLongPid() {
        return pid;
    }

    public static String mainClass() {
        return MAIN_CLASS;
    }
}