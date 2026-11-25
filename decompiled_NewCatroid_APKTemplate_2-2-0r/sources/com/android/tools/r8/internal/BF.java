package com.android.tools.r8.internal;

import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class BF implements D4 {
    static final /* synthetic */ boolean f = true;
    private S10 a = null;
    private int b = 0;
    private AbstractC2412ko c = null;
    private final VL d;
    private final GL e;

    public BF() {
        VL vl = new VL(new Predicate() { // from class: com.android.tools.r8.internal.BF$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a((AbstractC2412ko) obj);
            }
        });
        this.d = vl;
        this.e = GL.a(vl, new G20(new Predicate() { // from class: com.android.tools.r8.internal.BF$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.b((AbstractC2412ko) obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean b(AbstractC2412ko abstractC2412ko) {
        if (abstractC2412ko.m1() || (abstractC2412ko instanceof AF) || ((abstractC2412ko instanceof SW) && abstractC2412ko.b() == this.a)) {
            return false;
        }
        int iB = FL.b(abstractC2412ko) + this.b;
        this.b = iB;
        if (iB > 0) {
            return false;
        }
        int iA = iB - FL.a(abstractC2412ko);
        this.b = iA;
        if (iA != 0 || (abstractC2412ko instanceof C1343Sd)) {
            return true;
        }
        this.c = abstractC2412ko;
        return true;
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(AbstractC2412ko abstractC2412ko) {
        if (!FL.a(new BF$$ExternalSyntheticLambda0()).test(abstractC2412ko)) {
            return false;
        }
        C3044tC c3044tCK0 = abstractC2412ko.k0();
        if (c3044tCK0.T0().Z() > 20) {
            return false;
        }
        this.a = c3044tCK0.J2();
        return true;
    }

    /* JADX WARN: Code duplicated, block: B:46:0x00e4 A[LOOP:0: B:44:0x00e0->B:46:0x00e4, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:49:0x0106 A[LOOP:1: B:48:0x0104->B:49:0x0106, LOOP_END] */
    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        C3044tC c3044tCK0;
        AbstractC2412ko abstractC2412koPrevious;
        int i;
        int i2;
        int i3;
        this.b = 0;
        this.c = null;
        VD vdA = this.e.a(c3486zB);
        if (vdA != null && this.c != null) {
            c3486zB.previous();
            C3044tC c3044tCK1 = c3486zB.next().k0();
            if (c3486zB.hasNext()) {
                AbstractC1915eM abstractC1915eMY0 = c3044tCK1.Y0();
                AbstractC2412ko next = c3486zB.next();
                if (abstractC1915eMY0 == next.Y0() && next.B1() && next.a() == AbstractC3472z00.k() && next.E().N2() >= -128 && next.E().N2() <= 127 && c3486zB.hasNext()) {
                    AbstractC2412ko next2 = c3486zB.next();
                    if (abstractC1915eMY0 == next2.Y0() && next2.k1() && c3486zB.hasNext()) {
                        AbstractC2412ko next3 = c3486zB.next();
                        FL.a(c3486zB, 4);
                        if (abstractC1915eMY0 != next3.Y0() || !(next3 instanceof SW)) {
                        }
                    } else {
                        FL.a(c3486zB, 3);
                    }
                } else {
                    FL.a(c3486zB, 2);
                }
                c3044tCK0 = this.d.a(vdA).k0();
                if (f) {
                }
                C2617nW c2617nW = (C2617nW) c3044tCK0.b();
                C2617nW c2617nWC = c2617nW.c(c2617nW.j0());
                c2617nW.g(c2617nWC);
                c3044tCK0.J2().e(c3044tCK0);
                c3486zB.s();
                abstractC2412koPrevious = c3486zB.previous();
                i = 1;
                while (abstractC2412koPrevious != this.c) {
                    abstractC2412koPrevious = c3486zB.previous();
                    i++;
                }
                C3044tC c3044tC = new C3044tC(c2617nWC, c3044tCK0.J2());
                c3044tC.b(this.c.Y0());
                c3486zB.add(c3044tC);
                i2 = i + 1;
                for (i3 = 0; i3 < i2; i3++) {
                    c3486zB.next();
                }
            } else {
                c3044tCK0 = this.d.a(vdA).k0();
                if (f && c3044tCK0.J2().A()) {
                    throw new AssertionError();
                }
                C2617nW c2617nW2 = (C2617nW) c3044tCK0.b();
                C2617nW c2617nWC2 = c2617nW2.c(c2617nW2.j0());
                c2617nW2.g(c2617nWC2);
                c3044tCK0.J2().e(c3044tCK0);
                c3486zB.s();
                abstractC2412koPrevious = c3486zB.previous();
                i = 1;
                while (abstractC2412koPrevious != this.c) {
                    abstractC2412koPrevious = c3486zB.previous();
                    i++;
                }
                C3044tC c3044tC2 = new C3044tC(c2617nWC2, c3044tCK0.J2());
                c3044tC2.b(this.c.Y0());
                c3486zB.add(c3044tC2);
                i2 = i + 1;
                while (i3 < i2) {
                    c3486zB.next();
                }
            }
        }
        return false;
    }
}
