package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0729q2;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class UF<T> extends SF {
    public abstract T a(C0729q2 c0729q2);

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = c2688oE.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            T tA = a(c0729q2);
            if (tA != null) {
                ((C2688oE) linkedHashMap.computeIfAbsent(tA, new Function() { // from class: com.android.tools.r8.internal.UF$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return UF.a(obj);
                    }
                })).a(c0729q2);
            }
        }
        WL.a(linkedHashMap.values());
        return linkedHashMap.values();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C2688oE a(Object obj) {
        return new C2688oE();
    }
}
