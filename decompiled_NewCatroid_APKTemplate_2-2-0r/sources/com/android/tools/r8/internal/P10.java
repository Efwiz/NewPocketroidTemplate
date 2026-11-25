package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class P10 {
    static List a(List list, Object obj) {
        if (list == null) {
            list = new ArrayList(1);
        }
        list.add(obj);
        return list;
    }

    static ArrayList a(int i) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(null);
        }
        return arrayList;
    }

    static ArrayList a(Object[] objArr) {
        if (objArr == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(objArr.length);
        for (Object obj : objArr) {
            arrayList.add(obj);
        }
        return arrayList;
    }

    static ArrayList a(int[] iArr) {
        if (iArr == null) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i : iArr) {
            arrayList.add(Integer.valueOf(i));
        }
        return arrayList;
    }

    static ArrayList a(int i, Object[] objArr) {
        ArrayList arrayList = new ArrayList(i);
        for (int i2 = 0; i2 < i; i2++) {
            arrayList.add(objArr[i2]);
        }
        return arrayList;
    }
}
