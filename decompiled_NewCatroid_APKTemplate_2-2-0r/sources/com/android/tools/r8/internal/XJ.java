package com.android.tools.r8.internal;

import java.lang.reflect.Array;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class XJ {
    static Object[] a(int i, Object[] objArr) {
        for (int i2 = 0; i2 < i; i2++) {
            if (objArr[i2] == null) {
                throw new NullPointerException(new StringBuilder(20).append("at index ").append(i2).toString());
            }
        }
        return objArr;
    }

    public static Object[] b(int i, Object[] objArr) {
        int i2 = TL.a;
        return (Object[]) Array.newInstance(objArr.getClass().getComponentType(), i);
    }
}
