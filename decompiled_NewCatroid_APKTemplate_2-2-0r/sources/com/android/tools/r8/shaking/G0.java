package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0632d;
import com.android.tools.r8.graph.C0592a;
import com.android.tools.r8.graph.C0607b;
import com.android.tools.r8.graph.C0625c;
import com.android.tools.r8.graph.o5;
import java.util.IdentityHashMap;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class G0 {
    private final IdentityHashMap a = new IdentityHashMap();

    private H0 a(com.android.tools.r8.graph.Y0 y0) {
        return (H0) this.a.computeIfAbsent(y0, new Function() { // from class: com.android.tools.r8.shaking.G0$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return G0.b((com.android.tools.r8.graph.Y0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ H0 b(com.android.tools.r8.graph.Y0 y0) {
        return new H0();
    }

    public final G0 c(com.android.tools.r8.graph.Y0 y0) {
        a(y0).a = C0625c.a;
        return this;
    }

    public final void d(com.android.tools.r8.graph.Y0 y0) {
        a(y0).b = C0625c.a;
    }

    public final void a(com.android.tools.r8.graph.Y0 y0, o5 o5Var) {
        H0 h0A = a(y0);
        AbstractC0632d abstractC0632d = h0A.a;
        abstractC0632d.getClass();
        if (abstractC0632d instanceof C0607b) {
            C0592a c0592a = new C0592a();
            c0592a.a(y0, o5Var);
            h0A.a = c0592a;
            return;
        }
        AbstractC0632d abstractC0632d2 = h0A.a;
        abstractC0632d2.getClass();
        if (abstractC0632d2 instanceof C0592a) {
            h0A.a.a().a(y0, o5Var);
        } else {
            if (H0.c) {
                return;
            }
            AbstractC0632d abstractC0632d3 = h0A.a;
            abstractC0632d3.getClass();
            if (!(abstractC0632d3 instanceof C0625c)) {
                throw new AssertionError();
            }
        }
    }

    public final void b(com.android.tools.r8.graph.Y0 y0, o5 o5Var) {
        H0 h0A = a(y0);
        AbstractC0632d abstractC0632d = h0A.b;
        abstractC0632d.getClass();
        if (abstractC0632d instanceof C0607b) {
            C0592a c0592a = new C0592a();
            c0592a.a(y0, o5Var);
            h0A.b = c0592a;
            return;
        }
        AbstractC0632d abstractC0632d2 = h0A.b;
        abstractC0632d2.getClass();
        if (abstractC0632d2 instanceof C0592a) {
            h0A.b.a().a(y0, o5Var);
        } else {
            if (H0.c) {
                return;
            }
            AbstractC0632d abstractC0632d3 = h0A.b;
            abstractC0632d3.getClass();
            if (!(abstractC0632d3 instanceof C0625c)) {
                throw new AssertionError();
            }
        }
    }

    public final I0 a() {
        return new I0(this.a);
    }
}
