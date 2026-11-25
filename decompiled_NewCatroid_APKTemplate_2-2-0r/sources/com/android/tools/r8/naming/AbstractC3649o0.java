package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0744s2;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0737r2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.J3;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.graph.W0$$ExternalSyntheticLambda12;
import com.android.tools.r8.graph.Y0;
import com.android.tools.r8.graph.j2$$ExternalSyntheticLambda2;
import com.android.tools.r8.internal.OU;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3970u0;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/* JADX INFO: renamed from: com.android.tools.r8.naming.o0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3649o0 {
    static final /* synthetic */ boolean a = true;

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(C0759u2 c0759u2, C0759u2 c0759u3) {
        return c0759u3 == c0759u2;
    }

    public abstract C0759u2 a(J3 j3, A1 a1);

    public abstract C0759u2 a(Y0 y0);

    public abstract C0759u2 a(C0684j2 c0684j2);

    protected abstract C0759u2 a(C0767v2 c0767v2);

    public abstract String a(String str);

    public boolean b() {
        return this instanceof C3663t0;
    }

    public abstract C0759u2 c(C0767v2 c0767v2);

    public final boolean c() {
        return this instanceof C3645m0;
    }

    public final String d(C0767v2 c0767v2) {
        if (a || c0767v2.O0() || c0767v2.M0()) {
            return C3970u0.a(c(c0767v2).toString());
        }
        throw new AssertionError();
    }

    public C0759u2 e(C0767v2 c0767v2) {
        return null;
    }

    public final C0759u2 a(C0721p0 c0721p0, final C0702m c0702m) {
        if (!c0702m.f().i()) {
            return c0721p0.d;
        }
        C0702m<C3733i> c0702mI0 = c0702m.i0();
        Set setA = ((C3733i) c0702mI0.f()).a(c0721p0, c0702mI0);
        if (setA.isEmpty()) {
            return c0721p0.d;
        }
        final C0759u2 c0759u2C0 = a(c0702m.a(), ((W0) setA.iterator().next()).getReference()).C0();
        if (a || setA.stream().map(new W0$$ExternalSyntheticLambda12()).map(new Function() { // from class: com.android.tools.r8.naming.o0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0702m, (C0684j2) obj);
            }
        }).map(new j2$$ExternalSyntheticLambda2()).allMatch(new Predicate() { // from class: com.android.tools.r8.naming.o0$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return AbstractC3649o0.a(c0759u2C0, (C0759u2) obj);
            }
        })) {
            return c0759u2C0;
        }
        throw new AssertionError();
    }

    public final C0759u2 b(C0767v2 c0767v2) {
        if (a || c0767v2.O0()) {
            return a(c0767v2);
        }
        throw new AssertionError();
    }

    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public final C0767v2 a(C0722p1 c0722p1, C0767v2 c0767v2) {
        if (c0767v2.W0() || c0767v2.Y0()) {
            return c0767v2;
        }
        if (c0767v2.M0()) {
            return c0767v2.a(c0722p1, a(c0722p1, c0767v2.b(c0722p1)));
        }
        if (a || c0767v2.O0()) {
            return c0722p1.c(b(c0767v2));
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public C0684j2 a(C0702m c0702m, C0684j2 c0684j2) {
        C0722p1 c0722p1A = c0702m.a();
        return c0722p1A.a(a(c0722p1A, c0684j2.e), a(c0684j2.h, c0722p1A), a(c0684j2));
    }

    public final C0759u2 a(AbstractC0744s2 abstractC0744s2, C0722p1 c0722p1) {
        abstractC0744s2.getClass();
        if (abstractC0744s2 instanceof C0767v2) {
            return c0722p1.c(C3970u0.b(c(abstractC0744s2.w0()).toString()));
        }
        if (abstractC0744s2.y0()) {
            return a(abstractC0744s2.v0());
        }
        if (a || abstractC0744s2.x0()) {
            return a(abstractC0744s2.t0());
        }
        throw new AssertionError();
    }

    public final C0684j2 a(C0722p1 c0722p1, C0684j2 c0684j2) {
        return c0722p1.a(a(c0722p1, c0684j2.e), a(c0684j2.h, c0722p1), a(c0684j2));
    }

    private C0737r2 a(C0737r2 c0737r2, final C0722p1 c0722p1) {
        return c0722p1.b(a(c0722p1, c0737r2.e), (C0767v2[]) Arrays.stream(c0737r2.f.a).map(new Function() { // from class: com.android.tools.r8.naming.o0$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(c0722p1, (C0767v2) obj);
            }
        }).toArray(new IntFunction() { // from class: com.android.tools.r8.naming.o0$$ExternalSyntheticLambda3
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return AbstractC3649o0.a(i);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ C0767v2[] a(int i) {
        return new C0767v2[i];
    }

    public static AbstractC3649o0 a() {
        return new C3645m0();
    }

    public final void a(Collection collection, C0722p1 c0722p1) {
        Set setC = OU.c();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            C0729q2 c0729q2 = (C0729q2) it.next();
            C0767v2 c0767v2A = a(c0722p1, c0729q2.d);
            boolean zAdd = setC.add(c0767v2A);
            if (!a && !zAdd) {
                throw new AssertionError("Duplicate definition of type `" + c0767v2A.r0() + "`");
            }
            Iterator<com.android.tools.r8.graph.T0> it2 = c0729q2.W0().iterator();
            while (it2.hasNext()) {
                Y0 reference = it2.next().getReference();
                Y0 y0A = c0722p1.a(a(c0722p1, reference.e), a(c0722p1, reference.h), a(reference));
                boolean zAdd2 = setC.add(y0A);
                if (!a && !zAdd2) {
                    throw new AssertionError("Duplicate definition of field `" + y0A.r0() + "`");
                }
            }
            Iterator<W0> it3 = c0729q2.M1().iterator();
            while (it3.hasNext()) {
                C0684j2 c0684j2A = a(c0722p1, it3.next().getReference());
                boolean zAdd3 = setC.add(c0684j2A);
                if (!a && !zAdd3) {
                    throw new AssertionError("Duplicate definition of method `" + c0684j2A.r0() + "`");
                }
            }
        }
    }
}
