package com.android.tools.r8.dex;

import com.android.tools.r8.FeatureSplit;
import com.android.tools.r8.errors.DexFileOverflowDiagnostic;
import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0759u2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC2178hm;
import com.android.tools.r8.internal.AbstractC3135uW;
import com.android.tools.r8.internal.C1121Ka;
import com.android.tools.r8.shaking.N1;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3932m1;
import com.android.tools.r8.utils.O2;
import com.android.tools.r8.utils.T2;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class h0 extends g0 {
    static final /* synthetic */ boolean h = true;
    protected Set d;
    protected IdentityHashMap e;
    protected final r0 f;
    protected final A1 g;

    h0(C0548k c0548k, Collection collection, A1 a1, AbstractC3135uW abstractC3135uW) {
        super(c0548k);
        this.g = a1;
        this.d = T2.a((Iterable) collection);
        r0 r0Var = new r0(0, this.a, null, null, abstractC3135uW);
        this.f = r0Var;
        if (!h && !this.c.isEmpty()) {
            throw new AssertionError();
        }
        this.c.add(r0Var);
        a(r0Var);
        this.e = r0.a(collection, this.a.E(), this.a.f().b().g());
    }

    private void a(r0 r0Var) {
        List list = this.b.g;
        if (list == null || list.isEmpty()) {
            return;
        }
        Iterator it = this.b.g.iterator();
        while (it.hasNext()) {
            r0Var.c.a((C0759u2) it.next());
        }
        r0Var.c.a();
    }

    protected final Map b() {
        if (!h && this.a.f().h() != this.a.q()) {
            throw new AssertionError();
        }
        if (!this.a.f().h()) {
            return AbstractC2178hm.s();
        }
        C0702m c0702mH0 = this.a.h0();
        C1121Ka c1121KaP = ((C0680j) c0702mH0.f()).p();
        if (c1121KaP.b()) {
            return AbstractC2178hm.s();
        }
        IdentityHashMap identityHashMapA = c1121KaP.a(this.d, c0702mH0.Y(), c0702mH0.C(), c0702mH0.D());
        if (identityHashMapA.size() > 0) {
            Iterator it = identityHashMapA.values().iterator();
            while (it.hasNext()) {
                this.d.removeAll((Set) it.next());
            }
        }
        return identityHashMapA;
    }

    protected final void a(final Set set) {
        N1 n1F = this.a.f().f();
        if (n1F.d()) {
            return;
        }
        final r0 r0Var = (r0) this.c.get(0);
        n1F.a(new Consumer() { // from class: com.android.tools.r8.dex.h0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(r0Var, set, (C0767v2) obj);
            }
        });
        O2 o2 = this.g.e;
        if (r0Var.a(65536)) {
            l0 l0Var = r0Var.c;
            long size = l0Var.b.d.size() + l0Var.f.size();
            l0 l0Var2 = r0Var.c;
            o2.a(new DexFileOverflowDiagnostic(true, size, l0Var2.b.e.size() + l0Var2.e.size()));
            throw null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(r0 r0Var, Set set, C0767v2 c0767v2) {
        C0729q2 c0729q2A = C0729q2.a(this.a.f().c(c0767v2));
        if (c0729q2A != null) {
            r0Var.a(c0729q2A);
            set.remove(c0729q2A);
        }
        r0Var.c.a();
    }

    protected final void a(Map map, AbstractC3135uW abstractC3135uW) {
        if (map.isEmpty()) {
            return;
        }
        for (Map.Entry entry : map.entrySet()) {
            C3932m1 c3932m1 = new C3932m1();
            r0 r0Var = new r0(c3932m1.b(), this.a, (FeatureSplit) entry.getKey());
            this.c.add(r0Var);
            a(r0Var);
            new o0(this.c, AbstractC1639am.c(r0Var), this.a, (Collection) entry.getValue(), this.e, abstractC3135uW, c3932m1).a();
        }
    }
}
