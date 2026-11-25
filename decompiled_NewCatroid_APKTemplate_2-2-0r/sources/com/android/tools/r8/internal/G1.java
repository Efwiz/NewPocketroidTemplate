package com.android.tools.r8.internal;

import java.util.HashSet;
import java.util.Set;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public enum G1 {
    PUBLIC,
    PROTECTED,
    PACKAGE_PRIVATE,
    PRIVATE;

    private static final AbstractC1048Hm e = AbstractC1242Om.a((Comparable[]) values());

    public static Set a() {
        return e;
    }

    public static HashSet b() {
        return new HashSet();
    }

    public final String c() {
        int iOrdinal = ordinal();
        if (iOrdinal == 0) {
            return "public";
        }
        if (iOrdinal == 1) {
            return "protected";
        }
        if (iOrdinal == 2) {
            throw new C2883qv("No source syntax for package-private visibility.");
        }
        if (iOrdinal == 3) {
            return "private";
        }
        throw new C2883qv("Unexpected access visibility: " + this);
    }
}
