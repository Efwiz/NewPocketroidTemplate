package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0635d2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.iN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2230iN implements InterfaceC3179v2 {
    private final C2626nc a;
    private final InterfaceC3179v2 b;

    private C2230iN(C2626nc c2626nc, C3102u2 c3102u2) {
        this.a = c2626nc;
        this.b = c3102u2;
    }

    public static InterfaceC3179v2 a(C0702m c0702m, C3102u2 c3102u2) {
        if (c0702m.Y().q0().e()) {
            AbstractC2151hN abstractC2151hNB = AbstractC2151hN.b(c0702m);
            abstractC2151hNB.getClass();
            if (!(abstractC2151hNB instanceof IH)) {
                return new C2230iN(abstractC2151hNB.a(), c3102u2);
            }
        }
        return c3102u2;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3179v2
    public final void b(C0702m c0702m) {
        this.a.a(c0702m);
        this.b.b(c0702m);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3179v2
    public final boolean isEmpty() {
        return false;
    }

    @Override // com.android.tools.r8.internal.InterfaceC3179v2
    public final void a(C0729q2 c0729q2, C0635d2 c0635d2) {
        this.b.a(c0729q2, c0635d2);
    }

    @Override // com.android.tools.r8.internal.InterfaceC3179v2
    public final void a(final C0729q2 c0729q2, C0635d2 c0635d2, C0729q2 c0729q3) {
        this.a.a(new Consumer() { // from class: com.android.tools.r8.internal.iN$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2230iN.a(c0729q2, (InterfaceC1997fN) obj);
            }
        }, c0729q3);
        this.b.a(c0729q2, c0635d2, c0729q3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0729q2 c0729q2, InterfaceC1997fN interfaceC1997fN) {
        interfaceC1997fN.a(c0729q2.getType()).a(c0729q2.X1().getReference());
    }
}
