package com.android.tools.r8.tracereferences;

import com.android.tools.r8.DiagnosticsHandler;
import com.android.tools.r8.diagnostic.DefinitionContext;
import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.B2;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.O;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.C2620nZ;
import com.android.tools.r8.internal.C2786pZ;
import com.android.tools.r8.references.Reference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class j {
    static final /* synthetic */ boolean i = true;
    private final C0702m a;
    private final C0722p1 b;
    private final TraceReferencesConsumer c;
    private final DiagnosticsHandler d;
    private final Predicate e;
    private final HashSet f = new HashSet();
    private final HashSet g = new HashSet();
    private final HashSet h = new HashSet();

    j(C0702m c0702m, TraceReferencesConsumer traceReferencesConsumer, DiagnosticsHandler diagnosticsHandler, Predicate predicate) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.c = traceReferencesConsumer;
        this.d = diagnosticsHandler;
        this.e = predicate;
    }

    final C0680j a() {
        return (C0680j) this.a.f();
    }

    final C0702m b() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a(C0767v2 c0767v2) {
        return this.e.test(c0767v2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b(C0767v2 c0767v2, DefinitionContext definitionContext) {
        if (c0767v2.M0()) {
            b(c0767v2.b(this.b), definitionContext);
            return;
        }
        if (c0767v2.W0() || c0767v2.Y0()) {
            return;
        }
        if (!i && !c0767v2.O0()) {
            throw new AssertionError();
        }
        a(c0767v2, definitionContext);
    }

    private void a(C0767v2 c0767v2, final DefinitionContext definitionContext) {
        boolean z = i;
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        O oF = this.a.f().f(c0767v2);
        if (oF.T()) {
            oF.b(new Consumer() { // from class: com.android.tools.r8.tracereferences.j$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.b(definitionContext, (AbstractC0727q0) obj);
                }
            });
            return;
        }
        C2620nZ c2620nZ = new C2620nZ(c0767v2.B0(), definitionContext, null);
        if (!z && !c2620nZ.isMissingDefinition()) {
            throw new AssertionError();
        }
        HashSet hashSet = this.f;
        if (c2620nZ.isMissingDefinition()) {
            hashSet.add(c2620nZ.getReference());
        }
        this.c.acceptType(c2620nZ, this.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public void b(DefinitionContext definitionContext, AbstractC0727q0 abstractC0727q0) {
        if (this.e.test(abstractC0727q0.getType())) {
            this.c.acceptType(new C2620nZ(definitionContext, abstractC0727q0), this.d);
            if (abstractC0727q0.getAccessFlags().r()) {
                this.c.acceptPackage(Reference.packageFromString(abstractC0727q0.getType().I0()), this.d);
            }
        }
    }

    private void a(AbstractC0748t0 abstractC0748t0, DefinitionContext definitionContext) {
        boolean z = i;
        if (!z && (abstractC0748t0 instanceof o5)) {
            throw new AssertionError();
        }
        if (!z) {
            if (!this.e.test(abstractC0748t0.t())) {
                throw new AssertionError();
            }
        }
        C2786pZ c2786pZ = new C2786pZ(abstractC0748t0.e(), definitionContext);
        if (this.e.test(abstractC0748t0.t())) {
            this.c.acceptMethod(c2786pZ, this.d);
            if (abstractC0748t0.getAccessFlags().r()) {
                this.c.acceptPackage(Reference.packageFromString(abstractC0748t0.t().I0()), this.d);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(o5 o5Var) {
        I4 i4D;
        DefinitionContext definitionContextA = com.android.tools.r8.diagnostic.internal.d.a(o5Var);
        Iterator<C0767v2> it = o5Var.D().iterator();
        while (it.hasNext()) {
            b(it.next(), definitionContextA);
        }
        b(o5Var.G(), definitionContextA);
        for (C0633d0 c0633d0 : o5Var.e().s0().c) {
            if (c0633d0.t0() == this.b.h5) {
                for (B2 b2 : c0633d0.b.b[0].b.v0().j1()) {
                    b((C0767v2) b2.L0().b, definitionContextA);
                }
            }
        }
        if (o5Var.a().isInterface()) {
            i4D = a().b(o5Var.a(), o5Var.getReference());
        } else {
            i4D = a().d(o5Var.a(), o5Var.getReference());
        }
        AbstractC0748t0 abstractC0748t0B = i4D.b(o5Var.a(), this.a);
        if (abstractC0748t0B == null || (abstractC0748t0B instanceof o5)) {
            return;
        }
        if (this.e.test(abstractC0748t0B.t())) {
            a(abstractC0748t0B, definitionContextA);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(final C0729q2 c0729q2, final C0767v2 c0767v2, final DefinitionContext definitionContext) {
        b(c0767v2, definitionContext);
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.tracereferences.j$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0767v2, c0729q2, definitionContext, (W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(C0767v2 c0767v2, C0729q2 c0729q2, DefinitionContext definitionContext, W0 w0) {
        AbstractC0748t0 abstractC0748t0C = a().a(c0767v2, w0.getReference(), c0767v2 != c0729q2.f).c();
        if (abstractC0748t0C == null || (abstractC0748t0C instanceof o5)) {
            return;
        }
        if (this.e.test(abstractC0748t0C.t())) {
            a(abstractC0748t0C, definitionContext);
        }
    }
}
