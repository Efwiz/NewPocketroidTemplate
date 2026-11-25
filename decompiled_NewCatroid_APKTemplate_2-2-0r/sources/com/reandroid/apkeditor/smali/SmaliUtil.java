package com.reandroid.apkeditor.smali;

import com.android.dex.DexFormat;
import java.io.File;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class SmaliUtil {
    static final String CACHE_DIR = ".cache";

    static void sortDexFiles(List<File> list) {
        list.sort(new Comparator<File>() { // from class: com.reandroid.apkeditor.smali.SmaliUtil.1
            @Override // java.util.Comparator
            public int compare(File file, File file2) {
                int dexNumber = SmaliUtil.getDexNumber(file.getName());
                int dexNumber2 = SmaliUtil.getDexNumber(file2.getName());
                if (dexNumber == dexNumber2) {
                    return 0;
                }
                return (dexNumber < 0 || dexNumber < dexNumber2) ? -1 : 1;
            }
        });
    }

    static String getDexFileName(int i) {
        if (i == 0) {
            return DexFormat.DEX_IN_JAR_NAME;
        }
        return "classes" + i + ".dex";
    }

    static boolean isClassesDir(File file) {
        return file.isDirectory() && getDexNumber(file.getName()) >= 0;
    }

    static int getDexNumber(String str) {
        if (str.equals("classes") || str.equals(DexFormat.DEX_IN_JAR_NAME)) {
            return 0;
        }
        if (!str.startsWith("classes")) {
            return -1;
        }
        String strSubstring = str.substring("classes".length());
        if (strSubstring.endsWith(".dex")) {
            strSubstring = strSubstring.substring(0, strSubstring.length() - ".dex".length());
        }
        try {
            return Integer.parseInt(strSubstring);
        } catch (NumberFormatException unused) {
            return -1;
        }
    }
}
