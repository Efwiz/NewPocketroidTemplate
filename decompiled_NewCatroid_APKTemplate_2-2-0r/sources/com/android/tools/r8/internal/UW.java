package com.android.tools.r8.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UW implements D4 {
    static final /* synthetic */ boolean e = true;
    private final VL a;
    private final VL b;
    private final G20 c;
    private final GL d;

    public UW() {
        VL vl = new VL(FL.a(new TW$$ExternalSyntheticLambda0()));
        this.a = vl;
        VL vl2 = new VL(new BF$$ExternalSyntheticLambda0());
        this.b = vl2;
        G20 g20 = new G20(new Predicate() { // from class: com.android.tools.r8.internal.UW$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UW.a((AbstractC2412ko) obj);
            }
        });
        this.c = g20;
        this.d = GL.b(vl, vl2, g20);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C3044tC c3044tC, AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko == c3044tC;
    }

    @Override // com.android.tools.r8.internal.D4
    public final void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean a(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1099Jf) || (abstractC2412ko instanceof C1068If);
    }

    @Override // com.android.tools.r8.internal.D4
    public final boolean a(C3486zB c3486zB) {
        C2617nW c2617nW;
        VD vdA = this.d.a(c3486zB);
        if (vdA == null) {
            return false;
        }
        SW swH0 = this.a.a(vdA).H0();
        final C3044tC c3044tCK0 = this.b.a(vdA).k0();
        if (c3044tCK0.J2() != swH0.b() || swH0.b().Z() <= 1) {
            return false;
        }
        List listA = this.c.a(vdA);
        C2617nW c2617nW2 = (C2617nW) swH0.J2();
        if (listA.isEmpty()) {
            c2617nW = (C2617nW) c3044tCK0.b();
        } else {
            if (!e) {
                AbstractC2412ko abstractC2412ko = (AbstractC2412ko) listA.get(0);
                abstractC2412ko.getClass();
                if (!(abstractC2412ko instanceof C1099Jf) || ((ArrayList) ((AbstractC2412ko) listA.get(0)).h1()).get(0) != c3044tCK0.b()) {
                    throw new AssertionError();
                }
            }
            ((AbstractC2412ko) listA.get(0)).a(0, c2617nW2);
            FL.a(c3486zB, listA);
            C2617nW[] c2617nWArrJ0 = ((C2708oW) ((AbstractC2412ko) listA.get(listA.size() - 1)).b()).j0();
            c2617nW2 = c2617nWArrJ0[c2617nWArrJ0.length - 1];
            c2617nW = c2617nW2;
        }
        C2617nW c2617nWC = c2617nW.c(c2617nW.j0());
        C2617nW c2617nWC2 = c2617nW.c(c2617nW.j0() + 1);
        c2617nW.g(c2617nWC);
        if (!e && c3044tCK0.b().S()) {
            throw new AssertionError();
        }
        C1099Jf c1099Jf = new C1099Jf(c2617nWC, c2617nWC2, c2617nW2);
        c1099Jf.b(swH0.Y0());
        c3486zB.add(c1099Jf);
        swH0.a(0, c2617nWC2);
        c3486zB.a(new Predicate() { // from class: com.android.tools.r8.internal.UW$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return UW.a(c3044tCK0, (AbstractC2412ko) obj);
            }
        });
        c3486zB.s();
        FL.a(c3486zB, listA.size() + 1);
        return true;
    }
}
