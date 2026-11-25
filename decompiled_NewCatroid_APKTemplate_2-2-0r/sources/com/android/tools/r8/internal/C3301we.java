package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.utils.StringDiagnostic;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.internal.we, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3301we implements InterfaceC2135h8 {
    static final /* synthetic */ boolean f = true;
    private final C0702m a;
    private final C0722p1 b;
    private final C1317Re c;
    private final Set d;
    private final Predicate e;

    public C3301we(C0702m c0702m, Predicate predicate) {
        this.a = c0702m;
        this.b = c0702m.a();
        this.e = predicate;
        this.c = new C1317Re(c0702m);
        if (c0702m.Y().n1.e0) {
            this.d = OU.a();
        } else {
            this.d = null;
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    @Override // com.android.tools.r8.internal.InterfaceC2135h8
    public final void a(Collection collection, AbstractC2591n8 abstractC2591n8, ExecutorService executorService) {
        C1203Nb c1203NbA = this.a.p().a();
        Set setB = abstractC2591n8.b();
        if (!f) {
            for (C0729q2 c0729q2 : this.a.D().c()) {
                if (!f && C3378xe.a(c0729q2.d, this.c, this.a)) {
                    throw new AssertionError();
                }
            }
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q3 = (C0729q2) it.next();
            if (!this.a.b(c0729q3)) {
                ArrayList arrayList = new ArrayList();
                for (com.android.tools.r8.graph.o5 o5Var : c0729q3.k2()) {
                    com.android.tools.r8.graph.W0 w0E = o5Var.e();
                    if ((w0E.L1() || w0E.E0() || w0E.s1() || w0E.D1().a() || !this.a.t.a(w0E.m1(), this.a) || this.a.Y().C1.m().containsKey(o5Var.t()) || (!this.a.q() && o5Var.a().isInterface() && o5Var.e().w1() && (!this.a.Y().M() || this.a.Y().C1.d())) || (!this.a.Y().C1.F() && this.a.Y().C1.d())) ? false : a(o5Var)) {
                        if (this.e.test(o5Var) || setB.contains(o5Var.getReference())) {
                            Set set = this.d;
                            if (set != null) {
                                set.add(o5Var.getReference());
                            }
                            arrayList.add(this.c.b().a(o5Var, abstractC2591n8, c1203NbA).e());
                        }
                    }
                }
                if (!arrayList.isEmpty()) {
                    c0729q3.b((Collection) arrayList);
                }
            }
        }
        if (!f) {
            for (C0729q2 c0729q4 : this.a.D().c()) {
                if (!f && C3378xe.a(c0729q4.d, this.c, this.a)) {
                    throw new AssertionError();
                }
            }
        }
        Set<C0684j2> set2 = this.d;
        C0702m c0702m = this.a;
        if (c0702m.Y().n1.e0) {
            StringBuilder sb = new StringBuilder("Tracked callback desugared API conversions: ");
            for (C0684j2 c0684j2 : set2) {
                sb.append("\n");
                sb.append(c0684j2);
            }
            c0702m.Y().e.warning(new StringDiagnostic(sb.toString()));
            set2.clear();
        }
    }

    private boolean a(com.android.tools.r8.graph.o5 o5Var) {
        C0729q2 c0729q2A = o5Var.a();
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        for (C0767v2 c0767v2 : c0729q2A.g.a) {
            o3E.c(c0767v2);
        }
        C0767v2 c0767v3 = c0729q2A.f;
        if (c0767v3 != this.b.Z1) {
            o3E.c(c0767v3);
        }
        boolean z = false;
        while (o3E.c()) {
            AbstractC0727q0 abstractC0727q0C = this.a.c((C0767v2) o3E.f());
            if (abstractC0727q0C != null) {
                for (C0767v2 c0767v4 : abstractC0727q0C.g.a) {
                    o3E.c(c0767v4);
                }
                C0767v2 c0767v5 = abstractC0727q0C.f;
                if (c0767v5 != this.b.Z1) {
                    o3E.c(c0767v5);
                }
                if (abstractC0727q0C.f0() || this.a.Y().C1.d()) {
                    if (!this.a.Y().C1.F()) {
                        C2215iD c2215iD = this.a.Y().C1;
                        if (c2215iD.m().containsKey(abstractC0727q0C.d) || c2215iD.b(abstractC0727q0C.d)) {
                        }
                    }
                    com.android.tools.r8.graph.W0 w0D = abstractC0727q0C.d(o5Var.getReference());
                    if (w0D != null) {
                        C0702m c0702m = this.a;
                        if (c0702m.t.a(c0702m, abstractC0727q0C.d) || w0D.z1()) {
                            return false;
                        }
                        z = true;
                    } else {
                        continue;
                    }
                }
            }
        }
        return z;
    }
}
