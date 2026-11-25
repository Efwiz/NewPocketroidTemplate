package com.android.tools.r8.internal;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class WD {
    static void a(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(str.length() + 27).append(str).append(" (").append(i).append(") must be >= 0").toString());
        }
    }
}
