package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Vz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1466Vz {
    static final /* synthetic */ boolean c = true;
    private int a;
    private final Set b;

    public C1466Vz(C0702m c0702m) {
        this.b = c0702m.Y().n1.p0 ? OU.c() : null;
    }

    public final void a(AbstractC0642e2 abstractC0642e2) {
        this.a++;
        Set set = this.b;
        if (set != null) {
            set.add(abstractC0642e2);
        }
    }

    public final boolean a(C1466Vz c1466Vz, final C0702m c0702m) {
        if (this.a != c1466Vz.a) {
            return false;
        }
        Set set = this.b;
        if (set == null) {
            return true;
        }
        boolean z = c;
        if (!z && c1466Vz.b == null) {
            throw new AssertionError();
        }
        if (!z && set.size() != c1466Vz.b.size()) {
            throw new AssertionError();
        }
        NU nuA = OU.a(this.b, c1466Vz.b);
        if (nuA.isEmpty()) {
            return true;
        }
        final NU nuA2 = OU.a(c1466Vz.b, this.b);
        nuA.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Vz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, nuA2, (AbstractC0642e2) obj);
            }
        });
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0702m c0702m, NU nu, AbstractC0642e2 abstractC0642e2) {
        Object objC;
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        AbstractC1476Wj abstractC1476WjW = c0702m.w();
        abstractC1476WjE.getClass();
        if (abstractC0642e2.x0()) {
            objC = abstractC1476WjE.b(abstractC1476WjW, abstractC0642e2.t0());
        } else {
            objC = abstractC1476WjE.c(abstractC1476WjW, abstractC0642e2.v0());
        }
        boolean z = c;
        if (!z && !nu.contains(objC)) {
            throw new AssertionError();
        }
        if (!z && com.android.tools.r8.utils.I1.b(abstractC0642e2.D0(), new Predicate() { // from class: com.android.tools.r8.internal.Vz$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0702m, (C0767v2) obj);
            }
        }) == null) {
            throw new AssertionError();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0702m c0702m, C0767v2 c0767v2) {
        C0759u2 c0759u2 = c0767v2.e;
        C0759u2 c0759u3 = c0702m.a().z4.f;
        c0759u2.getClass();
        return c0759u2.a(c0759u3.e);
    }
}
