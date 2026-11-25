package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.h3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2129h3 extends AbstractC2354k3 {
    public static C2279j3 a(int[] iArr) {
        AbstractC0936Ds.c(iArr, "<this>");
        return new C2279j3(iArr);
    }

    public static List b(int[] iArr) {
        AbstractC0936Ds.c(iArr, "<this>");
        int length = iArr.length;
        if (length == 0) {
            return C1940eg.a;
        }
        if (length == 1) {
            List listSingletonList = Collections.singletonList(Integer.valueOf(iArr[0]));
            AbstractC0936Ds.b(listSingletonList, "singletonList(element)");
            return listSingletonList;
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    public static List a(Object[] objArr) {
        AbstractC0936Ds.c(objArr, "<this>");
        List listAsList = Arrays.asList(objArr);
        AbstractC0936Ds.b(listAsList, "asList(this)");
        return listAsList;
    }

    public static Integer a(int[] iArr, int i) {
        AbstractC0936Ds.c(iArr, "<this>");
        if (i < 0 || i > iArr.length - 1) {
            return null;
        }
        return Integer.valueOf(iArr[i]);
    }
}
