package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.fd, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2014fd extends AbstractC3473z1 {
    static final /* synthetic */ boolean h = true;
    private final C0684j2 d;
    private final C0684j2 e;
    private final com.android.tools.r8.graph.Y0 f;
    private final InterfaceC2415kr g;

    public C2014fd(C2807pq c2807pq, C0684j2 c0684j2, com.android.tools.r8.graph.Y0 y0, C0684j2 c0684j3) {
        this.g = c2807pq;
        this.d = c0684j2;
        this.f = y0;
        this.e = c0684j3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ KV a(com.android.tools.r8.graph.o5 o5Var, AbstractC1915eM abstractC1915eM) {
        return new C1936ed(this.g, this.d, this.f, abstractC1915eM, this.e);
    }

    @Override // com.android.tools.r8.internal.AbstractC3473z1
    public final Consumer H0() {
        return new Consumer() { // from class: com.android.tools.r8.internal.fd$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.J5) obj);
            }
        };
    }

    @Override // com.android.tools.r8.internal.AbstractC3473z1
    public final InterfaceC3404y1 I0() {
        return new InterfaceC3404y1() { // from class: com.android.tools.r8.internal.fd$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC3404y1
            public final KV a(com.android.tools.r8.graph.o5 o5Var, AbstractC1915eM abstractC1915eM) {
                return this.f$0.a(o5Var, abstractC1915eM);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.J5 j5) {
        if (!h && !j5.e().g()) {
            throw new AssertionError();
        }
        InterfaceC1912eK it = this.g.values().iterator();
        while (it.hasNext()) {
            j5.a((C0684j2) it.next());
            if (j5.e().f()) {
                return;
            }
        }
    }
}
