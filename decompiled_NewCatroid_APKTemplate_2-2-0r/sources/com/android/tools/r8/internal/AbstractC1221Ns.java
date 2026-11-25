package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0701l4;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0662g4;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0669h4;
import com.android.tools.r8.shaking.C3733i;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Ns, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC1221Ns extends com.android.tools.r8.graph.J5 {
    static final /* synthetic */ boolean j = true;
    protected final C0702m e;
    protected final AbstractC2839qH f;
    protected final Function g;
    protected final Map h;
    protected final Predicate i;

    public AbstractC1221Ns(C0702m c0702m, AbstractC2839qH abstractC2839qH, Function function, ConcurrentHashMap concurrentHashMap, Predicate predicate) {
        super(c0702m, abstractC2839qH.b());
        this.e = c0702m;
        this.f = abstractC2839qH;
        this.g = function;
        this.h = concurrentHashMap;
        this.i = predicate;
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void b(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void c(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void e(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void f(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.J5
    public void g(com.android.tools.r8.graph.Y0 y0) {
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        a(EnumC1953et.g, c0684j2);
    }

    protected final void a(boolean z, com.android.tools.r8.graph.o5 o5Var) {
        if (this.i.test(o5Var) && !o5Var.e().s1() && !o5Var.e().f.Z() && this.e.a(o5Var).b((com.android.tools.r8.shaking.K0) this.e.Y())) {
            ((AbstractC2839qH) this.g.apply(o5Var)).a(this.f, z);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        a(EnumC1953et.d, c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        a(EnumC1953et.e, c0684j2);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        a(EnumC1953et.f, c0684j2);
    }

    private void a(EnumC1953et enumC1953et, C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5VarB = this.f.b();
        KE keA = this.e.E().a(c0684j2, o5VarB.getReference(), enumC1953et, c());
        C0684j2 c0684j3 = (C0684j2) keA.b();
        EnumC1953et enumC1953etF = keA.f();
        EnumC1953et enumC1953et2 = EnumC1953et.d;
        if (enumC1953etF != enumC1953et2 && enumC1953etF != EnumC1953et.g) {
            C3733i c3733i = (C3733i) this.e.f();
            C0702m c0702m = this.e;
            com.android.tools.r8.graph.o5 o5VarA = c3733i.a(c0702m, enumC1953etF, c0684j3, o5VarB, c0702m);
            if (o5VarA != null) {
                a(o5VarA, o5VarB);
                return;
            }
            return;
        }
        AbstractC0748t0 abstractC0748t0C = ((C3733i) this.e.f()).b(c0684j3, enumC1953etF == enumC1953et2).c();
        if (abstractC0748t0C != null) {
            a(enumC1953etF, abstractC0748t0C, o5VarB);
        }
    }

    protected void a(com.android.tools.r8.graph.o5 o5Var, com.android.tools.r8.graph.o5 o5Var2) {
        if (!j && o5Var2.e().t1() && o5Var.e() == o5Var2.e()) {
            throw new AssertionError();
        }
        a(false, o5Var);
    }

    protected void a(EnumC1953et enumC1953et, AbstractC0748t0 abstractC0748t0, final com.android.tools.r8.graph.o5 o5Var) {
        C0684j2 reference = abstractC0748t0.getReference();
        AbstractC0727q0 abstractC0727q0A = abstractC0748t0.a();
        this.e.Y().n1.getClass();
        if (abstractC0727q0A.f0()) {
            return;
        }
        final boolean z = enumC1953et == EnumC1953et.d;
        CN cn = (CN) this.h.computeIfAbsent(reference, new Function() { // from class: com.android.tools.r8.internal.Ns$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(z, o5Var, (C0684j2) obj);
            }
        });
        if (cn != null) {
            boolean z2 = cn.size() >= this.e.Y().M;
            Iterator it = cn.iterator();
            while (it.hasNext()) {
                a(z2, (com.android.tools.r8.graph.o5) it.next());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ CN a(boolean z, com.android.tools.r8.graph.o5 o5Var, C0684j2 c0684j2) {
        com.android.tools.r8.graph.I4 i4B = ((C3733i) this.e.f()).b(c0684j2, z);
        if (!i4B.y()) {
            return null;
        }
        AbstractC0701l4 abstractC0701l4E = i4B.e(o5Var.a(), this.e);
        if (!abstractC0701l4E.e()) {
            return null;
        }
        final CN cnB = CN.b();
        abstractC0701l4E.a().a(new Consumer() { // from class: com.android.tools.r8.internal.Ns$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1221Ns.a(cnB, (InterfaceC0669h4) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.internal.Ns$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC1221Ns.a(cnB, (C0662g4) obj);
            }
        });
        return cnB;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(CN cn, InterfaceC0669h4 interfaceC0669h4) {
        AbstractC0748t0 abstractC0748t0P = interfaceC0669h4.p();
        abstractC0748t0P.getClass();
        if (abstractC0748t0P instanceof com.android.tools.r8.graph.o5) {
            cn.add(abstractC0748t0P.g0());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(CN cn, C0662g4 c0662g4) {
        AbstractC0748t0 abstractC0748t0B = c0662g4.b();
        abstractC0748t0B.getClass();
        if (abstractC0748t0B instanceof com.android.tools.r8.graph.o5) {
            cn.add(abstractC0748t0B.g0());
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public void a(C0721p0 c0721p0) {
        a(c0721p0.f, com.android.tools.r8.graph.I5.b);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        a(EnumC1953et.c, c0684j2);
    }
}
