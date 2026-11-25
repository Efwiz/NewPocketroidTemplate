package com.android.tools.r8.internal;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class VW implements D4 {
    private SW a;
    private int b = 0;
    private final VL c;
    private final G20 d;
    private final VL e;
    private final GL f;

    public VW() {
        VL vl = new VL(new Predicate() { // from class: com.android.tools.r8.internal.VW$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((AbstractC2412ko) obj);
            }
        });
        this.c = vl;
        G20 g20 = new G20(new Predicate() { // from class: com.android.tools.r8.internal.VW$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((AbstractC2412ko) obj);
            }
        });
        this.d = g20;
        VL vl2 = new VL(new Predicate() { // from class: com.android.tools.r8.internal.VW$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.c((AbstractC2412ko) obj);
            }
        });
        this.e = vl2;
        this.f = GL.b(vl, g20, vl2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3044tC c3044tC, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == c3044tC;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(AbstractC2412ko abstractC2412ko) {
        if (this.b == 1) {
            abstractC2412ko.getClass();
            if ((abstractC2412ko instanceof C3044tC) && abstractC2412ko.k0().J2() == this.a.b()) {
                return false;
            }
        }
        int iA = this.b - FL.a(abstractC2412ko);
        this.b = iA;
        if (iA < 0) {
            this.a = null;
            return false;
        }
        this.b = FL.b(abstractC2412ko) + iA;
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean c(AbstractC2412ko abstractC2412ko) {
        if (this.a == null) {
            return false;
        }
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C3044tC) && !abstractC2412ko.k0().J2().A() && abstractC2412ko.k0().J2() == this.a.b();
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof SW) || abstractC2412ko.H0().J2().w().M() || abstractC2412ko.b().A() || abstractC2412ko.H0().b().Z() != 1) {
            return false;
        }
        this.a = abstractC2412ko.H0();
        this.b = 0;
        return true;
    }

    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        C2617nW c2617nW;
        VD vdA = this.f.a(c3486zB);
        if (vdA != null && this.a != null) {
            SW swH0 = this.c.a(vdA).H0();
            List listA = this.d.a(vdA);
            final C3044tC c3044tCK0 = this.e.a(vdA).k0();
            AbstractC2412ko abstractC2412ko = (AbstractC2412ko) listA.get(listA.size() - 1);
            if (abstractC2412ko.b() instanceof C2617nW) {
                c2617nW = (C2617nW) abstractC2412ko.b();
            } else if (abstractC2412ko.b() instanceof C2708oW) {
                C2617nW[] c2617nWArrJ0 = ((C2708oW) abstractC2412ko.b()).j0();
                c2617nW = c2617nWArrJ0[c2617nWArrJ0.length - 1];
            } else {
                c2617nW = null;
            }
            if (c2617nW != null && !c2617nW.w().M()) {
                C2617nW c2617nW2 = (C2617nW) swH0.J2();
                swH0.b().e(c3044tCK0);
                c3044tCK0.b().g(c2617nW2);
                c3486zB.s();
                c3486zB.a(new Predicate() { // from class: com.android.tools.r8.internal.VW$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return VW.a(c3044tCK0, (AbstractC2412ko) obj);
                    }
                });
                C2617nW c2617nWC = c2617nW.c(c2617nW2.j0());
                C2617nW c2617nWC2 = c2617nW2.c(c2617nWC.j0() + 1);
                c2617nW2.g(c2617nWC2);
                c2617nW.g(c2617nWC);
                c3486zB.a(new HY(c2617nWC, c2617nWC2, c2617nW2, c2617nW), (Set<S10>) null);
                FL.a(c3486zB, 2);
                return true;
            }
        }
        return false;
    }
}
