package com.android.tools.r8.graph;

import com.android.tools.r8.dex.code.InterfaceC0419c;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C1890e6;
import com.android.tools.r8.internal.C2014fd;
import com.android.tools.r8.internal.C2145hI;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CE;
import com.android.tools.r8.internal.InterfaceC0992Fq;
import com.android.tools.r8.origin.Origin;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class W extends AbstractC0740s {
    static final /* synthetic */ boolean c = true;

    public boolean A0() {
        return this instanceof Z;
    }

    public boolean B0() {
        return false;
    }

    public boolean C0() {
        return this instanceof Z;
    }

    public boolean D0() {
        return this instanceof C2014fd;
    }

    public boolean E0() {
        return false;
    }

    public boolean F0() {
        return this instanceof Z;
    }

    public void G0() {
    }

    public C0753u K() {
        throw new C3405y10(getClass().getCanonicalName() + ".asCfCode()");
    }

    public C0772w0 P() {
        throw new C3405y10(getClass().getCanonicalName() + ".asDexCode()");
    }

    public C1890e6 a(InterfaceC0419c interfaceC0419c) {
        return null;
    }

    public abstract C2409kl a(o5 o5Var, C0702m c0702m, Origin origin, CE.a aVar);

    public abstract String a(W0 w0, com.android.tools.r8.utils.P2 p2);

    public abstract void a(S s, J5 j5);

    public abstract void a(o5 o5Var, J5 j5);

    public void d(Consumer consumer) {
    }

    public boolean k(int i) {
        return x0() <= i;
    }

    public D t0() {
        throw new C3405y10(getClass().getCanonicalName() + ".asCfWritableCode()");
    }

    public abstract String toString();

    public M2 u0() {
        throw new C3405y10(getClass().getCanonicalName() + ".asDexWritableCode()");
    }

    public D5 v() {
        return null;
    }

    public Z3 v0() {
        throw new C3405y10(getClass().getCanonicalName() + ".asLazyCfCode()");
    }

    public abstract int w0();

    public int x0() {
        return Integer.MAX_VALUE;
    }

    public boolean y0() {
        return false;
    }

    public boolean z0() {
        return false;
    }

    public C2409kl a(o5 o5Var, o5 o5Var2, C0702m c0702m, AbstractC1476Wj abstractC1476Wj, C2145hI c2145hI, AbstractC1915eM abstractC1915eM, Origin origin, com.android.tools.r8.graph.proto.k kVar) {
        throw new C3405y10("Unexpected attempt to build IR graph for inlining from: " + getClass().getCanonicalName());
    }

    public AbstractC1476Wj a(C0702m c0702m) {
        return c0702m.n();
    }

    public InterfaceC0992Fq a(C0702m c0702m, W0 w0) {
        throw new C3405y10();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public void a(com.android.tools.r8.dex.V v) {
        throw new C3405y10();
    }

    public W a(C0684j2 c0684j2, W0 w0, C0722p1 c0722p1) {
        return a(c0722p1, c0684j2, w0.getReference(), w0.N0());
    }

    public W a(C0722p1 c0722p1, C0684j2 c0684j2, C0684j2 c0684j3, boolean z) {
        throw new C3405y10();
    }

    public static AbstractC1915eM a(AbstractC1915eM abstractC1915eM, AbstractC1915eM abstractC1915eM2, boolean z) {
        AbstractC1915eM abstractC1915eMH = abstractC1915eM2.h();
        if (!z) {
            AbstractC1915eM abstractC1915eMH2 = abstractC1915eM2.h();
            if (abstractC1915eMH2.e() == 0) {
                while (abstractC1915eM != null && abstractC1915eMH2.g() == abstractC1915eM.g() && abstractC1915eM.e() == 0) {
                    abstractC1915eM = abstractC1915eM.b();
                }
            }
            return abstractC1915eM2.a(abstractC1915eM);
        }
        if (!abstractC1915eMH.q() && !abstractC1915eMH.r()) {
            return abstractC1915eM2.a(abstractC1915eMH, abstractC1915eM);
        }
        if (!c && abstractC1915eM.q()) {
            throw new AssertionError();
        }
        AbstractC1915eM.a aVarA = abstractC1915eMH.a().a(abstractC1915eM.g()).a(abstractC1915eM.e());
        if (abstractC1915eM.r()) {
            aVarA.b(true);
        }
        return abstractC1915eM2.a(abstractC1915eMH, aVarA.a());
    }
}
