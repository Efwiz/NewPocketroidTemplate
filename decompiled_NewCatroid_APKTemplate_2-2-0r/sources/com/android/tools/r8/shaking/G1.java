package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.G5;
import com.android.tools.r8.internal.AbstractC2412ko;
import com.android.tools.r8.internal.C2405kh;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.OU;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G1 {
    static final /* synthetic */ boolean c = true;
    private final C0702m a;
    private final Set b;

    public G1(C0702m c0702m) {
        this.a = c0702m;
        this.b = Collections.synchronizedSet(b(c0702m));
    }

    private static Set a(C0702m c0702m) {
        final Set setC = OU.c();
        G5.b(c0702m).a(((C3733i) c0702m.f()).d(), new Consumer() { // from class: com.android.tools.r8.shaking.G1$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                G1.a(setC, (C0729q2) obj);
            }
        });
        return setC;
    }

    private static Set b(C0702m c0702m) {
        final Set setA = a(c0702m);
        AbstractC3735i1 abstractC3735i1W = ((C3733i) c0702m.f()).w();
        Objects.requireNonNull(setA);
        abstractC3735i1W.c(new Consumer() { // from class: com.android.tools.r8.shaking.G1$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                setA.remove((C0767v2) obj);
            }
        }, c0702m.Y());
        return setA;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(Set set, C0729q2 c0729q2) {
        for (com.android.tools.r8.graph.W0 w0 : c0729q2.P1()) {
            if (!w0.f.W() && w0.D1().c()) {
                set.add(c0729q2.d);
            }
        }
        if (!set.contains(c0729q2.f)) {
            for (C0767v2 c0767v2 : c0729q2.g.a) {
                if (!set.contains(c0767v2)) {
                }
            }
            return;
        }
        set.add(c0729q2.d);
    }

    public final void a(C2409kl c2409kl) {
        C0767v2 c0767v2;
        AbstractC0727q0 abstractC0727q0C;
        if (this.b.isEmpty()) {
            return;
        }
        C2405kh c2405kh = new C2405kh(this.a, F1.a);
        for (AbstractC2412ko abstractC2412ko : c2409kl.r()) {
            if (abstractC2412ko.p2() && (abstractC0727q0C = this.a.c((c0767v2 = abstractC2412ko.t0().h))) != null && abstractC0727q0C.e0() && this.b.contains(c0767v2) && c2405kh.b(c2409kl, abstractC2412ko.b())) {
                this.b.remove(c0767v2);
            }
        }
    }

    public final void a() {
        if (!c) {
            Set setA = a(this.a);
            for (C0729q2 c0729q2 : ((C3733i) this.a.f()).d()) {
                if (!c && !((C3733i) this.a.f()).b(c0729q2) && setA.contains(c0729q2.d) && !this.b.contains(c0729q2.d)) {
                    throw new AssertionError();
                }
            }
        }
        this.a.a(new Predicate() { // from class: com.android.tools.r8.shaking.G1$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((C0767v2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0767v2 c0767v2) {
        return !this.b.contains(c0767v2);
    }
}
