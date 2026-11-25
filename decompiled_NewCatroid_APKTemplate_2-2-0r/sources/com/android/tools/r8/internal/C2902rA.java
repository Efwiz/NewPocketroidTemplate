package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.rA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2902rA implements InterfaceC3680a0 {
    final EnumC1364Sy a;
    final C2759pA b;

    private C2902rA(EnumC1364Sy enumC1364Sy, C2759pA c2759pA) {
        this.a = enumC1364Sy;
        this.b = c2759pA;
    }

    static C2902rA a(C1227Ny c1227Ny, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        return new C2902rA(c1227Ny.d(), C2759pA.a(c0722p1, o2, c1227Ny.c()));
    }

    final boolean a(final Consumer consumer, C0702m c0702m) {
        if (this.a == null && this.b == null) {
            consumer.accept(C1227Ny.c);
            return false;
        }
        return this.b.a(new Consumer() { // from class: com.android.tools.r8.internal.rA$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, (C0942Dy) obj);
            }
        }, c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Consumer consumer, C0942Dy c0942Dy) {
        consumer.accept(new C1227Ny(this.a, c0942Dy));
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C2759pA c2759pA = this.b;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
    }
}
