package com.android.tools.r8.internal;

import com.android.tools.r8.errors.CheckEnumUnboxedDiagnostic;
import com.android.tools.r8.graph.AbstractC0674i0;
import com.android.tools.r8.graph.AbstractC0681j0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.ir.optimize.C3541a;
import com.android.tools.r8.ir.optimize.C3546c0;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.InterfaceC3885d3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import com.android.tools.r8.utils.InterfaceC3949p3;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class UM extends C3012sl {
    static final /* synthetic */ boolean P = true;
    private final com.android.tools.r8.utils.y3 O;

    public UM(C0702m c0702m, com.android.tools.r8.utils.y3 y3Var) {
        super(c0702m);
        this.O = y3Var;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ AbstractC0681j0 a(AbstractC0681j0 abstractC0681j0, AbstractC0681j0 abstractC0681j1) {
        return abstractC0681j0;
    }

    private void c() {
        this.a.f().d().forEach(new Consumer() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.b((C0729q2) obj);
            }
        });
    }

    public final void b(C0702m c0702m, ExecutorService executorService) {
        this.O.a("Create IR");
        try {
            final com.android.tools.r8.graph.N2 n2B = a(c0702m.i0(), executorService).b();
            c0702m.h0().d(((C3733i) c0702m.f()).a(new Function() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return UM.a(n2B, (AbstractC0681j0) obj);
                }
            }));
        } finally {
            this.O.c();
        }
    }

    private AbstractC0681j0 a(final C0702m c0702m, final ExecutorService executorService) throws Throwable {
        boolean z = P;
        if (!z) {
            D7 d7 = this.d;
            d7.getClass();
            if (!(d7 instanceof C1399Uf)) {
                throw new AssertionError();
            }
        }
        a(executorService);
        b("Primary optimization pass");
        AbstractC1476Wj abstractC1476WjE = c0702m.E();
        c0702m.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda9
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(executorService, (C3476z2) obj);
            }
        });
        this.x.a(abstractC1476WjE);
        this.b.a(abstractC1476WjE);
        C3521zh c3521zh = this.e;
        if (c3521zh != null) {
            c3521zh.a().a();
        }
        final com.android.tools.r8.ir.optimize.info.o oVar = this.F;
        final C2844qM c2844qM = new C2844qM(abstractC1476WjE);
        this.O.a("Build primary method processor");
        VE veA = VE.a(c0702m);
        final TM tmA = TM.a(c0702m.i0(), veA, executorService, this.O);
        this.O.c();
        this.O.a("IR conversion phase 1");
        if (!z && c0702m.E() != abstractC1476WjE) {
            throw new AssertionError();
        }
        tmA.a(new QM() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda10
            @Override // com.android.tools.r8.internal.QM
            public final com.android.tools.r8.utils.y3 a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                return this.f$0.a(oVar, tmA, o5Var, c1230Ob);
            }
        }, new SM() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda11
            @Override // com.android.tools.r8.internal.SM
            public final void a(CN cn) {
                this.f$0.a(cn);
            }
        }, new RM() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda12
            @Override // com.android.tools.r8.internal.RM
            public final void a(CN cn, ExecutorService executorService2) throws Throwable {
                this.f$0.a(cn, executorService2);
            }
        }, this.O, executorService);
        a(c2844qM, executorService);
        veA.b(c0702m);
        if (!z && c0702m.E() != abstractC1476WjE) {
            throw new AssertionError();
        }
        this.O.c();
        ((C3733i) c0702m.f()).o().v().a();
        if (!z) {
            oVar.b();
        }
        c0702m.c0();
        c0702m.m();
        if (c0702m.D().d()) {
            c0702m.d(((C3733i) c0702m.f()).b(c0702m.D().a(((C3733i) c0702m.f()).b())));
        }
        b("Post optimization pass");
        this.x.b();
        this.b.b();
        c0702m.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a(c2844qM, executorService, (C3476z2) obj);
            }
        });
        com.android.tools.r8.shaking.G1 g1 = this.f;
        if (g1 != null) {
            g1.a();
        }
        if (!this.j.W0) {
            new C3365xZ(c0702m.i0(), c2844qM).a(executorService, oVar, this.O);
        }
        this.b.b();
        this.x.a(c0702m, this, c2844qM, executorService, oVar);
        C3226vg c3226vgE0 = c0702m.e0();
        c3226vgE0.getClass();
        ArrayList arrayList = new ArrayList();
        for (C0729q2 c0729q2 : ((C3733i) c0702m.f()).d()) {
            if (c0729q2.B1()) {
                com.android.tools.r8.shaking.Y0 y0A = c0702m.a(c0729q2);
                c0702m.Y();
                if (y0A.m() && !c3226vgE0.b(c0729q2.getType())) {
                    arrayList.add(c0729q2);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            c0702m.Y().e.a(CheckEnumUnboxedDiagnostic.builder().a(arrayList).a());
            throw null;
        }
        AbstractC1476Wj abstractC1476WjE2 = c0702m.E();
        this.b.b();
        this.O.a("IR conversion phase 2");
        final VE veA2 = VE.a(c0702m);
        final C2914rM c2914rM = (C2914rM) this.O.a("Build post method processor", new InterfaceC3949p3() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3949p3
            public final Object get() {
                return this.f$0.a(c2844qM, c0702m, veA2, executorService);
            }
        });
        if (c2914rM != null) {
            boolean z2 = P;
            if (!z2 && this.j.W0) {
                throw new AssertionError();
            }
            if (!z2 && c0702m.E() != abstractC1476WjE2) {
                throw new AssertionError();
            }
            this.O.a("Process code");
            c2914rM.a(new QM() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda3
                @Override // com.android.tools.r8.internal.QM
                public final com.android.tools.r8.utils.y3 a(com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
                    return this.f$0.a(oVar, c2914rM, o5Var, c1230Ob);
                }
            }, oVar, executorService, this.O);
            this.O.c();
            com.android.tools.r8.utils.y3 y3Var = this.O;
            Objects.requireNonNull(oVar);
            y3Var.a("Update visible optimization info", new InterfaceC3885d3() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.InterfaceC3885d3
                public final void a() {
                    oVar.c();
                }
            });
            veA2.b(c0702m);
            if (!z2 && c0702m.E() != abstractC1476WjE2) {
                throw new AssertionError();
            }
        }
        this.O.c();
        this.x.c();
        c0702m.m();
        if (c0702m.D().d()) {
            c0702m.d(((C3733i) c0702m.f()).b(c0702m.D().a(((C3733i) c0702m.f()).b())));
        }
        AbstractC0674i0 abstractC0674i0C = ((C3733i) c0702m.f()).b().c();
        abstractC0674i0C.a(this.H);
        com.android.tools.r8.ir.optimize.G0 g0 = this.v;
        if (g0 != null) {
            a(g0.a(), executorService);
        }
        C1536Yn c1536Yn = this.y;
        if (c1536Yn != null) {
            a(c1536Yn.a(), executorService);
        }
        C3541a c3541a = this.l;
        if (c3541a != null) {
            a(c3541a.a(), executorService);
        }
        oVar.c();
        this.b.a(this, oVar, executorService, this.O);
        c();
        com.android.tools.r8.naming.C c = this.q;
        if (c != null) {
            c.a(executorService);
        }
        if (!P) {
            oVar.b();
        }
        return abstractC0674i0C.b();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0729q2 c0729q2) {
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((com.android.tools.r8.graph.W0) obj).T1();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ExecutorService executorService, C3476z2 c3476z2) {
        c3476z2.a(executorService, this.O);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.y3 a(com.android.tools.r8.ir.optimize.info.o oVar, TM tm, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        return b(o5Var, oVar, tm, c1230Ob);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2844qM c2844qM, ExecutorService executorService, C3476z2 c3476z2) {
        c3476z2.a(this, c2844qM, executorService, this.O);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C2914rM a(C2844qM c2844qM, C0702m c0702m, VE ve, ExecutorService executorService) {
        return c2844qM.a(c0702m, ve, executorService, this.O);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.utils.y3 a(com.android.tools.r8.ir.optimize.info.o oVar, C2914rM c2914rM, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob) {
        return b(o5Var, oVar, c2914rM, c1230Ob);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CN cn) {
        this.I = Collections.synchronizedList(new ArrayList());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(CN cn, ExecutorService executorService) throws Throwable {
        this.F.a(this.a.f().o());
        this.F.c();
        this.e.a().a(cn, this.F);
        this.a.a(new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.UM$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                ((C3476z2) obj).a();
            }
        });
        if (this.a.Y().x1().c()) {
            this.a.Z().g.a();
        }
        this.x.d();
        if (!P) {
            this.F.b();
        }
        this.I.forEach(new UM$$ExternalSyntheticLambda6());
        this.I = null;
        if (this.J.isEmpty()) {
            return;
        }
        this.a.a(com.android.tools.r8.graph.u5.a().f(this.J).a(this.a.f().b()).a(), executorService);
        this.J.clear();
    }

    private void a(C2844qM c2844qM, ExecutorService executorService) {
        C3546c0 c3546c0 = this.p;
        if (c3546c0 != null) {
            c3546c0.a(c2844qM, executorService, this.O);
        }
        this.a.Y().n1.a(new Fg$$ExternalSyntheticLambda6(c2844qM));
    }
}
