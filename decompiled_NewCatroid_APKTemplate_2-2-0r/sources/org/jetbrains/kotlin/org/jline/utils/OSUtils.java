package org.jetbrains.kotlin.org.jline.utils;

import java.io.File;
import org.apache.commons.lang3.SystemProperties;

/* JADX INFO: loaded from: classes4.dex */
public class OSUtils {
    public static String INFOCMP_COMMAND;
    public static final boolean IS_CYGWIN;
    public static final boolean IS_MINGW;
    public static final boolean IS_OSX;
    public static final boolean IS_WINDOWS;
    public static String STTY_COMMAND;
    public static String STTY_F_OPTION;
    public static String TTY_COMMAND;

    static {
        String str;
        String str2;
        String str3;
        String str4;
        boolean zContains = System.getProperty(SystemProperties.OS_NAME).toLowerCase().contains("win");
        IS_WINDOWS = zContains;
        boolean z = zContains && System.getenv("PWD") != null && System.getenv("PWD").startsWith("/") && !"cygwin".equals(System.getenv("TERM"));
        IS_CYGWIN = z;
        boolean z2 = zContains && System.getenv("MSYSTEM") != null && System.getenv("MSYSTEM").startsWith("MINGW");
        IS_MINGW = z2;
        boolean zContains2 = System.getProperty(SystemProperties.OS_NAME).toLowerCase().contains("mac");
        IS_OSX = zContains2;
        if (z || z2) {
            String str5 = System.getenv("PATH");
            str = "stty.exe";
            if (str5 == null) {
                str2 = "infocmp.exe";
                str3 = "tty.exe";
            } else {
                String absolutePath = "infocmp.exe";
                String absolutePath2 = "stty.exe";
                String absolutePath3 = "tty.exe";
                for (String str6 : str5.split(";")) {
                    if (new File(str6, "tty.exe").exists()) {
                        absolutePath3 = new File(str6, "tty.exe").getAbsolutePath();
                    }
                    if (new File(str6, "stty.exe").exists()) {
                        absolutePath2 = new File(str6, "stty.exe").getAbsolutePath();
                    }
                    if (new File(str6, "infocmp.exe").exists()) {
                        absolutePath = new File(str6, "infocmp.exe").getAbsolutePath();
                    }
                }
                str3 = absolutePath3;
                str = absolutePath2;
                str2 = absolutePath;
            }
            str4 = null;
        } else {
            str3 = "tty";
            str = "stty";
            str2 = "infocmp";
            if (zContains2) {
                str4 = "-f";
            } else {
                str4 = "-F";
            }
        }
        TTY_COMMAND = str3;
        STTY_COMMAND = str;
        STTY_F_OPTION = str4;
        INFOCMP_COMMAND = str2;
    }
}
