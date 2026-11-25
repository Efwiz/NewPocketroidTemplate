package com.android.tools.r8.tracereferences;

import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.graph.AbstractC0677i3;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0735r0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.C0800x2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.J5;
import com.android.tools.r8.graph.O;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1476Wj;
import com.android.tools.r8.internal.AbstractC2964s2;
import com.android.tools.r8.internal.C2620nZ;
import com.android.tools.r8.internal.C2711oZ;
import com.android.tools.r8.internal.C2786pZ;
import com.android.tools.r8.internal.EnumC1953et;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.KE;
import com.android.tools.r8.references.Reference;
import com.android.tools.r8.utils.K;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class i extends J5 {
    static final /* synthetic */ boolean g = true;
    private final DefinitionContext e;
    final /* synthetic */ j f;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public i(j jVar, o5 o5Var) {
        super(jVar.b(), o5Var);
        this.f = jVar;
        this.e = com.android.tools.r8.diagnostic.internal.d.a(o5Var);
    }

    private void i(final Y0 y0) {
        this.f.b(y0.B0(), this.e);
        this.f.b(y0.getType(), this.e);
        AbstractC0677i3 abstractC0677i3C = this.f.a().c(y0);
        abstractC0677i3C.a(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda8
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0, (AbstractC0677i3) obj);
            }
        });
        if (abstractC0677i3C.y()) {
            return;
        }
        C2711oZ c2711oZ = new C2711oZ(y0.E0(), this.e, null);
        j jVar = this.f;
        if (!j.i) {
            jVar.getClass();
            if (!c2711oZ.isMissingDefinition()) {
                throw new AssertionError();
            }
        }
        HashSet hashSet = jVar.g;
        if (c2711oZ.isMissingDefinition()) {
            hashSet.add(c2711oZ.getReference());
        }
        j jVar2 = this.f;
        jVar2.c.acceptField(c2711oZ, jVar2.d);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.a.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        C0684j2 reference = o5Var.getReference();
        EnumC1953et enumC1953et = EnumC1953et.c;
        KE keA = abstractC1476WjE.a(c0684j2, reference, enumC1953et, (AbstractC1476Wj) null);
        if (!g) {
            EnumC1953et enumC1953etF = keA.f();
            enumC1953etF.getClass();
            if (enumC1953etF != enumC1953et) {
                throw new AssertionError();
            }
        }
        final C0684j2 c0684j3 = (C0684j2) keA.b();
        if (((o5) d()).a().g2()) {
            a(c0684j3, this.f.a().g(c0684j3), new i$$ExternalSyntheticLambda0());
            return;
        }
        final K k = new K();
        C0702m c0702m = this.a;
        c0702m.f().f(c0684j3.B0()).b(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda7
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j3, k, (AbstractC0727q0) obj);
            }
        });
        if (k.d()) {
            b(c0684j3, (W0) null);
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.a.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        C0684j2 reference = o5Var.getReference();
        EnumC1953et enumC1953et = EnumC1953et.d;
        KE keA = abstractC1476WjE.a(c0684j2, reference, enumC1953et, (AbstractC1476Wj) null);
        if (!g) {
            EnumC1953et enumC1953etF = keA.f();
            enumC1953etF.getClass();
            if (enumC1953etF != enumC1953et) {
                throw new AssertionError();
            }
        }
        a(keA);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void c(Y0 y0) {
        i((Y0) this.f.a.E().d((AbstractC1476Wj) null, y0).b());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.a.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        KE keA = abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.e, (AbstractC1476Wj) null);
        if (!g && !keA.f().d()) {
            throw new AssertionError();
        }
        C0684j2 c0684j3 = (C0684j2) keA.b();
        a(c0684j3, this.f.a().g(c0684j3), new i$$ExternalSyntheticLambda0());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void f(C0767v2 c0767v2) {
        j jVar = this.f;
        jVar.b(AbstractC2964s2.a(jVar.a.E(), c0767v2), this.e);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.a.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        KE keA = abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.f, (AbstractC1476Wj) null);
        if (!g && !keA.f().e()) {
            throw new AssertionError();
        }
        a(c0684j2, this.f.a().g((C0684j2) keA.b()), new Function() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a((I4.c) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.J5
    public final void h(C0684j2 c0684j2) {
        AbstractC1476Wj abstractC1476WjE = this.f.a.E();
        o5 o5Var = (o5) d();
        abstractC1476WjE.getClass();
        KE keA = abstractC1476WjE.a(c0684j2, o5Var.getReference(), EnumC1953et.g, (AbstractC1476Wj) null);
        if (!g && !keA.f().f()) {
            throw new AssertionError();
        }
        a(keA);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void b(C0767v2 c0767v2) {
        i(this.a.N().a(AbstractC2964s2.a(this.f.a.E(), c0767v2)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void g(C0767v2 c0767v2) {
        this.f.b(c0767v2, this.e);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void e(Y0 y0) {
        i((Y0) this.f.a.E().d((AbstractC1476Wj) null, y0).b());
    }

    @Override // com.android.tools.r8.graph.J5
    public final void g(Y0 y0) {
        i((Y0) this.f.a.E().d((AbstractC1476Wj) null, y0).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0684j2 c0684j2, K k, AbstractC0727q0 abstractC0727q0) {
        AbstractC0748t0 abstractC0748t0A = c0684j2.a(abstractC0727q0);
        if (abstractC0748t0A != null) {
            b(c0684j2, abstractC0748t0A.e());
            k.f();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ AbstractC0748t0 a(I4.c cVar) {
        return cVar.c(((o5) d()).a(), this.a, this.f.a());
    }

    private void a(KE ke) {
        I4 i4C;
        C0684j2 c0684j2 = (C0684j2) ke.b();
        if (c0684j2.B0().M0()) {
            if (!g && !ke.f().f()) {
                throw new AssertionError();
            }
            this.f.b(c0684j2.B0(), this.e);
            return;
        }
        if (!g) {
            EnumC1953et enumC1953etF = ke.f();
            enumC1953etF.getClass();
            if (enumC1953etF != EnumC1953et.d && !ke.f().f()) {
                throw new AssertionError();
            }
        }
        EnumC1953et enumC1953etF2 = ke.f();
        enumC1953etF2.getClass();
        if (enumC1953etF2 == EnumC1953et.d) {
            i4C = this.f.a().e(c0684j2);
        } else {
            i4C = this.f.a().c(c0684j2);
        }
        a(c0684j2, i4C, new i$$ExternalSyntheticLambda0());
    }

    private void a(final C0684j2 c0684j2, I4 i4, final Function function) {
        final K k = new K();
        i4.a(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0684j2, k, function, (I4) obj);
            }
        });
        if (k.d()) {
            i4.a(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(c0684j2, (I4) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0684j2 c0684j2, K k, Function function, I4 i4) {
        if (i4.h()) {
            i4.k().a(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda5
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.g((C0767v2) obj);
                }
            }, new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda6
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(c0684j2, (W0) obj);
                }
            });
            return;
        }
        k.f();
        AbstractC0748t0 abstractC0748t0 = (AbstractC0748t0) function.apply(i4.o());
        b(c0684j2, abstractC0748t0 == null ? null : abstractC0748t0.e());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0684j2 c0684j2, I4 i4) {
        if (!g && !i4.h()) {
            throw new AssertionError();
        }
        if (i4.k().z()) {
            return;
        }
        b(c0684j2, (W0) null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(C0684j2 c0684j2, W0 w0) {
        if (!g && w0 != null && !w0.getReference().b(c0684j2) && !AbstractC0727q0.a(w0, this.f.b)) {
            throw new AssertionError();
        }
        this.f.b(c0684j2.B0(), this.e);
        j jVar = this.f;
        C0800x2 c0800x2G0 = c0684j2.G0();
        DefinitionContext definitionContext = this.e;
        jVar.getClass();
        Iterator<C0767v2> it = c0800x2G0.iterator();
        while (it.hasNext()) {
            jVar.b(it.next(), definitionContext);
        }
        this.f.b(c0684j2.I0(), this.e);
        if (w0 != null) {
            if (this.f.a(w0.J0())) {
                if (w0.J0() != c0684j2.B0()) {
                    this.f.b(w0.J0(), this.e);
                }
                C2786pZ c2786pZ = new C2786pZ(w0, this.e);
                j jVar2 = this.f;
                jVar2.c.acceptMethod(c2786pZ, jVar2.d);
                if (w0.getAccessFlags().r()) {
                    this.f.c.acceptPackage(Reference.packageFromString(w0.J0().I0()), this.f.d);
                    return;
                }
                return;
            }
            return;
        }
        C2786pZ c2786pZ2 = new C2786pZ(c0684j2.E0(), this.e, null);
        j jVar3 = this.f;
        if (!j.i) {
            jVar3.getClass();
            if (!c2786pZ2.isMissingDefinition()) {
                throw new AssertionError();
            }
        }
        HashSet hashSet = jVar3.h;
        if (c2786pZ2.isMissingDefinition()) {
            hashSet.add(c2786pZ2.getReference());
        }
        j jVar4 = this.f;
        jVar4.c.acceptMethod(c2786pZ2, jVar4.d);
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(Y0 y0) {
        i((Y0) this.f.a.E().d((AbstractC1476Wj) null, y0).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Y0 y0, AbstractC0677i3 abstractC0677i3) {
        if (abstractC0677i3.B()) {
            AbstractC0735r0 abstractC0735r0C = abstractC0677i3.c();
            if (this.f.a(abstractC0735r0C.t())) {
                if (abstractC0735r0C.t() != y0.B0()) {
                    this.f.b(this.e, abstractC0735r0C.a());
                }
                C2711oZ c2711oZ = new C2711oZ(abstractC0735r0C, this.e);
                j jVar = this.f;
                jVar.c.acceptField(c2711oZ, jVar.d);
                if (abstractC0735r0C.getAccessFlags().r()) {
                    this.f.c.acceptPackage(Reference.packageFromString(abstractC0735r0C.t().I0()), this.f.d);
                }
            }
        }
    }

    @Override // com.android.tools.r8.graph.J5
    public final void a(C0721p0 c0721p0) {
        super.a(c0721p0);
        j jVar = this.f;
        final JA jaB = JA.b(c0721p0, jVar.a, jVar.a(), (o5) d());
        if (jaB != null) {
            for (C0767v2 c0767v2 : jaB.g) {
                O oF = this.a.f().f(c0767v2);
                if (oF.T()) {
                    oF.b(new Consumer() { // from class: com.android.tools.r8.tracereferences.i$$ExternalSyntheticLambda3
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            this.f$0.a(jaB, (AbstractC0727q0) obj);
                        }
                    });
                } else {
                    C2620nZ c2620nZ = new C2620nZ(c0767v2, this.e);
                    j jVar2 = this.f;
                    if (!j.i) {
                        jVar2.getClass();
                        if (!c2620nZ.isMissingDefinition()) {
                            throw new AssertionError();
                        }
                    }
                    HashSet hashSet = jVar2.f;
                    if (c2620nZ.isMissingDefinition()) {
                        hashSet.add(c2620nZ.getReference());
                    }
                    j jVar3 = this.f;
                    jVar3.c.acceptType(c2620nZ, jVar3.d);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(JA ja, AbstractC0727q0 abstractC0727q0) {
        W0 w0C = abstractC0727q0.c(ja.b());
        if (w0C != null) {
            b(w0C.getReference());
        }
        Iterator it = ja.h.iterator();
        while (it.hasNext()) {
            W0 w0A = abstractC0727q0.a((C0737r2) it.next(), ja.c);
            if (w0A != null) {
                b(w0A.getReference());
            }
        }
    }
}
