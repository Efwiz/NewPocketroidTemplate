package com.android.tools.r8.internal;

import com.android.tools.r8.ClassFileConsumer;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0725p4;
import com.android.tools.r8.graph.C0729q2;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class CA extends AA {
    static final /* synthetic */ boolean h = true;
    private final C0702m f;
    private final C0684j2 g;

    CA(C0684j2 c0684j2, boolean z, C0684j2 c0684j3, C0702m c0702m) {
        super(c0684j3, EnumC1953et.e, z);
        this.g = c0684j2;
        this.f = c0702m;
    }

    @Override // com.android.tools.r8.internal.GA
    final com.android.tools.r8.graph.o5 a(final InterfaceC1011Gi interfaceC1011Gi, Consumer consumer) {
        C0729q2 c0729q2A0 = this.f.c(this.g.e).a0();
        com.android.tools.r8.graph.W0 w0A = c0729q2A0.Y().a(this.g, new Function() { // from class: com.android.tools.r8.internal.CA$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.a(interfaceC1011Gi, (com.android.tools.r8.graph.W0) obj);
            }
        });
        if (w0A == null) {
            com.android.tools.r8.graph.o5 o5VarF = c0729q2A0.f(this.a);
            boolean z = h;
            if (!z && o5VarF == null) {
                throw new AssertionError();
            }
            if (z || o5VarF.e().H1()) {
                return o5VarF;
            }
            throw new AssertionError();
        }
        boolean z2 = h;
        if (!z2 && (this.f.Y().f instanceof ClassFileConsumer) && !w0A.a1().z0()) {
            throw new AssertionError();
        }
        if (z2 || !this.f.Y().Y0() || w0A.a1().B0()) {
            return new com.android.tools.r8.graph.o5(c0729q2A0, w0A);
        }
        throw new AssertionError();
    }

    @Override // com.android.tools.r8.internal.GA
    public final C0684j2 b() {
        return this.g;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ com.android.tools.r8.graph.W0 a(InterfaceC1011Gi interfaceC1011Gi, com.android.tools.r8.graph.W0 w0) {
        C0725p4 c0725p4P = w0.f.P();
        c0725p4P.D();
        c0725p4P.I();
        c0725p4P.C();
        com.android.tools.r8.graph.W0 w0A = com.android.tools.r8.graph.W0.Y1().a(this.a).a(c0725p4P).a(w0.e1()).a(w0.s0()).a(w0.h).a(w0.a1().a(this.a, w0, this.f.a())).b(w0.I0()).a(w0.Y0()).a();
        w0A.a(this.f, w0);
        interfaceC1011Gi.b(w0.getReference(), this.a);
        com.android.tools.r8.graph.W0.a(w0A.a1(), this.a.F0(), this.f);
        return w0A;
    }
}
