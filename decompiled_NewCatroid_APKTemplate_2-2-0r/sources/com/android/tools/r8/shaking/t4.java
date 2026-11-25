package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.C2173hh;
import com.android.tools.r8.internal.C2537mR;
import com.android.tools.r8.utils.C3894f2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class t4 {
    private final C2537mR a = new C2537mR();
    private final C3894f2 b = C3894f2.a();
    private final HashSet c = new HashSet();
    final /* synthetic */ x4 d;

    public t4(x4 x4Var) {
        this.d = x4Var;
        Iterator it = x4Var.l.iterator();
        while (it.hasNext()) {
            this.c.add(x4Var.e.d(((C0729q2) it.next()).d));
        }
    }

    public final ArrayList a() {
        HashMap map = new HashMap();
        HashSet hashSet = new HashSet();
        Iterator<C0729q2> it = this.d.b.d().iterator();
        while (it.hasNext()) {
            Iterator<com.android.tools.r8.graph.W0> it2 = it.next().M1().iterator();
            while (it2.hasNext()) {
                final C0684j2 reference = it2.next().getReference();
                AbstractC0727q0 abstractC0727q0C = this.d.b.c(reference.e);
                if (abstractC0727q0C != null && abstractC0727q0C.e0() && a(reference.h)) {
                    hashSet.add(this.b.c(reference));
                    C0737r2 c0737r2 = (C0737r2) map.computeIfAbsent(reference.f, new Function() { // from class: com.android.tools.r8.shaking.t4$$ExternalSyntheticLambda0
                        @Override // java.util.function.Function
                        public final Object apply(Object obj) {
                            return t4.a(reference, (C0759u2) obj);
                        }
                    });
                    C0737r2 c0737r3 = C0737r2.g;
                    if (c0737r2 != c0737r3 && !c0737r2.equals(reference.h)) {
                        map.put(reference.f, c0737r3);
                    }
                }
            }
        }
        ArrayList arrayList = new ArrayList();
        Iterator it3 = hashSet.iterator();
        while (it3.hasNext()) {
            C0684j2 c0684j2 = (C0684j2) ((C2173hh) it3.next()).a();
            if (map.get(c0684j2.f) == C0737r2.g) {
                arrayList.add(c0684j2);
            }
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0737r2 a(C0684j2 c0684j2, C0759u2 c0759u2) {
        return c0684j2.h;
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0053  */
    /* JADX WARN: Code duplicated, block: B:30:0x00a0  */
    private boolean a(C0737r2 c0737r2) {
        boolean zContains;
        boolean zContains2;
        if (this.a.containsKey(c0737r2)) {
            return this.a.b(c0737r2);
        }
        C0767v2 c0767v2B = c0737r2.e.b(this.d.c.a());
        boolean z = true;
        if (!c0767v2B.O0()) {
            zContains = false;
        } else if (this.c.contains(c0767v2B)) {
            zContains = true;
        } else {
            AbstractC0727q0 abstractC0727q0C = this.d.b.c(c0767v2B);
            if (abstractC0727q0C == null || !abstractC0727q0C.e0()) {
                zContains = false;
            } else {
                zContains = this.d.l.contains(abstractC0727q0C.a0());
            }
        }
        if (!zContains) {
            for (C0767v2 c0767v2 : c0737r2.f.a) {
                C0767v2 c0767v2B2 = c0767v2.b(this.d.c.a());
                if (!c0767v2B2.O0()) {
                    zContains2 = false;
                } else if (this.c.contains(c0767v2B2)) {
                    zContains2 = true;
                } else {
                    AbstractC0727q0 abstractC0727q0C2 = this.d.b.c(c0767v2B2);
                    if (abstractC0727q0C2 == null || !abstractC0727q0C2.e0()) {
                        zContains2 = false;
                    } else {
                        zContains2 = this.d.l.contains(abstractC0727q0C2.a0());
                    }
                }
                if (!zContains2) {
                }
            }
            z = false;
        }
        this.a.a(c0737r2, z);
        return z;
    }
}
