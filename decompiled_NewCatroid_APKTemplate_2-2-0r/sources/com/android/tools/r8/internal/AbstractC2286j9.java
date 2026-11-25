package com.android.tools.r8.internal;

import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: renamed from: com.android.tools.r8.internal.j9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2286j9 {
    public static String a(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        int i = AbstractC3123uM.a;
        char[] cArr = new char[2048];
        while (true) {
            int i2 = inputStreamReader.read(cArr);
            if (i2 == -1) {
                return sb.toString();
            }
            sb.append(cArr, 0, i2);
        }
    }
}
