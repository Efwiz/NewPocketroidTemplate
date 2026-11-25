package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.k8, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC2360k8 {
    public static AbstractC2360k8 a(C0702m c0702m, C1952es c1952es, Predicate predicate) {
        if (!c0702m.Y().A0.b()) {
            return C2210i8.a;
        }
        ArrayList arrayList = new ArrayList();
        if (c0702m.Y().C1.C() && !c0702m.Y().C1.d()) {
            arrayList.add(new C1066Ie(c0702m));
        }
        if (c1952es != null) {
            arrayList.add(c1952es);
        }
        C3301we c3301we = c0702m.t.a() ? new C3301we(c0702m, predicate) : null;
        if (c3301we != null) {
            arrayList.add(c3301we);
        }
        EQ eqA = EQ.a(c0702m);
        if (eqA != null) {
            arrayList.add(eqA);
        }
        C0920De c0920DeA = C0920De.a(c0702m);
        if (c0920DeA != null) {
            arrayList.add(c0920DeA);
        }
        return arrayList.isEmpty() ? C2210i8.a : new C2285j8(arrayList);
    }

    public abstract void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService);
}
