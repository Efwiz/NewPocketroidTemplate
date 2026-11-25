package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0639e;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3880d;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.BitSet;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.t9, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3041t9 {
    private static final com.android.tools.r8.ir.optimize.info.r d = com.android.tools.r8.ir.optimize.info.r.b();
    static final /* synthetic */ boolean e = true;
    private final C0702m a;
    private final C3012sl b;
    private final JV c = JV.f();

    public C3041t9(C0702m c0702m, C3012sl c3012sl) {
        if (!e && c0702m.Y().W0) {
            throw new AssertionError();
        }
        this.a = c0702m;
        this.b = c3012sl;
    }

    public final void a(com.android.tools.r8.graph.o5 o5Var, B1 b1, final TE te) {
        if (this.b.b()) {
            if (!e && !te.c()) {
                throw new AssertionError();
            }
            if (o5Var.getReference().I0().N0() && b1.z()) {
                synchronized (this) {
                    if (this.c.isEmpty()) {
                        this.b.a(new InterfaceC3880d() { // from class: com.android.tools.r8.internal.t9$$ExternalSyntheticLambda0
                            @Override // com.android.tools.r8.utils.InterfaceC3880d
                            public final void a() throws Throwable {
                                this.f$0.a(te);
                            }
                        });
                    }
                    this.c.add(o5Var);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(TE te) throws Throwable {
        if (!e && this.c.isEmpty()) {
            throw new AssertionError();
        }
        CN cnB = CN.b();
        for (com.android.tools.r8.graph.o5 o5Var : this.c) {
            if (!cnB.b(o5Var)) {
                a(o5Var, te);
            }
        }
    }

    private void a(final com.android.tools.r8.graph.o5 o5Var, TE te) throws Throwable {
        com.android.tools.r8.graph.I4.c<?> cVarO;
        if (o5Var.e().H1()) {
            com.android.tools.r8.ir.optimize.info.h hVarI1 = o5Var.e().i1();
            if (hVarI1.A()) {
                return;
            }
            B1 b1H = hVarI1.h();
            if (b1H.z()) {
                C0729q2 c0729q2A = C0729q2.a(this.a.c(o5Var.a().f));
                final com.android.tools.r8.graph.o5 o5VarS = (c0729q2A == null || (cVarO = ((C3733i) this.a.f()).g(c0729q2A, o5Var.getReference()).o()) == null) ? null : cVarO.s();
                if (o5VarS == null || !o5VarS.e().H1()) {
                    return;
                }
                com.android.tools.r8.ir.optimize.info.h hVarI2 = o5VarS.e().i1();
                if (hVarI2.A()) {
                    return;
                }
                B1 b1H2 = hVarI2.h();
                if (b1H2.z()) {
                    boolean z = e;
                    if (!z && ((C3733i) this.a.f()).c((InterfaceC0694k5) o5Var)) {
                        throw new AssertionError();
                    }
                    if (!z && ((C3733i) this.a.f()).c((InterfaceC0694k5) o5VarS)) {
                        throw new AssertionError();
                    }
                    if (((C3733i) this.a.f()).z().a(o5Var.a())) {
                        return;
                    }
                    com.android.tools.r8.graph.W0 w0E = o5VarS.e();
                    if (b1H2 != b1H) {
                        if (!AbstractC0639e.a(o5Var.a(), o5VarS, this.a).d()) {
                            return;
                        }
                        o5VarS.a(w0E.a(o5Var.t(), b1H2.D(), this.a.Y()), this.a);
                        com.android.tools.r8.graph.W wA1 = w0E.a1();
                        C0702m c0702m = this.a;
                        Origin origin = o5VarS.getOrigin();
                        wA1.getClass();
                        C2409kl c2409klA = wA1.a(o5VarS, c0702m, origin, new CE.a(c0702m.Y()));
                        C3012sl c3012sl = this.b;
                        com.android.tools.r8.ir.optimize.info.r rVar = d;
                        c3012sl.a(c2409klA, rVar);
                        com.android.tools.r8.ir.optimize.info.r.a(o5VarS, new Consumer() { // from class: com.android.tools.r8.internal.t9$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                ((BitSet) obj).clear(0);
                            }
                        });
                        rVar.e(o5VarS);
                        rVar.g(o5VarS);
                    }
                    this.a.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.t9$$ExternalSyntheticLambda2
                        @Override // com.android.tools.r8.utils.InterfaceC3905h3
                        public final void accept(Object obj) {
                            ((C3476z2) obj).a(o5Var, o5VarS);
                        }
                    });
                    d.getClass();
                    com.android.tools.r8.ir.optimize.info.r.k(o5VarS);
                    te.a().c(o5VarS);
                    o5Var.a().e(o5Var.getReference());
                    this.b.c(o5Var);
                }
            }
        }
    }
}
