package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.ir.optimize.C3550d0;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Rd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1316Rd extends AbstractC2412ko {
    static final /* synthetic */ boolean j = true;
    private final InterfaceC0992Fq h;
    private final InterfaceC0992Fq i;

    public C1316Rd(C1303Qq c1303Qq, C1303Qq c1303Qq2) {
        super(null);
        if (!j && c1303Qq.isEmpty() && c1303Qq2.isEmpty()) {
            throw new AssertionError();
        }
        this.h = c1303Qq;
        this.i = c1303Qq2;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int E2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int F2() {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final int G2() {
        return 18;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final C1316Rd H() {
        return this;
    }

    public final InterfaceC0992Fq J2() {
        return this.h;
    }

    public final InterfaceC0992Fq K2() {
        return this.i;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final Object a(AbstractC1855de abstractC1855de) {
        return null;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final boolean b(AbstractC2412ko abstractC2412ko) {
        abstractC2412ko.getClass();
        if (!(abstractC2412ko instanceof C1316Rd)) {
            return false;
        }
        C1316Rd c1316RdH = abstractC2412ko.H();
        return com.android.tools.r8.graph.X.a(this.h, c1316RdH.h) && com.android.tools.r8.graph.X.a(this.i, c1316RdH.i);
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
    public final boolean l1() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        sb.append("ending: ");
        com.android.tools.r8.utils.Y2.a(sb, this.h.c());
        sb.append(", starting: ");
        com.android.tools.r8.utils.Y2.a(sb, this.i.c());
        return sb.toString();
    }

    public final boolean a(InterfaceC0992Fq interfaceC0992Fq) {
        InterfaceC1912eK it = this.h.c().iterator();
        boolean z = false;
        while (it.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq = (InterfaceC0934Dq) it.next();
            if (!j && interfaceC0992Fq.get(interfaceC0934Dq.a()) != interfaceC0934Dq.getValue()) {
                throw new AssertionError();
            }
            if (interfaceC0992Fq.remove(interfaceC0934Dq.a()) != null) {
                z = true;
            }
        }
        InterfaceC1912eK it2 = this.i.c().iterator();
        while (it2.hasNext()) {
            InterfaceC0934Dq interfaceC0934Dq2 = (InterfaceC0934Dq) it2.next();
            if (!j && interfaceC0992Fq.a(interfaceC0934Dq2.a())) {
                throw new AssertionError();
            }
            com.android.tools.r8.graph.X x = (com.android.tools.r8.graph.X) interfaceC0992Fq.a(interfaceC0934Dq2.a(), (com.android.tools.r8.graph.X) interfaceC0934Dq2.getValue());
            z |= x == null || x != interfaceC0934Dq2.getValue();
        }
        return z;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(C2322jf c2322jf) {
        c2322jf.b(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.L a(C0702m c0702m, C2409kl c2409kl) {
        return com.android.tools.r8.ir.optimize.L.e();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final com.android.tools.r8.ir.optimize.V a(C3550d0 c3550d0, com.android.tools.r8.graph.o5 o5Var) {
        return com.android.tools.r8.ir.optimize.V.d;
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(InterfaceC2641no interfaceC2641no, C3340xC c3340xC) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.internal.AbstractC2412ko
    public final void a(L6 l6) {
        throw new C3405y10();
    }
}
