package com.android.tools.r8.utils;

import com.android.dex.DexFormat;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class F0 {
    public static String a(int i) {
        if (i == 0) {
            return DexFormat.DEX_IN_JAR_NAME;
        }
        return "classes" + (i + 1) + ".dex";
    }
}
