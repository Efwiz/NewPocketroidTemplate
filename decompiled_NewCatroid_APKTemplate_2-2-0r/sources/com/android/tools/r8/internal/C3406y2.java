package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.ir.optimize.C3550d0;
import java.util.Set;

/* JADX INFO: renamed from: com.android.tools.r8.internal.y2, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C3406y2 extends AbstractC2412ko {
    static final /* synthetic */ boolean j = true;
    public static final /* synthetic */ int k = 0;
    private final int h;
    private final boolean i;

    public C3406y2(S10 s10, int i, boolean z) {
        super(s10);
        this.h = i;
        this.i = z;
    }

    private int b(boolean z) {
        if (!j && z) {
            InterfaceC2488lo interfaceC2488loR = h().R();
            int i = 0;
            while (interfaceC2488loR.hasNext()) {
                AbstractC2412ko next = interfaceC2488loR.next();
                boolean z2 = j;
                if (!z2 && !next.m1()) {
                    throw new AssertionError();
                }
                if (next != this) {
                    i++;
                } else if (!z2 && i != this.h) {
                    throw new AssertionError();
                }
            }
            throw new AssertionError();
        }
        return this.h;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        if (j) {
            return 0;
        }
        throw new AssertionError("Argument has no register arguments.");
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        return 65535;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 5;
    }

    public final int J2() {
        return b(true);
    }

    public final int K2() {
        return b(false);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean c1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean d(C0702m c0702m, com.android.tools.r8.graph.o5 o5Var) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean m1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C3406y2 q() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        return com.android.tools.r8.ir.optimize.L.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.a(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C0767v2 a(C0702m c0702m, Q00 q00) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        l6.getClass();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final AbstractC3472z00 a(C0702m c0702m) {
        return this.a.w();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean a(Set set) {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(NB nb) {
        nb.a(this.h);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        return abstractC2412ko.m1();
    }
}
