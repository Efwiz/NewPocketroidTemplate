package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.o2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public final class C3943o2 {
    private final String[] a;
    private int b = 0;

    public C3943o2(String[] strArr) {
        this.a = strArr;
    }

    public final String a() {
        int i = this.b;
        String[] strArr = this.a;
        if (i < strArr.length) {
            return strArr[i];
        }
        return null;
    }

    public final String b() {
        int i = this.b;
        if (i >= this.a.length) {
            throw new RuntimeException("Iterating over the end of argument list.");
        }
        this.b = i + 1;
        return a();
    }
}
