package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0626c0;
import com.android.tools.r8.graph.C0633d0;
import com.android.tools.r8.graph.C0658g0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.internal.T3;
import com.android.tools.r8.utils.C3970u0;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class F {
    private static final F c = new F(null, null);
    static final /* synthetic */ boolean d = true;
    private final L.b a;
    private final C3846e b;

    public F(L.b bVar, C3846e c3846e) {
        this.a = bVar;
        this.b = c3846e;
    }

    public static T3 a(L l) {
        return new E(null, null, l);
    }

    public static F b(C0702m c0702m, C0729q2 c0729q2) {
        if (!c0729q2.f2()) {
            F fA = a(c0702m, c0729q2);
            if (d || fA != c || C0633d0.a(c0729q2.s0(), c0702m.a(), c0702m.D(), c0702m.d()) == null) {
                return fA;
            }
            throw new AssertionError();
        }
        F fJ2 = c0729q2.j2();
        if (fJ2 == null) {
            return c;
        }
        if (!d && fJ2.b != null) {
            throw new AssertionError();
        }
        C0767v2 c0767v2E = c0729q2.d;
        L.b bVar = fJ2.a;
        C0722p1 c0722p1A = c0702m.a();
        if (!bVar.e()) {
            c0767v2E = c0722p1A.e(C3970u0.l(L.a(c0767v2E, bVar)));
        }
        return new F(fJ2.a, C3846e.a(c0729q2, c0767v2E, c0702m));
    }

    public final boolean c() {
        L.b bVar = this.a;
        return (bVar == null || bVar.i()) ? false : true;
    }

    public final boolean d() {
        L.b bVar = this.a;
        return bVar != null && bVar.i();
    }

    public static F a(T3 t3) {
        if (t3 instanceof E) {
            E e = (E) t3;
            if (e.e.equals(e.f.a())) {
                return new F(e.d, null);
            }
        }
        return null;
    }

    private static F a(C0702m c0702m, C0729q2 c0729q2) {
        if (c0729q2.f != c0702m.a().Z1) {
            return c;
        }
        com.android.tools.r8.graph.E e = c0729q2.e;
        if (!e.q() || e.V()) {
            return c;
        }
        final C0626c0 c0626c0A = C0633d0.a(c0729q2.s0(), c0702m.a(), c0702m.D(), c0702m.d());
        if (c0626c0A == null) {
            return c;
        }
        if (!d && c0729q2.s0().size() != 1) {
            throw new AssertionError();
        }
        L.b bVarB = c0626c0A.b();
        if (bVarB.i()) {
            if (!c0729q2.g.isEmpty()) {
                return c;
            }
            for (W0 w0 : c0729q2.M1()) {
                if (!w0.E0() || !w0.F1() || !w0.getAccessFlags().m() || !w0.s0().isEmpty() || !w0.j1().isEmpty()) {
                    return c;
                }
            }
        }
        c0729q2.a(C0658g0.t0());
        c0729q2.f(new Consumer() { // from class: com.android.tools.r8.synthesis.F$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                F.a(c0626c0A, (W0) obj);
            }
        });
        C0767v2 c0767v2E = c0729q2.d;
        C0722p1 c0722p1A = c0702m.a();
        if (!bVarB.e()) {
            c0767v2E = c0722p1A.e(C3970u0.l(L.a(c0767v2E, bVarB)));
        }
        return new F(bVarB, C3846e.a(c0729q2, c0767v2E, c0702m));
    }

    public final L.b b() {
        return this.a;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0626c0 c0626c0, W0 w0) {
        w0.g(c0626c0.a());
    }

    public final C3846e a() {
        return this.b;
    }
}
