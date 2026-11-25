package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC2625nb {
    static void a(Object obj, Object obj2) {
        if (obj == null) {
            String strValueOf = String.valueOf(obj2);
            throw new NullPointerException(new StringBuilder(strValueOf.length() + 24).append("null key in entry: null=").append(strValueOf).toString());
        }
        if (obj2 != null) {
            return;
        }
        String strValueOf2 = String.valueOf(obj);
        throw new NullPointerException(new StringBuilder(strValueOf2.length() + 26).append("null value in entry: ").append(strValueOf2).append("=null").toString());
    }

    static void a(int i, String str) {
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuilder(str.length() + 40).append(str).append(" cannot be negative but was: ").append(i).toString());
        }
    }
}
