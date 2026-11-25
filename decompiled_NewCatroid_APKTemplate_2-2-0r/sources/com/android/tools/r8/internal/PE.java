package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.IdentityHashMap;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class PE extends AbstractC2550me {
    private final IdentityHashMap f;
    private final CN g;

    PE(C0702m c0702m, com.android.tools.r8.graph.H3 h3, CN cn, CN cn2) {
        super(c0702m, h3);
        this.f = new IdentityHashMap();
        this.g = CN.b();
        cn.forEach(new Consumer() { // from class: com.android.tools.r8.internal.PE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
        cn2.forEach(new Consumer() { // from class: com.android.tools.r8.internal.PE$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        C2720of c2720of = (C2720of) this.f.computeIfAbsent(o5Var.a(), com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()));
        c2720of.getClass();
        c2720of.a(o5Var.getReference());
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void i(C0729q2 c0729q2) {
        this.f.remove(c0729q2);
    }

    @Override // com.android.tools.r8.internal.AbstractC2550me
    public final void k(C0729q2 c0729q2) {
        final C2720of c2720of = (C2720of) this.f.computeIfAbsent(c0729q2, com.android.tools.r8.utils.Z1.a(new IP$$ExternalSyntheticLambda2()));
        com.android.tools.r8.graph.H3 h3 = this.b;
        Consumer consumer = new Consumer() { // from class: com.android.tools.r8.internal.PE$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2720of, (C0729q2) obj);
            }
        };
        h3.getClass();
        h3.a(CM.b(), consumer, c0729q2);
        c0729q2.l(new Consumer() { // from class: com.android.tools.r8.internal.PE$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c2720of, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2720of c2720of, C0729q2 c0729q2) {
        c2720of.a((C2720of) this.f.get(c0729q2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C2720of c2720of, com.android.tools.r8.graph.o5 o5Var) {
        if (c2720of.b(o5Var)) {
            this.g.add(o5Var);
        }
    }
}
