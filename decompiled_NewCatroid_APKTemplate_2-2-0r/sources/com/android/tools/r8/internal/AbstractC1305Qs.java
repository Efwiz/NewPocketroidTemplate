package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0701l4;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0662g4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.ir.optimize.EnumC3542a0;
import com.android.tools.r8.shaking.C3733i;
import java.util.BitSet;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Qs, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1305Qs extends AbstractC1084Is {
    static final /* synthetic */ boolean j = true;
    private final C0684j2 i;

    public AbstractC1305Qs(C0684j2 c0684j2, S10 s10, List list) {
        super(s10, list);
        this.i = c0684j2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0662g4 c0662g4) {
    }

    @Override // com.android.tools.r8.internal.AbstractC1084Is
    public C0767v2 L2() {
        return this.i.h.e;
    }

    public final S10 Q2() {
        return c(com.android.tools.r8.utils.M.a(Z1()));
    }

    public abstract boolean R2();

    public C0684j2 S2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean Y1() {
        return true;
    }

    public abstract com.android.tools.r8.ir.optimize.W a(com.android.tools.r8.graph.o5 o5Var, EnumC3542a0 enumC3542a0, com.android.tools.r8.ir.optimize.N n, V9 v9, AbstractC3477z20 abstractC3477z20);

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.Y1() && this.i == abstractC2412ko.b0().S2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC1305Qs b0() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public AbstractC2744p f(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return AbstractC2903rB.a(c0702m, this);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x007f  */
    public CN g(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        C0729q2 c0729q2A;
        if (!S2().e.O0()) {
            return null;
        }
        if (!(this instanceof C1248Os) && !f2()) {
            com.android.tools.r8.graph.o5 o5VarA = AbstractC0748t0.a(h(c0702m, o5Var));
            if (o5VarA == null) {
                return null;
            }
            CN cnB = CN.b();
            cnB.add(o5VarA);
            return cnB;
        }
        AbstractC1332Rs abstractC1332RsC0 = c0();
        S10 s10U2 = abstractC1332RsC0.U2();
        s10U2.getClass();
        C0729q2 c0729q2A2 = C0729q2.a(c0702m.c(C3030t00.a(AbstractC1154Lf.a(c0702m, s10U2), abstractC1332RsC0.S2(), c0702m)));
        S10 s10U3 = c0().U2();
        s10U3.getClass();
        C1229Oa c1229OaA = s10U3.a(c0702m, (AbstractC3472z00) null, WH.i());
        if (c1229OaA != null) {
            c0729q2A = C0729q2.a(c0702m.c(c1229OaA.R()));
            if (c0729q2A2 != null && c0729q2A != null && !((C3733i) c0702m.f()).c(c0729q2A.d, c0729q2A2.d)) {
                c0729q2A = null;
            }
        } else {
            c0729q2A = null;
        }
        com.android.tools.r8.graph.I4 i4B = ((C3733i) c0702m.f()).b(this.i, R2());
        AbstractC0701l4 abstractC0701l4A = c0729q2A2 != null ? i4B.a(o5Var.a(), (C0702m<C3733i>) c0702m, c0729q2A2, c0729q2A) : i4B.e(o5Var.a(), c0702m);
        if (abstractC0701l4A.d()) {
            return null;
        }
        final CN cnB2 = CN.b();
        abstractC0701l4A.a(new Consumer() { // from class: com.android.tools.r8.internal.Qs$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1305Qs.a(cnB2, (InterfaceC0669h4) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Qs$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1305Qs.a((C0662g4) obj);
            }
        });
        return cnB2;
    }

    public abstract AbstractC0748t0 h(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var);

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + "; method: " + this.i.r0();
    }

    public static AbstractC1305Qs a(EnumC1953et enumC1953et, C0684j2 c0684j2, S10 s10, List list, boolean z) {
        int iOrdinal = enumC1953et.ordinal();
        if (iOrdinal == 0) {
            return new C1166Ls(c0684j2, s10, list, z);
        }
        if (iOrdinal == 1) {
            return new C1248Os(c0684j2, s10, list);
        }
        if (iOrdinal == 2) {
            return new C1721bt(c0684j2, s10, list, z);
        }
        if (iOrdinal == 3) {
            return new C1798ct(c0684j2, s10, list, z);
        }
        if (iOrdinal != 4) {
            throw new C3405y10("Unexpected invoke type: " + enumC1953et);
        }
        if (j || !z) {
            return new C2115gt(c0684j2, s10, list);
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(CN cn, InterfaceC0669h4 interfaceC0669h4) {
        AbstractC0748t0 abstractC0748t0P = interfaceC0669h4.p();
        abstractC0748t0P.getClass();
        if (abstractC0748t0P instanceof com.android.tools.r8.graph.o5) {
            cn.add(abstractC0748t0P.g0());
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(AbstractC2412ko abstractC2412ko, com.android.tools.r8.ir.regalloc.f fVar, CE ce) {
        if (!super.a(abstractC2412ko, fVar, ce)) {
            return false;
        }
        fVar.b().getClass();
        AbstractC1305Qs abstractC1305QsB0 = abstractC2412ko.b0();
        for (int i = 0; i < this.b.size(); i++) {
            S10 s10 = (S10) this.b.get(i);
            if (s10.w().r() && s10 != abstractC1305QsB0.b.get(i)) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.a(this, interfaceC2641no);
        if (L2().Y0()) {
            return;
        }
        S10 s10 = this.a;
        if (s10 == null) {
            c3340xC.a(L2(), this, interfaceC2641no);
        } else {
            if (!j && !s10.S()) {
                throw new AssertionError();
            }
            c3340xC.b(this, interfaceC2641no);
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return L2();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!j && !d1()) {
            throw new AssertionError();
        }
        AbstractC0748t0 abstractC0748t0H = h(c0702m, o5Var);
        if (abstractC0748t0H != null) {
            return abstractC0748t0H.e().i1().h();
        }
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public boolean a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var, S10 s10) {
        BitSet bitSetQ;
        int iIndexOf;
        AbstractC0748t0 abstractC0748t0H = h(c0702m, o5Var);
        return abstractC0748t0H != null && (bitSetQ = abstractC0748t0H.e().i1().q()) != null && (iIndexOf = this.b.indexOf(s10)) >= 0 && bitSetQ.get(iIndexOf);
    }
}
