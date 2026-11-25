package com.android.tools.r8.internal;

import java.util.Comparator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kb, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC1122Kb {
    public static Comparator a() {
        final C2767pG c2767pG = C2767pG.a;
        return new Comparator() { // from class: com.android.tools.r8.internal.Kb$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return AbstractC1122Kb.a(c2767pG, obj, obj2);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int a(Comparator comparator, Object obj, Object obj2) {
        AbstractC0936Ds.c(comparator, "$comparator");
        if (obj == obj2) {
            return 0;
        }
        if (obj == null) {
            return 1;
        }
        if (obj2 == null) {
            return -1;
        }
        return comparator.compare(obj, obj2);
    }
}
