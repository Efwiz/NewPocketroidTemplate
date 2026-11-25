package org.antlr.runtime.misc;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

/* JADX INFO: loaded from: classes12.dex */
public class Stats {
    public static final String ANTLRWORKS_DIR = "antlrworks";

    public static double stddev(int[] iArr) {
        int length = iArr.length;
        double d = 0.0d;
        if (length <= 1) {
            return 0.0d;
        }
        double dAvg = avg(iArr);
        for (int i : iArr) {
            d += (((double) i) - dAvg) * (((double) i) - dAvg);
        }
        return Math.sqrt(d / ((double) (length - 1)));
    }

    public static double avg(int[] iArr) {
        int length = iArr.length;
        if (length == 0) {
            return 0.0d;
        }
        double d = 0.0d;
        for (int i : iArr) {
            d += (double) i;
        }
        if (d >= 0.0d) {
            return d / ((double) length);
        }
        return 0.0d;
    }

    public static int min(int[] iArr) {
        if (iArr.length == 0) {
            return 0;
        }
        int i = Integer.MAX_VALUE;
        for (int i2 : iArr) {
            if (i2 < i) {
                i = i2;
            }
        }
        return i;
    }

    public static int max(int[] iArr) {
        if (iArr.length == 0) {
            return 0;
        }
        int i = Integer.MIN_VALUE;
        for (int i2 : iArr) {
            if (i2 > i) {
                i = i2;
            }
        }
        return i;
    }

    public static double avg(List<Integer> list) {
        int size = list.size();
        if (size == 0) {
            return 0.0d;
        }
        double dIntValue = 0.0d;
        for (int i = 0; i < size; i++) {
            dIntValue += (double) list.get(i).intValue();
        }
        if (dIntValue >= 0.0d) {
            return dIntValue / ((double) size);
        }
        return 0.0d;
    }

    public static int min(List<Integer> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iIntValue = Integer.MAX_VALUE;
        for (int i = 0; i < size; i++) {
            if (list.get(i).intValue() < iIntValue) {
                iIntValue = list.get(i).intValue();
            }
        }
        return iIntValue;
    }

    public static int max(List<Integer> list) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        int iIntValue = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (list.get(i).intValue() > iIntValue) {
                iIntValue = list.get(i).intValue();
            }
        }
        return iIntValue;
    }

    public static int sum(int[] iArr) {
        if (iArr.length == 0) {
            return 0;
        }
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        return i;
    }

    public static void writeReport(String str, String str2) throws IOException {
        File file = new File(getAbsoluteFileName(str));
        file.getParentFile().mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(file, true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        PrintStream printStream = new PrintStream(bufferedOutputStream);
        printStream.println(str2);
        printStream.close();
        bufferedOutputStream.close();
        fileOutputStream.close();
    }

    public static String getAbsoluteFileName(String str) {
        return System.getProperty("user.home") + File.separator + ANTLRWORKS_DIR + File.separator + str;
    }
}
