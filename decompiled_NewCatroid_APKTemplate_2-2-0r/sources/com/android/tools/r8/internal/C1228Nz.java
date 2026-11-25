package com.android.tools.r8.internal;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.Objects;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Nz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1228Nz implements InterfaceC3680a0 {
    private final C1091Iz a;
    private final boolean b;

    private C1228Nz(C1091Iz c1091Iz, boolean z) {
        this.a = c1091Iz;
        this.b = z;
    }

    static C1228Nz a(AbstractC0727q0 abstractC0727q0, C3095ty c3095ty, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2, C1201Mz c1201Mz) {
        C1091Iz c1091IzA = C1091Iz.a(c3095ty.b, c0722p1, o2, c1201Mz.b(0));
        com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.j jVarB = com.android.tools.r8.jetbrains.kotlinx.metadata.jvm.d.b(c3095ty.b);
        if (jVarB != null) {
            for (com.android.tools.r8.graph.W0 w0 : abstractC0727q0.M1()) {
                if (AbstractC1745cA.a(w0.getReference()).a().equals(jVarB.a())) {
                    w0.a(c1091IzA);
                    return new C1228Nz(c1091IzA, true);
                }
            }
        }
        return new C1228Nz(c1091IzA, false);
    }

    final boolean a(C0702m c0702m, AbstractC0727q0 abstractC0727q0, Consumer consumer) {
        final C3095ty c3095ty = (C3095ty) AbstractC1745cA.a(consumer, new C3095ty());
        com.android.tools.r8.graph.W0 w0 = null;
        if (!this.b) {
            C1091Iz c1091Iz = this.a;
            Objects.requireNonNull(c3095ty);
            c1091Iz.a(new Consumer() { // from class: com.android.tools.r8.internal.Nz$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    c3095ty.a((C2815py) obj);
                }
            }, (com.android.tools.r8.graph.W0) null, c0702m);
            return true;
        }
        for (com.android.tools.r8.graph.W0 w1 : abstractC0727q0.M1()) {
            if (w1.f1() == this.a) {
                w0 = w1;
                break;
            }
        }
        C1091Iz c1091Iz2 = this.a;
        Objects.requireNonNull(c3095ty);
        return c1091Iz2.a(new Consumer() { // from class: com.android.tools.r8.internal.Nz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c3095ty.a((C2815py) obj);
            }
        }, w0, c0702m);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.a.a(q0);
    }
}
