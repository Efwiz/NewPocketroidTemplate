package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.internal.C1513Xr;
import com.android.tools.r8.internal.JA;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.synthesis.C3842a;
import com.android.tools.r8.utils.C3979v2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.graph.j, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public class C0680j extends C0664h {
    private static final C0673i i = new C0673i();
    static final /* synthetic */ boolean j = true;
    private final C1121Ka g;
    private final com.android.tools.r8.shaking.U1 h;

    private C0680j(C0664h c0664h) {
        super(i, c0664h);
        this.g = C1121Ka.a();
        this.h = com.android.tools.r8.shaking.U1.b();
    }

    @Override // com.android.tools.r8.graph.C0664h
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public C0680j a(com.android.tools.r8.shaking.N1 n1) {
        boolean z = j;
        if (!z && getClass() != C0680j.class) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        return new C0680j(g().a(b()), this.g, n1, this.h);
    }

    public boolean c(C0767v2 c0767v2, C0767v2 c0767v3) {
        boolean z = j;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v3 == null) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (z || c0767v3.O0()) {
            return c0767v2 == c0767v3 || b(c0767v2, c0767v3);
        }
        throw new AssertionError();
    }

    public I4 d(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return a(c0684j2.B0(), c0684j2);
    }

    public I4 e(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return b(abstractC0727q0, c0684j2.H0(), c0684j2.C0());
    }

    public I4 f(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return b(c0684j2.B0(), c0684j2);
    }

    /* JADX WARN: Code duplicated, block: B:81:0x0138  */
    public C1513Xr g(C0767v2 c0767v2) {
        boolean z;
        if (!j && !c0767v2.O0()) {
            throw new AssertionError();
        }
        AbstractC0727q0 abstractC0727q0C = c(c0767v2);
        if (abstractC0727q0C == null) {
            return C1513Xr.b();
        }
        if (abstractC0727q0C.f == a().Z1 && abstractC0727q0C.g.isEmpty()) {
            return abstractC0727q0C.isInterface() ? C1513Xr.c(c0767v2) : C1513Xr.b();
        }
        C1513Xr.a aVarA = C1513Xr.a();
        if (abstractC0727q0C.isInterface()) {
            aVarA.a(c0767v2, true);
        }
        Set setC = OU.c();
        ArrayDeque arrayDeque = new ArrayDeque();
        while (abstractC0727q0C != null) {
            Iterator<C0767v2> it = abstractC0727q0C.g.iterator();
            while (it.hasNext()) {
                C0767v2 next = it.next();
                if (!setC.contains(next)) {
                    C0767v2 type = abstractC0727q0C.getType();
                    com.android.tools.r8.utils.A1 a1L = l();
                    boolean z2 = (a1L.F() && type == a1L.j0().u2 && next == a1L.j0().t2) ? false : true;
                    aVarA.a(next, z2);
                    if (z2) {
                        setC.add(next);
                    }
                    AbstractC0727q0 abstractC0727q0C2 = c(next);
                    if (abstractC0727q0C2 != null && !abstractC0727q0C2.g.isEmpty()) {
                        arrayDeque.add(new C3979v2(abstractC0727q0C2, Boolean.valueOf(z2)));
                    }
                }
            }
            C0767v2 c0767v3 = abstractC0727q0C.f;
            if (c0767v3 == null || c0767v3 == l().j0().Z1) {
                break;
            }
            abstractC0727q0C = c(abstractC0727q0C.f);
        }
        while (!arrayDeque.isEmpty()) {
            C3979v2 c3979v2 = (C3979v2) arrayDeque.poll();
            AbstractC0727q0 abstractC0727q0 = (AbstractC0727q0) c3979v2.a();
            if (!j && abstractC0727q0.g.isEmpty()) {
                throw new AssertionError();
            }
            for (C0767v2 c0767v4 : abstractC0727q0.g) {
                if (!setC.contains(c0767v4)) {
                    if (((Boolean) c3979v2.b()).booleanValue()) {
                        C0767v2 type2 = abstractC0727q0.getType();
                        com.android.tools.r8.utils.A1 a1L2 = l();
                        if (a1L2.F() && type2 == a1L2.j0().u2 && c0767v4 == a1L2.j0().t2) {
                            z = false;
                        } else {
                            z = true;
                        }
                    } else {
                        z = false;
                    }
                    aVarA.a(c0767v4, z);
                    if (z) {
                        setC.add(c0767v4);
                    }
                    AbstractC0727q0 abstractC0727q0C3 = c(c0767v4);
                    if (abstractC0727q0C3 != null && !abstractC0727q0C3.g.isEmpty()) {
                        arrayDeque.add(new C3979v2(abstractC0727q0C3, Boolean.valueOf(z)));
                    }
                }
            }
        }
        return aVarA.a();
    }

    @Override // com.android.tools.r8.graph.C0664h
    public final boolean h() {
        if (j) {
            return true;
        }
        c();
        return true;
    }

    @Override // com.android.tools.r8.graph.C0664h
    public final C0680j n() {
        if (!j) {
            c();
        }
        return this;
    }

    public final C1121Ka p() {
        return this.g;
    }

    public final com.android.tools.r8.shaking.U1 q() {
        return this.h;
    }

    public I4 h(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).a(c0684j2);
    }

    public static C0680j a(AbstractC0681j0 abstractC0681j0, C1121Ka c1121Ka, com.android.tools.r8.shaking.N1 n1, com.android.tools.r8.synthesis.y yVar) {
        return new C0680j(com.android.tools.r8.synthesis.D.a(abstractC0681j0, yVar), c1121Ka, n1, com.android.tools.r8.shaking.U1.b());
    }

    public final I4 d(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return a(abstractC0727q0, c0684j2.H0(), c0684j2.C0());
    }

    public final I4 e(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return b(c0684j2, c0684j2.B0());
    }

    public I4 f(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return c(abstractC0727q0, c0684j2.H0(), c0684j2.C0());
    }

    public final boolean c(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1) {
        if (abstractC0727q1.isInterface()) {
            return c(abstractC0727q0.getType(), abstractC0727q1.getType());
        }
        boolean z = j;
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        if (z || !abstractC0727q1.isInterface()) {
            return !abstractC0727q0.isInterface() ? !(abstractC0727q0 == abstractC0727q1 || b(abstractC0727q0, abstractC0727q1)) : abstractC0727q1.getType() != a().Z1;
        }
        throw new AssertionError();
    }

    public static C0680j a(C0664h c0664h) {
        if (j || !c0664h.h()) {
            return new C0680j(c0664h);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.graph.C0664h
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public C0680j a(u5 u5Var, ExecutorService executorService) {
        boolean z = j;
        if (!z && getClass() != C0680j.class) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        if (z || u5Var.d() == b()) {
            return u5Var.k() ? this : new C0680j(g().a(u5Var), this.g.a(u5Var), f().a(u5Var), this.h);
        }
        throw new AssertionError();
    }

    public final C0680j a(C3842a c3842a) {
        return new C0680j(c3842a, this.g, f(), this.h);
    }

    private ArrayList c(C0729q2 c0729q2, C0767v2 c0767v2) {
        boolean z = j;
        if (!z && !c(c0729q2.d, c0767v2)) {
            throw new AssertionError();
        }
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!z && c0767v2 != a().Z1 && c(c0767v2) != null && c(c0767v2).isInterface()) {
            throw new AssertionError();
        }
        ArrayList arrayList = new ArrayList();
        AbstractC0727q0 abstractC0727q0C = c0729q2;
        while (abstractC0727q0C != null) {
            if (abstractC0727q0C.e0()) {
                arrayList.add(abstractC0727q0C.a0());
            }
            if (abstractC0727q0C.d == c0767v2) {
                return arrayList;
            }
            abstractC0727q0C = c(abstractC0727q0C.f);
        }
        return arrayList;
    }

    public final C0680j a(Function function) {
        if (!j) {
            c();
        }
        return new C0680j(g().a((AbstractC0681j0) function.apply(b())), this.g, f(), this.h);
    }

    public boolean b(C0767v2 c0767v2, final C0767v2 c0767v3) {
        AbstractC0727q0 abstractC0727q0C;
        boolean z = j;
        if (!z && c0767v2 == null) {
            throw new AssertionError();
        }
        if (!z && c0767v3 == null) {
            throw new AssertionError();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (!z && !c0767v3.O0()) {
            throw new AssertionError();
        }
        if (c0767v2 == c0767v3 || c0767v2 == a().Z1) {
            return false;
        }
        if (c0767v3 == a().Z1) {
            return true;
        }
        if (c0767v2.O0() && c0767v3.O0() && (abstractC0727q0C = c(c0767v2)) != null) {
            return a(abstractC0727q0C, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.graph.j$$ExternalSyntheticLambda4
                @Override // com.android.tools.r8.utils.G3
                public final Object a(Object obj, Object obj2, Object obj3) {
                    return C0680j.a(c0767v3, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
                }
            }).f();
        }
        return false;
    }

    public final com.android.tools.r8.utils.D3 a(AbstractC0727q0 abstractC0727q0, com.android.tools.r8.utils.G3 g3) {
        AbstractC0727q0 abstractC0727q0C = abstractC0727q0;
        int length = 0;
        while (abstractC0727q0C != null) {
            length += abstractC0727q0C.g.a.length;
            C0767v2 c0767v2 = abstractC0727q0C.f;
            if (c0767v2 == null) {
                break;
            }
            com.android.tools.r8.utils.D3 d3 = (com.android.tools.r8.utils.D3) g3.a(c0767v2, abstractC0727q0C, Boolean.FALSE);
            if (d3.f()) {
                return d3;
            }
            abstractC0727q0C = c(abstractC0727q0C.f);
        }
        if (length == 0) {
            return com.android.tools.r8.utils.D3.e();
        }
        Set setC = OU.c();
        ArrayDeque arrayDeque = new ArrayDeque();
        while (abstractC0727q0 != null) {
            for (C0767v2 c0767v3 : abstractC0727q0.g.a) {
                if (setC.add(c0767v3)) {
                    com.android.tools.r8.utils.D3 d4 = (com.android.tools.r8.utils.D3) g3.a(c0767v3, abstractC0727q0, Boolean.TRUE);
                    if (d4.f()) {
                        return d4;
                    }
                    arrayDeque.addLast(c0767v3);
                }
            }
            C0767v2 c0767v4 = abstractC0727q0.f;
            if (c0767v4 == null) {
                break;
            }
            abstractC0727q0 = c(c0767v4);
        }
        while (!arrayDeque.isEmpty()) {
            AbstractC0727q0 abstractC0727q0C2 = c((C0767v2) arrayDeque.removeFirst());
            if (abstractC0727q0C2 != null) {
                for (C0767v2 c0767v5 : abstractC0727q0C2.g.a) {
                    if (setC.add(c0767v5)) {
                        com.android.tools.r8.utils.D3 d5 = (com.android.tools.r8.utils.D3) g3.a(c0767v5, abstractC0727q0C2, Boolean.TRUE);
                        if (d5.f()) {
                            return d5;
                        }
                        arrayDeque.addLast(c0767v5);
                    }
                }
            }
        }
        return com.android.tools.r8.utils.D3.e();
    }

    final I4 c(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).c(abstractC0727q0, c0684j2);
    }

    public final I4 c(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).b(abstractC0727q0, c0737r2, c0759u2);
    }

    public final I4 c(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return a(c0684j2, c0684j2.B0());
    }

    public AbstractC0677i3 c(Y0 y0) {
        boolean z = j;
        if (!z) {
            c();
        }
        C0767v2 c0767v2 = y0.e;
        if (!z) {
            c();
        }
        return new Z2(this).a(c0767v2, y0);
    }

    public final boolean b(AbstractC0727q0 abstractC0727q0, final AbstractC0727q0 abstractC0727q1) {
        com.android.tools.r8.utils.D3 d3E;
        boolean z = j;
        if (!z && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q1 == null) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q0.isInterface()) {
            throw new AssertionError();
        }
        if (!z && abstractC0727q1.isInterface()) {
            throw new AssertionError();
        }
        if (abstractC0727q0 == abstractC0727q1 || abstractC0727q0.getType() == a().Z1) {
            return false;
        }
        if (abstractC0727q1.getType() == a().Z1) {
            return true;
        }
        com.android.tools.r8.utils.G3 g3 = new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.graph.j$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return C0680j.a(abstractC0727q1, (C0767v2) obj, (AbstractC0727q0) obj2, (AbstractC0727q0) obj3);
            }
        };
        while (true) {
            if (abstractC0727q0 != null && abstractC0727q0.n1() != null) {
                AbstractC0727q0 abstractC0727q0C = c(abstractC0727q0.n1());
                d3E = (com.android.tools.r8.utils.D3) g3.a(abstractC0727q0.n1(), abstractC0727q0C, abstractC0727q0);
                if (d3E.f()) {
                    break;
                }
                abstractC0727q0 = abstractC0727q0C;
            } else {
                d3E = com.android.tools.r8.utils.D3.e();
                break;
            }
        }
        return d3E.f() && ((Boolean) d3E.a().i()).booleanValue();
    }

    public final AbstractC0748t0 b(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).b(abstractC0727q0, c0684j2);
    }

    public AbstractC0735r0 b(Y0 y0) {
        C0767v2 c0767v2B0 = y0.B0();
        boolean z = j;
        if (!z) {
            c();
        }
        if (!z && !c0767v2B0.O0()) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        AbstractC0735r0 abstractC0735r0C = new Z2(this).a(c0767v2B0, y0).c();
        if (abstractC0735r0C == null || !abstractC0735r0C.getAccessFlags().o()) {
            return null;
        }
        return abstractC0735r0C;
    }

    public final void a(C0729q2 c0729q2, final com.android.tools.r8.utils.F3 f3) {
        a(c0729q2, new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.graph.j$$ExternalSyntheticLambda3
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return C0680j.a(f3, (C0767v2) obj, (AbstractC0727q0) obj2, (Boolean) obj3);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(com.android.tools.r8.utils.F3 f3, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        f3.a(c0767v2, abstractC0727q0, bool);
        return com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(C0767v2 c0767v2, C0767v2 c0767v3, AbstractC0727q0 abstractC0727q0, Boolean bool) {
        return c0767v3 == c0767v2 ? com.android.tools.r8.utils.D3.d() : com.android.tools.r8.utils.D3.e();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.utils.D3 a(AbstractC0727q0 abstractC0727q0, C0767v2 c0767v2, AbstractC0727q0 abstractC0727q1, AbstractC0727q0 abstractC0727q2) {
        if (c0767v2 == abstractC0727q0.getType()) {
            return com.android.tools.r8.utils.D3.a(Boolean.TRUE);
        }
        if (abstractC0727q1 == null) {
            return com.android.tools.r8.utils.D3.a(Boolean.FALSE);
        }
        if (abstractC0727q0.e0() && !abstractC0727q1.e0()) {
            return com.android.tools.r8.utils.D3.a(Boolean.FALSE);
        }
        return com.android.tools.r8.utils.D3.e();
    }

    public W0 b(C0684j2 c0684j2, o5 o5Var, C0702m<? extends C0680j> c0702m) {
        C0729q2 c0729q2A = o5Var.a();
        C0680j c0680j = (C0680j) c0702m.f();
        if (!j) {
            c();
        }
        return h(c0684j2).b(c0729q2A, (C0702m) c0702m, c0680j);
    }

    public I4 g(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        if (abstractC0727q0.isInterface()) {
            return f(abstractC0727q0, c0684j2);
        }
        return e(abstractC0727q0, c0684j2);
    }

    public final W0 b(C0684j2 c0684j2, o5 o5Var, C0702m c0702m, C0680j c0680j) {
        C0729q2 c0729q2A = o5Var.a();
        if (!j) {
            c();
        }
        return h(c0684j2).b(c0729q2A, c0702m, c0680j);
    }

    public I4 g(C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return D4.a(a(), new j$$ExternalSyntheticLambda0(this)).a(c0684j2);
    }

    public final boolean a(C0767v2 c0767v2, C0767v2 c0767v3) {
        boolean z = j;
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (z || c0767v3.O0()) {
            return !(c(c0767v2, c0767v3) || c(c0767v3, c0767v2));
        }
        throw new AssertionError();
    }

    protected C0680j(C3842a c3842a, C1121Ka c1121Ka, com.android.tools.r8.shaking.N1 n1, com.android.tools.r8.shaking.U1 u1) {
        super(c3842a, n1);
        this.g = c1121Ka;
        this.h = u1;
    }

    public I4 b(C0684j2 c0684j2, boolean z) {
        if (!j) {
            c();
        }
        return b(c0684j2.B0(), c0684j2, z);
    }

    public final List a(C0729q2 c0729q2, C0729q2 c0729q3) {
        boolean z = j;
        if (!z && !c(c0729q2.d, c0729q3.d)) {
            throw new AssertionError();
        }
        if (!z && c0729q2.isInterface()) {
            throw new AssertionError();
        }
        if (!c0729q3.isInterface()) {
            return c(c0729q2, c0729q3.d);
        }
        ArrayList arrayListC = c(c0729q2, a().Z1);
        com.android.tools.r8.utils.O3 o3E = com.android.tools.r8.utils.O3.e();
        for (int size = arrayListC.size() - 1; size >= 0; size--) {
            C0729q2 c0729q4 = (C0729q2) arrayListC.get(size);
            C0767v2 c0767v2 = c0729q3.d;
            o3E.a(c0729q4.H0());
            while (o3E.c()) {
                C0767v2 c0767v3 = (C0767v2) o3E.f();
                if (c0767v3 != c0767v2) {
                    AbstractC0727q0 abstractC0727q0C = c(c0767v3);
                    if (abstractC0727q0C != null) {
                        o3E.a(abstractC0727q0C.H0());
                    }
                } else {
                    return arrayListC.subList(0, size + 1);
                }
            }
        }
        return Collections.emptyList();
    }

    public final I4 b(AbstractC0727q0 abstractC0727q0, AbstractC0717o2 abstractC0717o2) {
        boolean z = j;
        if (!z) {
            c();
        }
        if (abstractC0727q0.isInterface()) {
            if (!z) {
                c();
            }
            return c(abstractC0727q0, abstractC0717o2.b(), abstractC0717o2.a());
        }
        return a(abstractC0727q0, abstractC0717o2);
    }

    public final I4 b(C0767v2 c0767v2, C0684j2 c0684j2, boolean z) {
        if (!j) {
            c();
        }
        if (z) {
            return b(c0767v2, c0684j2);
        }
        return a(c0767v2, c0684j2);
    }

    public final I4 b(C0767v2 c0767v2, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).a(c0767v2, c0737r2, c0759u2);
    }

    public final I4 b(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).a(abstractC0727q0, c0737r2, c0759u2);
    }

    public I4 b(C0767v2 c0767v2, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).b(c0767v2, c0684j2.H0(), c0684j2.C0());
    }

    public final I4 b(C0684j2 c0684j2, C0767v2 c0767v2) {
        if (!j) {
            c();
        }
        return D4.a(a(), new j$$ExternalSyntheticLambda0(this)).b(c0767v2, c0684j2.H0(), c0684j2.C0());
    }

    public final I4 b(C0729q2 c0729q2, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return a(c0729q2, c0684j2.H0(), c0684j2.C0());
    }

    /* JADX WARN: Code duplicated, block: B:27:0x006a  */
    /* JADX WARN: Code duplicated, block: B:28:0x006c  */
    public final boolean a(W0 w0, C0767v2 c0767v2) {
        boolean zEquals;
        AbstractC0727q0 abstractC0727q0C = c(c0767v2);
        if (abstractC0727q0C == null) {
            return false;
        }
        if (!j && !abstractC0727q0C.isInterface()) {
            throw new AssertionError();
        }
        for (W0 w1 : abstractC0727q0C.P1()) {
            if (w1.getReference().b(w0.getReference())) {
                C0725p4 c0725p4X0 = w1.getAccessFlags();
                if (c0725p4X0.g() != w0.getAccessFlags().g()) {
                    zEquals = false;
                } else if (c0725p4X0.m()) {
                    zEquals = true;
                } else if (c0725p4X0.j()) {
                    if (w1.J0() == w0.J0()) {
                        zEquals = true;
                    } else {
                        zEquals = false;
                    }
                } else {
                    if (!W0.v && !c0725p4X0.r()) {
                        throw new AssertionError();
                    }
                    zEquals = w1.J0().I0().equals(w0.J0().I0());
                }
                if (zEquals) {
                    return true;
                }
            }
        }
        for (C0767v2 c0767v3 : abstractC0727q0C.g.a) {
            if (a(w0, c0767v3)) {
                return true;
            }
        }
        return false;
    }

    public final ArrayList a(AbstractC0727q0 abstractC0727q0, C0684j2 c0684j2) {
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).a(abstractC0727q0, c0684j2);
    }

    final I4 a(JA ja, C0684j2 c0684j2) {
        return D4.b(a(), new j$$ExternalSyntheticLambda1(this)).a(ja, c0684j2);
    }

    public final T0 a(Y0 y0) {
        C0767v2 c0767v2 = y0.e;
        boolean z = j;
        if (!z) {
            c();
        }
        if (!z && !c0767v2.O0()) {
            throw new AssertionError();
        }
        if (!z) {
            c();
        }
        T0 t0S = new Z2(this).a(c0767v2, y0).s();
        if (t0S == null || t0S.f.o()) {
            return null;
        }
        return t0S;
    }

    public AbstractC0748t0 a(C0684j2 c0684j2, C0729q2 c0729q2, C0702m<? extends C0680j> c0702m) {
        return a(c0684j2, c0729q2, c0702m, (C0680j) c0702m.f());
    }

    public final AbstractC0748t0 a(C0684j2 c0684j2, C0729q2 c0729q2, C0702m c0702m, C0680j c0680j) {
        if (!j) {
            c();
        }
        return h(c0684j2).c(c0729q2, c0702m, c0680j);
    }

    public W0 a(C0684j2 c0684j2, o5 o5Var, C0702m<? extends C0680j> c0702m) {
        C0680j c0680j = (C0680j) c0702m.f();
        C0729q2 c0729q2A = o5Var.a();
        if (!j) {
            c();
        }
        return h(c0684j2).a(c0729q2A, (C0702m) c0702m, c0680j);
    }

    public final W0 a(C0684j2 c0684j2, o5 o5Var, C0702m c0702m, C0680j c0680j) {
        C0729q2 c0729q2A = o5Var.a();
        if (!j) {
            c();
        }
        return h(c0684j2).a(c0729q2A, c0702m, c0680j);
    }

    public final I4 a(C0729q2 c0729q2, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        if (c0729q2.isInterface()) {
            return b(c0729q2, c0684j2);
        }
        return d(c0729q2, c0684j2);
    }

    public I4 a(C0767v2 c0767v2, C0684j2 c0684j2) {
        if (!j) {
            c();
        }
        return b(c0767v2, c0684j2.H0(), c0684j2.C0());
    }

    public final I4 a(C0767v2 c0767v2, AbstractC0717o2 abstractC0717o2) {
        if (!j) {
            c();
        }
        return b(c0767v2, abstractC0717o2.b(), abstractC0717o2.a());
    }

    public final I4 a(AbstractC0727q0 abstractC0727q0, AbstractC0717o2 abstractC0717o2) {
        if (!j) {
            c();
        }
        return b(abstractC0727q0, abstractC0717o2.b(), abstractC0717o2.a());
    }

    public final I4 a(C0684j2 c0684j2, boolean z) {
        if (!j) {
            c();
        }
        return a(c0684j2.B0(), c0684j2, z);
    }

    public final I4 a(C0767v2 c0767v2, C0684j2 c0684j2, boolean z) {
        if (!j) {
            c();
        }
        if (z) {
            return b(c0684j2, c0767v2);
        }
        return a(c0684j2, c0767v2);
    }

    public final I4 a(C0684j2 c0684j2, C0767v2 c0767v2) {
        if (!j) {
            c();
        }
        return a(c0767v2, c0684j2.H0(), c0684j2.C0());
    }

    public final I4 a(C0767v2 c0767v2, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.a(a(), new j$$ExternalSyntheticLambda0(this)).a(c0767v2, c0737r2, c0759u2);
    }

    public final I4 a(AbstractC0727q0 abstractC0727q0, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.a(a(), new j$$ExternalSyntheticLambda0(this)).a(abstractC0727q0, c0737r2, c0759u2);
    }

    public final I4 a(C0729q2 c0729q2, C0737r2 c0737r2, C0759u2 c0759u2) {
        if (!j) {
            c();
        }
        return D4.a(a(), new j$$ExternalSyntheticLambda0(this)).b(c0729q2, c0737r2, c0759u2);
    }

    @Override // com.android.tools.r8.graph.C0664h
    public final AbstractC0677i3 a(C0767v2 c0767v2, Y0 y0, o5 o5Var) {
        boolean z = j;
        if (!z) {
            c();
        }
        if (!z) {
            c();
        }
        return new Z2(this).a(c0767v2, y0);
    }

    public final AbstractC0677i3 a(AbstractC0727q0 abstractC0727q0, Y0 y0) {
        if (!j) {
            c();
        }
        return new Z2(this).a(abstractC0727q0, y0);
    }
}
