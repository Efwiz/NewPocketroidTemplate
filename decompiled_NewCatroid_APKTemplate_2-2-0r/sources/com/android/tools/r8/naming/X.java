package com.android.tools.r8.naming;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.W0;
import com.android.tools.r8.utils.C3894f2;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class X extends Y {
    static final /* synthetic */ boolean f = true;
    private final C3604a0 c;
    private final X d;
    private final S e;

    private X(X x, Function function, S s, C3604a0 c3604a0) {
        super(function);
        this.d = x;
        this.e = s;
        this.c = c3604a0;
    }

    static X a(Function function, S s, C3604a0 c3604a0) {
        return new X(null, function, s, c3604a0);
    }

    private C0759u2 d(C0684j2 c0684j2) {
        X x;
        W w = (W) b(c0684j2);
        C0759u2 c0759u2 = w != null ? (C0759u2) w.b.get(C3894f2.a().c(c0684j2)) : null;
        return (c0759u2 != null || (x = this.d) == null) ? c0759u2 : x.d(c0684j2);
    }

    final C0759u2 b(W0 w0, BiPredicate biPredicate) {
        C0759u2 c0759u2A = this.e.a(w0, (W) c(w0.getReference()), biPredicate);
        if (f || c0759u2A != null) {
            return c0759u2A;
        }
        throw new AssertionError();
    }

    final X a(C3604a0 c3604a0) {
        return new X(this, this.b, this.e, c3604a0);
    }

    final C0759u2 a(W0 w0) {
        return a(w0, new BiPredicate() { // from class: com.android.tools.r8.naming.X$$ExternalSyntheticLambda0
            @Override // java.util.function.BiPredicate
            public final boolean test(Object obj, Object obj2) {
                return this.f$0.a((C0759u2) obj, (C0684j2) obj2);
            }
        });
    }

    final C0759u2 a(W0 w0, BiPredicate biPredicate) {
        C0759u2 c0759u2D = d(w0.getReference());
        if (c0759u2D != null) {
            return c0759u2D;
        }
        Set setD = this.c.d(w0.getReference());
        if (setD != null && setD.size() == 1) {
            C0759u2 c0759u2 = (C0759u2) setD.iterator().next();
            if (a(c0759u2, w0.getReference())) {
                return c0759u2;
            }
        }
        return b(w0, biPredicate);
    }

    final void a(C0759u2 c0759u2, W0 w0) {
        ((W) c(w0.getReference())).a(w0.getReference(), c0759u2);
    }

    final boolean a(C0759u2 c0759u2, C0684j2 c0684j2) {
        Set setA = a(c0684j2, c0759u2);
        if (setA != null && setA.contains(C3894f2.a().c(c0684j2))) {
            return true;
        }
        if (!this.c.a(c0684j2, c0759u2) && setA == null) {
            return true;
        }
        Set setD = this.c.d(c0684j2);
        return setD != null && setD.contains(c0759u2);
    }

    private Set a(C0684j2 c0684j2, C0759u2 c0759u2) {
        X x;
        W w = (W) b(c0684j2);
        Set set = w != null ? (Set) w.c.get(c0759u2) : null;
        return (set != null || (x = this.d) == null) ? set : x.a(c0684j2, c0759u2);
    }

    @Override // com.android.tools.r8.naming.Y
    final Object a(C0684j2 c0684j2) {
        X x = this.d;
        return new W();
    }
}
