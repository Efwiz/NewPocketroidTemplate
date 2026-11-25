package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3533zt {
    static AbstractC2963s10 a() {
        return C3316wt.e;
    }

    public static boolean b(Collection collection, Iterator it) {
        int i = AbstractC3123uM.a;
        boolean z = false;
        while (it.hasNext()) {
            if (collection.contains(it.next())) {
                it.remove();
                z = true;
            }
        }
        return z;
    }

    public static boolean a(Collection collection, Iterator it) {
        int i = AbstractC3123uM.a;
        collection.getClass();
        it.getClass();
        boolean zAdd = false;
        while (it.hasNext()) {
            zAdd |= collection.add(it.next());
        }
        return zAdd;
    }

    public static Iterator a(List list) {
        int i = AbstractC3123uM.a;
        list.getClass();
        return new C2881qt(list);
    }

    public static Iterator a(Iterator it, Iterator it2) {
        int i = AbstractC3123uM.a;
        it.getClass();
        it2.getClass();
        return new C3393xt(new C2951rt(new Iterator[]{it, it2}));
    }

    public static Iterator a(int i, Iterator it) {
        int i2 = AbstractC3123uM.a;
        it.getClass();
        AbstractC3123uM.a("limit is negative", i >= 0);
        return new C3167ut(i, it);
    }

    public static AbstractC2963s10 a(Object... objArr) {
        int length = objArr.length;
        AbstractC3123uM.a(length >= 0);
        AbstractC3123uM.a(0, length, objArr.length);
        AbstractC3123uM.b(0, length);
        if (length == 0) {
            return C3316wt.e;
        }
        return new C3316wt(objArr, length, 0);
    }

    public static AbstractC2963s10 a(Object obj) {
        return new C3241vt(obj);
    }
}
