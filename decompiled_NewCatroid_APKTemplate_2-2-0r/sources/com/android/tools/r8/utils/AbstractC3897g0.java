package com.android.tools.r8.utils;

import com.android.tools.r8.internal.C3405y10;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: renamed from: com.android.tools.r8.utils.g0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes8.dex */
public abstract class AbstractC3897g0 {
    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(Comparator comparator, List list, List list2) {
        int iCompare = Integer.compare(list.size(), list2.size());
        for (int i = 0; i < list.size() && iCompare == 0; i++) {
            iCompare = comparator.compare(list.get(i), list2.get(i));
        }
        return iCompare;
    }

    public static Comparator b(final Comparator comparator) {
        return new Comparator() { // from class: com.android.tools.r8.utils.g0$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractC3897g0.a(comparator, (List) obj, (List) obj2);
            }
        };
    }

    public static Comparator a(final Comparator comparator) {
        return new Comparator() { // from class: com.android.tools.r8.utils.g0$$ExternalSyntheticLambda2
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractC3897g0.a(comparator, (Object[]) obj, (Object[]) obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(Comparator comparator, Object[] objArr, Object[] objArr2) {
        int iCompare = Integer.compare(objArr.length, objArr2.length);
        for (int i = 0; i < objArr.length && iCompare == 0; i++) {
            iCompare = comparator.compare(objArr[i], objArr2[i]);
        }
        return iCompare;
    }

    public static Comparator a() {
        return new Comparator() { // from class: com.android.tools.r8.utils.g0$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractC3897g0.a(obj, obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(Object obj, Object obj2) {
        throw new C3405y10();
    }
}
