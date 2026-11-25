package com.android.tools.r8.utils;

import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class E {
    static final /* synthetic */ boolean a = true;

    public static boolean a(Object obj) {
        if (a || obj != null) {
            return true;
        }
        throw new AssertionError();
    }

    public static boolean a(A1 a1, Supplier supplier) {
        if (a1.n1.p0) {
            return ((Boolean) supplier.get()).booleanValue();
        }
        return true;
    }
}
