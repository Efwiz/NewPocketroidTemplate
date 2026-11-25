package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0753u;
import java.util.AbstractCollection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.aa, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1622aa {
    static final /* synthetic */ boolean b = true;
    private final AbstractC1639am a;

    private C1622aa(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    public static C1622aa a(C2688oE c2688oE) {
        final X9 x9 = new X9();
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.aa$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C1622aa.a(x9, (C0729q2) obj);
            }
        });
        return new C1622aa(x9.a.a());
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean b(com.android.tools.r8.graph.o5 o5Var) {
        return Boolean.valueOf(o5Var.e().a1().z0());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(X9 x9, C0729q2 c0729q2) {
        if (c0729q2.p1()) {
            com.android.tools.r8.graph.o5 o5VarX1 = c0729q2.X1();
            x9.getClass();
            boolean z = X9.b;
            if (!z && !o5VarX1.e().u1()) {
                throw new AssertionError();
            }
            if (!z && !o5VarX1.e().q1()) {
                throw new AssertionError();
            }
            x9.a.a(o5VarX1);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final com.android.tools.r8.graph.W a(C0684j2 c0684j2) {
        boolean z = b;
        if (!z && this.a.isEmpty()) {
            throw new AssertionError();
        }
        Y9IA y9ia = null;
        Object[] objArr = 0;
        if (((com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.a)).e().a1().z0()) {
            if (!z && !com.android.tools.r8.utils.I1.a((AbstractCollection) this.a, new Function() { // from class: com.android.tools.r8.internal.aa$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1622aa.b((com.android.tools.r8.graph.o5) obj);
                }
            })) {
                throw new AssertionError();
            }
            Y9 y9 = new Y9(this);
            return new C0753u(c0684j2.B0(), y9.a, y9.b, y9.a(AbstractC1915eM.c.x().a(0).a(c0684j2).a()));
        }
        return new Z9(this.a, c0684j2);
    }

    public final Z8 a() {
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) com.android.tools.r8.utils.V1.a(this.a);
        Z8 z8 = null;
        if (this.a.size() == 1) {
            com.android.tools.r8.graph.W0 w0E = o5Var.e();
            if (w0E.p1()) {
                return w0E.Z0();
            }
            return null;
        }
        if (o5Var.e().a1().z0()) {
            if (!b && !com.android.tools.r8.utils.I1.a((AbstractCollection) this.a, new Function() { // from class: com.android.tools.r8.internal.aa$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C1622aa.a((com.android.tools.r8.graph.o5) obj);
                }
            })) {
                throw new AssertionError();
            }
            for (com.android.tools.r8.graph.o5 o5Var2 : this.a) {
                if (o5Var2.e().p1()) {
                    z8 = (Z8) com.android.tools.r8.utils.structural.s.c(z8, o5Var2.e().Z0());
                }
            }
        }
        return z8;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Boolean a(com.android.tools.r8.graph.o5 o5Var) {
        return Boolean.valueOf(o5Var.e().a1().z0());
    }

    public final com.android.tools.r8.androidapi.h a(C0702m c0702m) {
        if (b || !this.a.isEmpty()) {
            return (com.android.tools.r8.androidapi.h) com.android.tools.r8.utils.V1.a(this.a, c0702m.o(), new BiFunction() { // from class: com.android.tools.r8.internal.aa$$ExternalSyntheticLambda0
                @Override // java.util.function.BiFunction
                public final Object apply(Object obj, Object obj2) {
                    return C1622aa.a((com.android.tools.r8.androidapi.h) obj, (com.android.tools.r8.graph.o5) obj2);
                }
            });
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.androidapi.h a(com.android.tools.r8.androidapi.h hVar, com.android.tools.r8.graph.o5 o5Var) {
        return hVar.e(o5Var.e().H0());
    }
}
