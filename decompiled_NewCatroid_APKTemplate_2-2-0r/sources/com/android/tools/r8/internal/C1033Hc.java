package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.dex.code.C0430e0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import java.io.UTFDataFormatException;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Hc, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C1033Hc extends AbstractC0824Ac {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    private final C0759u2 i;

    public C1033Hc(S10 s10, C0759u2 c0759u2) {
        super(s10);
        this.i = c0759u2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("ConstString has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1033Hc F() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 255;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 16;
    }

    public final C0759u2 J2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        return (abstractC2412ko instanceof C1033Hc) && abstractC2412ko.F().i == this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko, com.android.tools.r8.internal.InterfaceC3174v
    public final boolean g() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean i1() {
        try {
            this.i.toString();
            return false;
        } catch (RuntimeException e) {
            if (e.getCause() instanceof UTFDataFormatException) {
                return true;
            }
            throw e;
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean j1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean r2() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        return super.toString() + " \"" + this.i + "\"";
    }

    public static C1033Hc a(S10 s10, C1033Hc c1033Hc) {
        if (j || s10 != c1033Hc.b()) {
            return new C1033Hc(s10, c1033Hc.i);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this, new C0430e0(c2322jf.a(W0(), this.a), this.i));
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        if (!(c0702m.Y().f instanceof ClassFileConsumer) && i1()) {
            return com.android.tools.r8.ir.optimize.L.e();
        }
        return com.android.tools.r8.ir.optimize.L.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        c3340xC.b(this, interfaceC2641no);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.a(new C1891e7(this.i), this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        return c0702m.a().X1;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return AbstractC3472z00.b((C0702m<?>) c0702m, WH.b());
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final B1 a(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        if (!i1()) {
            return c0702m.b().a(this.i);
        }
        return C2892r10.F();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C0702m c0702m, C2433l20 c2433l20) {
        boolean z = j;
        if (!z) {
            super.a(c0702m, c2433l20);
        }
        C1229Oa c1229OaB = AbstractC3472z00.b((C0702m<?>) c0702m, WH.b());
        if (!z && !a().equals(c1229OaB)) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.i);
    }
}
