package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public enum N3 {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    PACKAGE_PRIVATE;

    @Override // java.lang.Enum
    public final String toString() {
        int i = M3.a[ordinal()];
        if (i == 1) {
            return "public";
        }
        if (i == 2) {
            return "protected";
        }
        if (i == 3) {
            return "private";
        }
        if (i == 4) {
            return "package-private";
        }
        throw new C3405y10("Unexpected visibility");
    }
}
