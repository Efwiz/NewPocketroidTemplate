package com.android.tools.r8.internal;

import java.util.Set;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Kf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1127Kf implements D4 {
    private final VL a;
    private final VL b;
    private final VL c;
    private final GL d;

    public C1127Kf() {
        VL vl = new VL(new Predicate() { // from class: com.android.tools.r8.internal.Kf$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1127Kf.a((AbstractC2412ko) obj);
            }
        });
        this.a = vl;
        VL vl2 = new VL(new Predicate() { // from class: com.android.tools.r8.internal.Kf$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1127Kf.b((AbstractC2412ko) obj);
            }
        });
        this.b = vl2;
        VL vl3 = new VL(new Predicate() { // from class: com.android.tools.r8.internal.Kf$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return C1127Kf.c((AbstractC2412ko) obj);
            }
        });
        this.c = vl3;
        this.d = GL.a(vl, vl2, vl3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1099Jf) && !((S10) abstractC2412ko.h1().get(0)).w().M();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean c(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1099Jf) && !((S10) abstractC2412ko.h1().get(0)).w().M();
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1099Jf) && !((S10) abstractC2412ko.h1().get(0)).w().M();
    }

    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        VD vdA = this.d.a(c3486zB);
        if (vdA == null) {
            return false;
        }
        C1099Jf c1099JfL = this.c.a(vdA).L();
        C1099Jf c1099JfL2 = this.b.a(vdA).L();
        C1099Jf c1099JfL3 = this.a.a(vdA).L();
        int iJ0 = c1099JfL.L2().j0();
        C2617nW c2617nWC = c1099JfL.J2().c(iJ0);
        C2617nW c2617nWC2 = c1099JfL2.J2().c(iJ0 + 1);
        C2617nW c2617nWC3 = c1099JfL3.J2().c(iJ0 + 2);
        C2617nW c2617nWC4 = c1099JfL3.K2().c(iJ0 + 3);
        c1099JfL2.L2().e(c1099JfL2);
        c1099JfL3.L2().e(c1099JfL3);
        c1099JfL.J2().g(c2617nWC);
        c1099JfL2.J2().g(c2617nWC2);
        c1099JfL3.J2().g(c2617nWC3);
        c1099JfL3.K2().g(c2617nWC4);
        C1068If c1068If = new C1068If(c2617nWC, c2617nWC2, c2617nWC3, c2617nWC4, c1099JfL.J2(), c1099JfL.K2());
        c3486zB.s();
        c3486zB.previous();
        c3486zB.a(c1068If, (Set<S10>) null);
        c3486zB.next();
        return true;
    }
}
