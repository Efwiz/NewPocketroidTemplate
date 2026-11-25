package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.shaking.C3733i;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class RH extends SH {
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C2409kl b;
    private final DH c;
    private final IdentityHashMap d = new IdentityHashMap();

    RH(C0702m c0702m, C2409kl c2409kl, DH dh) {
        this.a = c0702m;
        this.b = c2409kl;
        this.c = dh;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3332x4 c3332x4, C3332x4 c3332x5) {
        return c3332x5 == c3332x4;
    }

    @Override // com.android.tools.r8.internal.SH
    public final void a(AbstractC1305Qs abstractC1305Qs, AbstractC1305Qs abstractC1305Qs2, KE ke) {
        if (abstractC1305Qs.Z1() && (abstractC1305Qs2 instanceof C1721bt)) {
            com.android.tools.r8.graph.proto.b bVarA = ke.e().a().a(0);
            bVarA.getClass();
            if (bVarA instanceof com.android.tools.r8.graph.proto.j) {
                S10 s10U2 = abstractC1305Qs.c0().U2();
                if (s10U2.w().x() || s10U2.b(new Predicate() { // from class: com.android.tools.r8.internal.RH$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return ((AbstractC2412ko) obj).D2();
                    }
                })) {
                    return;
                }
                this.d.put(abstractC1305Qs2.g0(), s10U2);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:50:0x0138  */
    /* JADX WARN: Code duplicated, block: B:51:0x0151  */
    /* JADX WARN: Code duplicated, block: B:54:0x0165  */
    @Override // com.android.tools.r8.internal.SH
    public final void a() {
        AbstractC1915eM abstractC1915eMA;
        AbstractC1915eM abstractC1915eM;
        final C3332x4 c3332x4H;
        if (this.d.isEmpty()) {
            return;
        }
        B4 b4S = this.b.s();
        while (b4S.hasNext()) {
            InterfaceC2641no interfaceC2641noA = b4S.next().a(this.b);
            while (interfaceC2641noA.hasNext()) {
                AbstractC2412ko next = interfaceC2641noA.next();
                next.getClass();
                if (next instanceof C1721bt) {
                    C1721bt c1721btG0 = next.g0();
                    if (this.d.containsKey(c1721btG0)) {
                        S10 s10 = (S10) this.d.get(c1721btG0);
                        if (s10.i()) {
                            com.android.tools.r8.utils.O3 o3H = com.android.tools.r8.utils.O3.h(s10.l());
                            boolean z = false;
                            while (true) {
                                if (o3H.c()) {
                                    Iterator<S10> it = ((ML) o3H.f()).k0().iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            S10 next2 = it.next();
                                            if (next2.i()) {
                                                o3H.c(next2.l());
                                            } else {
                                                AbstractC2412ko abstractC2412koQ = next2.q();
                                                if (abstractC2412koQ.J1()) {
                                                    AbstractC0677i3 abstractC0677i3C = ((C3733i) this.a.f()).c(this.a.E().c(this.c, abstractC2412koQ.M().getField()));
                                                    if (abstractC0677i3C.B() && !((C3733i) this.a.f()).a(abstractC0677i3C.s())) {
                                                        z = true;
                                                    }
                                                }
                                                interfaceC2641noA.previous();
                                                abstractC1915eMA = c1721btG0.Y0().a(new RH$$ExternalSyntheticLambda1(), c1721btG0.Y0());
                                                if (abstractC1915eMA.r()) {
                                                    AbstractC1915eM abstractC1915eMA2 = abstractC1915eMA.a().b(false).a();
                                                    c1721btG0.a(c1721btG0.Y0().a(abstractC1915eMA, abstractC1915eMA2));
                                                    abstractC1915eM = abstractC1915eMA2;
                                                } else {
                                                    abstractC1915eM = abstractC1915eMA;
                                                }
                                                interfaceC2641noA.a(this.a, this.b, b4S, s10, abstractC1915eM);
                                                if (c1721btG0.h().D()) {
                                                    c3332x4H = c1721btG0.h();
                                                    C3332x4 c3332x4 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4S, new Predicate() { // from class: com.android.tools.r8.internal.RH$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            return RH.a(c3332x4H, (C3332x4) obj);
                                                        }
                                                    });
                                                    if (e && c3332x4 != c3332x4H) {
                                                        throw new AssertionError();
                                                    }
                                                    b4S.next();
                                                    interfaceC2641noA = c3332x4H.a(this.b);
                                                }
                                                AbstractC2412ko next3 = interfaceC2641noA.next();
                                                if (!e && next3 != c1721btG0) {
                                                    throw new AssertionError();
                                                }
                                            }
                                        }
                                    }
                                } else if (!e && !z) {
                                    throw new AssertionError();
                                }
                            }
                        } else {
                            AbstractC2412ko abstractC2412koQ2 = s10.q();
                            if (abstractC2412koQ2.J1()) {
                                AbstractC0677i3 abstractC0677i3C2 = ((C3733i) this.a.f()).c(this.a.E().c(this.c, abstractC2412koQ2.M().getField()));
                                if (!abstractC0677i3C2.B() || ((C3733i) this.a.f()).a(abstractC0677i3C2.s())) {
                                }
                            }
                            interfaceC2641noA.previous();
                            abstractC1915eMA = c1721btG0.Y0().a(new RH$$ExternalSyntheticLambda1(), c1721btG0.Y0());
                            if (abstractC1915eMA.r()) {
                                AbstractC1915eM abstractC1915eMA3 = abstractC1915eMA.a().b(false).a();
                                c1721btG0.a(c1721btG0.Y0().a(abstractC1915eMA, abstractC1915eMA3));
                                abstractC1915eM = abstractC1915eMA3;
                            } else {
                                abstractC1915eM = abstractC1915eMA;
                            }
                            interfaceC2641noA.a(this.a, this.b, b4S, s10, abstractC1915eM);
                            if (c1721btG0.h().D()) {
                                c3332x4H = c1721btG0.h();
                                C3332x4 c3332x5 = (C3332x4) com.android.tools.r8.utils.L1.b((ListIterator) b4S, new Predicate() { // from class: com.android.tools.r8.internal.RH$$ExternalSyntheticLambda2
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        return RH.a(c3332x4H, (C3332x4) obj);
                                    }
                                });
                                if (e) {
                                }
                                b4S.next();
                                interfaceC2641noA = c3332x4H.a(this.b);
                            }
                            AbstractC2412ko next4 = interfaceC2641noA.next();
                            if (!e) {
                                continue;
                            }
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
    }
}
