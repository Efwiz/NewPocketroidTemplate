package com.android.tools.r8.utils;

/* JADX INFO: renamed from: com.android.tools.r8.utils.h0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract /* synthetic */ class AbstractC3902h0 {
    public static int a(int i) {
        if (i < 0) {
            return 1;
        }
        return i == 0 ? 2 : 3;
    }

    public static final boolean b(int i) {
        return i == 2;
    }

    public static /* synthetic */ int c(int i) {
        if (i == 1) {
            return -1;
        }
        if (i == 2) {
            return 0;
        }
        if (i == 3) {
            return 1;
        }
        throw null;
    }
}
