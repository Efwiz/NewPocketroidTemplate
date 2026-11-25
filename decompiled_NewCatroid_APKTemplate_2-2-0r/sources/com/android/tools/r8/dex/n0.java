package com.android.tools.r8.dex;

import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.internal.AbstractC3135uW;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class n0 {
    private final List a;
    private final List b;

    private n0(ArrayList arrayList, ArrayList arrayList2) {
        this.a = arrayList;
        this.b = arrayList2;
    }

    public static n0 a(Collection collection, IdentityHashMap identityHashMap, AbstractC3135uW abstractC3135uW) {
        Comparator comparatorA = a(identityHashMap);
        Predicate predicateA = a(abstractC3135uW);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            if (predicateA.test(c0729q2)) {
                arrayList.add(c0729q2);
            } else {
                arrayList2.add(c0729q2);
            }
        }
        arrayList.sort(comparatorA);
        arrayList2.sort(comparatorA);
        return new n0(arrayList, arrayList2);
    }

    private static Comparator a(final IdentityHashMap identityHashMap) {
        return new Comparator() { // from class: com.android.tools.r8.dex.n0$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return n0.a(identityHashMap, (C0729q2) obj, (C0729q2) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(Map map, C0729q2 c0729q2, C0729q2 c0729q3) {
        String str = (String) map.get(c0729q2);
        String str2 = (String) map.get(c0729q3);
        int iLastIndexOf = str.lastIndexOf(46);
        int iLastIndexOf2 = str2.lastIndexOf(46);
        if (iLastIndexOf == -1 && iLastIndexOf2 == -1) {
            return str.compareTo(str2);
        }
        if (iLastIndexOf == -1) {
            return -1;
        }
        if (iLastIndexOf2 == -1) {
            return 1;
        }
        int iCompareTo = str.substring(0, iLastIndexOf).compareTo(str2.substring(0, iLastIndexOf2));
        return iCompareTo != 0 ? iCompareTo : str.compareTo(str2);
    }

    private static Predicate a(final AbstractC3135uW abstractC3135uW) {
        return new Predicate() { // from class: com.android.tools.r8.dex.n0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return n0.a(abstractC3135uW, (C0729q2) obj);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(AbstractC3135uW abstractC3135uW, C0729q2 c0729q2) {
        return abstractC3135uW.b(c0729q2.getType());
    }
}
