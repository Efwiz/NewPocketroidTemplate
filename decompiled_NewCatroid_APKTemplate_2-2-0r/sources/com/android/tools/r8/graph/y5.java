package com.android.tools.r8.graph;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class y5 extends r5 {
    public y5(String str) {
        super(str, new Supplier() { // from class: com.android.tools.r8.graph.y5$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return y5.c();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int a(C0729q2 c0729q2, C0729q2 c0729q3) {
        return c0729q2.getType().compareTo(c0729q3.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set c() {
        return new TreeSet(new Comparator() { // from class: com.android.tools.r8.graph.y5$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return y5.a((C0729q2) obj, (C0729q2) obj2);
            }
        });
    }
}
