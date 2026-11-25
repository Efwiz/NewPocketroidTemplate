package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import java.util.function.Predicate;
import java.util.function.Supplier;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class KK {
    static final /* synthetic */ boolean a = true;

    public static C2170he a() {
        return C2170he.b();
    }

    public abstract KK a(com.android.tools.r8.graph.u5 u5Var);

    public abstract KK a(AbstractC1476Wj abstractC1476Wj);

    public abstract boolean a(AbstractC0727q0 abstractC0727q0);

    public final boolean a(final C0702m c0702m, final S10 s10) {
        return a(c0702m, new Supplier() { // from class: com.android.tools.r8.internal.KK$$ExternalSyntheticLambda1
            @Override // java.util.function.Supplier
            public final Object get() {
                return KK.a(s10, c0702m);
            }
        }, s10.w());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AbstractC1154Lf a(S10 s10, C0702m c0702m) {
        s10.getClass();
        return AbstractC1154Lf.a(c0702m, s10);
    }

    public final boolean a(final C0702m c0702m, Supplier supplier, AbstractC3472z00 abstractC3472z00) {
        if (!abstractC3472z00.w()) {
            return true;
        }
        C1229Oa c1229OaB = abstractC3472z00.b();
        if (c1229OaB.R() != c0702m.a().Z1 || c1229OaB.N().f() || c1229OaB.S().a(new Predicate() { // from class: com.android.tools.r8.internal.KK$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return this.f$0.a(c0702m, (C0767v2) obj);
            }
        })) {
            return true;
        }
        AbstractC1154Lf abstractC1154Lf = (AbstractC1154Lf) supplier.get();
        if (abstractC1154Lf.l()) {
            return true;
        }
        if (abstractC1154Lf.m()) {
            return false;
        }
        AbstractC3472z00 abstractC3472z00A = abstractC1154Lf.a(abstractC3472z00);
        if (abstractC3472z00A.r()) {
            return abstractC3472z00A.b(abstractC3472z00, (C0702m<?>) c0702m);
        }
        if (!abstractC3472z00A.w()) {
            if (a) {
                return false;
            }
            throw new AssertionError();
        }
        C1229Oa c1229OaB2 = abstractC3472z00A.b();
        if (c1229OaB2.R() != c0702m.a().Z1) {
            return c1229OaB2.b(abstractC3472z00, (C0702m<?>) c0702m);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean a(C0702m c0702m, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0C = c0702m.c(c0767v2);
        return abstractC0727q0C != null && a(abstractC0727q0C);
    }
}
