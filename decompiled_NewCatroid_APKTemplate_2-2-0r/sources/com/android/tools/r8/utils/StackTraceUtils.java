package com.android.tools.r8.utils;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class StackTraceUtils {
    private static final PrintStream a;
    private static final int b;
    private static int c;

    static {
        String property = System.getProperty("com.android.tools.r8.internalPathToStacktraces");
        if (property == null) {
            throw new RuntimeException("pathToWriteStacktrace is null");
        }
        try {
            a = new PrintStream(property, StandardCharsets.UTF_8.name());
            String property2 = System.getProperty("com.android.tools.r8.internalStackTraceSamplingInterval");
            b = property2 == null ? 1000 : Integer.parseInt(property2);
            c = 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printCurrentStack(long j) {
        int i = c;
        c = i + 1;
        if (i < b) {
            RuntimeException runtimeException = new RuntimeException("------(" + j + "," + c + ")------");
            PrintStream printStream = a;
            runtimeException.printStackTrace(printStream);
            printStream.println("@@@@");
        }
    }
}
