package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2115gt;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3017sq;
import com.android.tools.r8.internal.QW;
import com.android.tools.r8.internal.S10;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.jetbrains.kotlin.codegen.when.WhenByEnumsMapping;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class R0 {
    private final C0702m a;
    private final C0759u2 b;
    private final C0759u2 c;
    private final C0767v2 d;
    private final IdentityHashMap e = new IdentityHashMap();

    public R0(C0702m c0702m) {
        this.a = c0702m;
        C0722p1 c0722p1A = c0702m.a();
        this.b = c0722p1A.c("$SwitchMap$");
        this.c = c0722p1A.c(WhenByEnumsMapping.MAPPING_ARRAY_FIELD_PREFIX);
        this.d = c0722p1A.J1;
    }

    public final C3733i a() {
        Iterator<C0729q2> it = ((C3733i) this.a.f()).d().iterator();
        while (it.hasNext()) {
            a(it.next());
        }
        return !this.e.isEmpty() ? ((C3733i) this.a.f()).a(this.e) : (C3733i) this.a.f();
    }

    private void a(C0729q2 c0729q2) {
        if (c0729q2.e.q() && c0729q2.p1()) {
            List list = (List) c0729q2.N1().stream().filter(new Predicate() { // from class: com.android.tools.r8.ir.optimize.R0$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return this.f$0.a((com.android.tools.r8.graph.T0) obj);
                }
            }).collect(Collectors.toList());
            if (list.isEmpty()) {
                return;
            }
            final C2409kl c2409klA = c0729q2.X1().a(this.a);
            list.forEach(new Consumer() { // from class: com.android.tools.r8.ir.optimize.R0$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(c2409klA, (com.android.tools.r8.graph.T0) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(C2409kl c2409kl, com.android.tools.r8.graph.T0 t0) {
        AbstractC2412ko abstractC2412ko;
        final Y0 reference = t0.getReference();
        C3017sq c3017sq = new C3017sq();
        for (AbstractC2412ko abstractC2412ko2 : c2409kl.b(new Predicate() { // from class: com.android.tools.r8.ir.optimize.R0$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return R0.a(reference, (AbstractC2412ko) obj);
            }
        })) {
            abstractC2412ko2.getClass();
            for (AbstractC2412ko abstractC2412ko3 : (abstractC2412ko2 instanceof QW ? abstractC2412ko2.b() : abstractC2412ko2.G0().value()).h0()) {
                if (abstractC2412ko3.r1()) {
                    AbstractC2412ko abstractC2412ko4 = abstractC2412ko3.v().value().b;
                    if (abstractC2412ko4 == null || !abstractC2412ko4.B1()) {
                        return;
                    }
                    int iN2 = abstractC2412ko4.E().N2();
                    AbstractC2412ko abstractC2412ko5 = abstractC2412ko3.v().K2().b;
                    if (abstractC2412ko5 == null || !abstractC2412ko5.f2()) {
                        return;
                    }
                    C2115gt c2115gtI0 = abstractC2412ko5.i0();
                    AbstractC0727q0 abstractC0727q0C = this.a.c(c2115gtI0.S2().e);
                    if (abstractC0727q0C == null) {
                        return;
                    }
                    if ((!abstractC0727q0C.e.V() && abstractC0727q0C.d != this.a.a().e2) || (abstractC2412ko = ((S10) ((ArrayList) c2115gtI0.J2()).get(0)).b) == null || !(abstractC2412ko instanceof QW)) {
                        return;
                    }
                    Y0 field = abstractC2412ko.F0().getField();
                    if (!this.a.c(field.e).e.V() || c3017sq.a(iN2, field) != null) {
                        return;
                    }
                } else if (abstractC2412ko3 != abstractC2412ko2) {
                    return;
                }
            }
        }
        this.e.put(reference, c3017sq);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(Y0 y0, AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return ((abstractC2412ko instanceof QW) || abstractC2412ko.c()) && abstractC2412ko.N().getField() == y0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:10:0x0032 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:8:0x002c  */
    public boolean a(com.android.tools.r8.graph.T0 t0) {
        Y0 reference = t0.getReference();
        if (t0.f.q()) {
            C0759u2 c0759u2 = reference.f;
            C0759u2 c0759u3 = this.b;
            c0759u2.getClass();
            if (!c0759u2.a(c0759u3.e)) {
                C0759u2 c0759u4 = reference.f;
                C0759u2 c0759u5 = this.c;
                c0759u4.getClass();
                if (c0759u4.a(c0759u5.e)) {
                    if (reference.h == this.d) {
                        return true;
                    }
                }
            } else if (reference.h == this.d) {
                return true;
            }
        }
        return false;
    }
}
