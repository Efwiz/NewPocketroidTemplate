package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0717o2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class OE extends AbstractC2550me {
    private final CN f;
    private final IdentityHashMap g;

    OE(C0702m c0702m, com.android.tools.r8.graph.H3 h3, CN cn, CN cn2) {
        super(c0702m, h3);
        this.g = new IdentityHashMap();
        this.f = cn;
        Iterator it = cn2.iterator();
        while (it.hasNext()) {
            com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) it.next();
            ((C2720of) this.g.computeIfAbsent(o5Var.a(), com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()))).a(o5Var);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2720of c2720of, C0729q2 c0729q2) {
        c2720of.a((C2720of) this.g.get(c0729q2));
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final boolean c(C0729q2 c0729q2) {
        return c0729q2.isInterface() && super.c(c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void i(C0729q2 c0729q2) {
        this.g.remove(c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void k(C0729q2 c0729q2) {
        final C2720of c2720of = (C2720of) this.g.computeIfAbsent(c0729q2, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()));
        com.android.tools.r8.graph.H3 h3 = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.OE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2720of, (C0729q2) obj);
            }
        };
        h3.getClass();
        h3.a(CM.b(), consumer, c0729q2);
        for (C0729q2 c0729q3 : this.b.a(c0729q2)) {
            if (!c0729q3.isInterface()) {
                Iterator it = c2720of.iterator();
                while (it.hasNext()) {
                    com.android.tools.r8.graph.I4.c<?> cVarO = ((C3733i) this.a.f()).a(c0729q3, (AbstractC0717o2) it.next()).o();
                    if (cVarO != null && cVarO.d().e0()) {
                        com.android.tools.r8.graph.o5 o5VarS = cVarO.s();
                        if (!o5VarS.a().isInterface() && o5VarS.a() != c0729q3) {
                            this.f.add(o5VarS);
                        }
                    }
                }
            }
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void a(Consumer consumer, C0729q2 c0729q2) {
        for (C0729q2 c0729q3 : this.b.a(c0729q2)) {
            if (c0729q3.isInterface()) {
                consumer.accept(c0729q3);
            }
        }
    }
}
