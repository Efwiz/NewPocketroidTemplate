package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0612b4;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.G00;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class E1 {
    private final com.android.tools.r8.utils.A1 a;
    private final ArrayList b = new ArrayList();

    public E1(com.android.tools.r8.utils.A1 a1) {
        this.a = a1;
    }

    public final AbstractC0681j0 a(C0612b4 c0612b4, G00 g00) {
        Set<C0767v2> setR = this.a.C1.r();
        Set setKeySet = this.a.C1.m().keySet();
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        ArrayList arrayList = new ArrayList();
        boolean zD1 = false;
        for (C0729q2 c0729q2 : c0612b4.d()) {
            identityHashMap.put(c0729q2.d, c0729q2);
            if (g00.a((C0702m) null, c0729q2.d) || setKeySet.contains(c0729q2.d) || setR.contains(c0729q2.d)) {
                arrayList.add(c0729q2);
            } else {
                zD1 |= c0729q2.D1();
                this.b.add(c0729q2.d);
            }
        }
        if (zD1) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                k4.a((C0729q2) it.next(), new Predicate() { // from class: com.android.tools.r8.shaking.E1$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return this.f$0.a((C0767v2) obj);
                    }
                }, new Function() { // from class: com.android.tools.r8.shaking.E1$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return (AbstractC0727q0) identityHashMap.get((C0767v2) obj);
                    }
                });
            }
        }
        identityHashMap.clear();
        return c0612b4.c().a(arrayList).b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0767v2 c0767v2) {
        return !this.b.contains(c0767v2);
    }
}
