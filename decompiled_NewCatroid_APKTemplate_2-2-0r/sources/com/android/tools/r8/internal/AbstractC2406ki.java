package com.android.tools.r8.internal;

import java.io.File;

/* JADX INFO: renamed from: com.android.tools.r8.internal.ki, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2406ki {
    public static String a(String str) {
        int i = AbstractC3123uM.a;
        str.getClass();
        String name2 = new File(str).getName();
        int iLastIndexOf = name2.lastIndexOf(46);
        return iLastIndexOf == -1 ? "" : name2.substring(iLastIndexOf + 1);
    }
}
