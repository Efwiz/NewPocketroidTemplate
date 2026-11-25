package com.android.tools.r8.ir.optimize;

import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.I4;
import com.android.tools.r8.graph.o5;
import com.android.tools.r8.internal.AbstractC1305Qs;
import com.android.tools.r8.internal.AbstractC3477z20;
import com.android.tools.r8.internal.C1229Oa;
import com.android.tools.r8.internal.C2409kl;
import com.android.tools.r8.internal.C3332x4;
import com.android.tools.r8.internal.C3387xn;
import com.android.tools.r8.internal.V9;
import java.util.IdentityHashMap;
import java.util.Map;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Q implements InterfaceC3556e0, InterfaceC3558f0 {
    static final /* synthetic */ boolean d = true;
    private final C0702m a;
    private final o5 b;
    private final Map c;

    Q(C0702m c0702m, o5 o5Var, IdentityHashMap identityHashMap) {
        this.a = c0702m;
        this.b = o5Var;
        this.c = identityHashMap;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final void a(Y y) {
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final boolean a() {
        return true;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final boolean a(AbstractC1305Qs abstractC1305Qs, I4.c cVar, o5 o5Var, EnumC3542a0 enumC3542a0, AbstractC3477z20 abstractC3477z20) {
        return true;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final boolean a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, Y y, C3332x4 c3332x4, AbstractC3477z20 abstractC3477z20) {
        return false;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final boolean a(W w, AbstractC3477z20 abstractC3477z20) {
        return true;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final C0702m b() {
        return this.a;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final o5 a(o5 o5Var, AbstractC1305Qs abstractC1305Qs) {
        Z z = (Z) this.c.get(abstractC1305Qs);
        if (z != null) {
            return z.a;
        }
        return AbstractC0748t0.a(abstractC1305Qs.h(this.a, o5Var));
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3556e0
    public final X a(C2409kl c2409kl, AbstractC1305Qs abstractC1305Qs, I4.c cVar, o5 o5Var, o5 o5Var2, V9 v9, C3387xn c3387xn, AbstractC3477z20 abstractC3477z20) {
        W w;
        Z z = (Z) this.c.get(abstractC1305Qs);
        if (z == null) {
            w = null;
        } else {
            if (!d && this.b.e() == z.a.e()) {
                throw new AssertionError();
            }
            w = new W(z.a, abstractC1305Qs, EnumC3542a0.a);
        }
        if (w != null && a(this.a, w, abstractC1305Qs, o5Var, o5Var2)) {
            return w;
        }
        return null;
    }

    @Override // com.android.tools.r8.ir.optimize.InterfaceC3558f0
    public final C1229Oa a(AbstractC1305Qs abstractC1305Qs, C1229Oa c1229Oa) {
        boolean z = d;
        if (!z && !abstractC1305Qs.Z1()) {
            throw new AssertionError();
        }
        Z z2 = (Z) this.c.get(abstractC1305Qs.c0());
        if (!z && z2 == null) {
            throw new AssertionError();
        }
        C0729q2 c0729q2 = z2.b;
        return c0729q2 != null ? c0729q2.getType().c(this.a).b() : c1229Oa;
    }
}
