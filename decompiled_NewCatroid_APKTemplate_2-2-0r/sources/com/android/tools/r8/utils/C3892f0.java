package com.android.tools.r8.utils;

import com.android.tools.r8.internal.AbstractC1048Hm;
import com.android.tools.r8.internal.C0862Bm;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.utils.f0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public class C3892f0 {
    public static AbstractC1048Hm a(Collection collection, Set set) {
        C0862Bm c0862BmG = AbstractC1048Hm.g();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            c0862BmG.a(it.next());
        }
        Iterator it2 = set.iterator();
        while (it2.hasNext()) {
            c0862BmG.a(it2.next());
        }
        return c0862BmG.a();
    }

    public static <T> Collection<T> a(Collection<T> collection, Comparator<T> comparator) {
        ArrayList arrayList = new ArrayList(collection);
        arrayList.sort(comparator);
        return arrayList;
    }

    public static String[] a(List list, Function function) {
        String[] strArr = new String[list.size()];
        Iterator it = list.iterator();
        int i = 0;
        while (it.hasNext()) {
            strArr[i] = (String) function.apply(it.next());
            i++;
        }
        return strArr;
    }
}
