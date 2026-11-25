package com.android.tools.r8.internal;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class TL {
    public static final /* synthetic */ int a = 0;

    static {
        Logger.getLogger(TL.class.getName());
    }

    static HashMap a() {
        return RD.b(12);
    }

    static LinkedHashMap b() {
        return new LinkedHashMap();
    }

    static Object[] a(int i, int i2, Object[] objArr, Object[] objArr2) {
        return Arrays.copyOfRange(objArr, i, i2, objArr2.getClass());
    }
}
