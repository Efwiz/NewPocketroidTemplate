package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

/* JADX INFO: renamed from: com.android.tools.r8.internal.pC, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2763pC {
    public static ArrayList a(Object... objArr) {
        int i = AbstractC3123uM.a;
        objArr.getClass();
        int length = objArr.length;
        AbstractC2625nb.a(length, "arraySize");
        ArrayList arrayList = new ArrayList(AbstractC0968Es.a(((long) length) + 5 + ((long) (length / 10))));
        Collections.addAll(arrayList, objArr);
        return arrayList;
    }

    public static ArrayList a(Iterable iterable) {
        int i = AbstractC3123uM.a;
        iterable.getClass();
        if (iterable instanceof Collection) {
            return new ArrayList((Collection) iterable);
        }
        Iterator it = iterable.iterator();
        ArrayList arrayList = new ArrayList();
        AbstractC3533zt.a(arrayList, it);
        return arrayList;
    }

    public static List a(List list) {
        if (list instanceof AbstractC1639am) {
            return ((AbstractC1639am) list).j();
        }
        if (list instanceof C2686oC) {
            return ((C2686oC) list).a();
        }
        if (list instanceof RandomAccess) {
            return new C2521mC(list);
        }
        return new C2686oC(list);
    }
}
