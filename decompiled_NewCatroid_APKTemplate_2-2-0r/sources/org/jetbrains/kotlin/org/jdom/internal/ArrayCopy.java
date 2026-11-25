package org.jetbrains.kotlin.org.jdom.internal;

import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes4.dex */
public final class ArrayCopy {
    public static final <E> E[] copyOf(E[] eArr, int i) {
        E[] eArr2 = (E[]) ((Object[]) Array.newInstance(eArr.getClass().getComponentType(), i));
        if (i >= eArr.length) {
            i = eArr.length;
        }
        System.arraycopy(eArr, 0, eArr2, 0, i);
        return eArr2;
    }

    public static final int[] copyOf(int[] iArr, int i) {
        int[] iArr2 = new int[i];
        if (i >= iArr.length) {
            i = iArr.length;
        }
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }
}
