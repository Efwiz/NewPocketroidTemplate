package com.android.tools.r8.internal;

/* JADX INFO: renamed from: com.android.tools.r8.internal.l9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2443l9 extends AbstractC2518m9 {
    public static void a() {
        C1247Or c1247Or = new C1247Or(2, 36);
        if (c1247Or.a() > 10 || 10 > c1247Or.b()) {
            throw new IllegalArgumentException("radix 10 was not in valid range " + new C1247Or(2, 36));
        }
    }
}
