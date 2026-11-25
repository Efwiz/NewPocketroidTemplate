package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.utils.InterfaceC3937n1;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Xf, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1501Xf extends AbstractC2744p implements InterfaceC1547Yy {
    private static final C1501Xf a = new C1501Xf();
    static final /* synthetic */ boolean b = true;

    private C1501Xf() {
    }

    public static C1501Xf e() {
        return a;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final AbstractC2744p a(C0702m c0702m, DH dh, com.android.tools.r8.graph.u5 u5Var) {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p, com.android.tools.r8.internal.InterfaceC1547Yy
    public final boolean a(com.android.tools.r8.graph.T0 t0) {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final InterfaceC1547Yy b() {
        return this;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final boolean c() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final boolean d() {
        return true;
    }

    @Override // com.android.tools.r8.internal.InterfaceC1547Yy
    public final int size() {
        return 0;
    }

    @Override // com.android.tools.r8.internal.AbstractC2744p
    public final AbstractC2744p a(final C0702m c0702m, com.android.tools.r8.graph.proto.c cVar) {
        if (cVar.g()) {
            return this;
        }
        final C2092gc c2092gc = new C2092gc();
        cVar.a(new InterfaceC3937n1() { // from class: com.android.tools.r8.internal.Xf$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.InterfaceC3937n1
            public final void a(int i, Object obj) {
                C1501Xf.a(c0702m, c2092gc, i, (com.android.tools.r8.graph.proto.b) obj);
            }
        });
        return c2092gc.c() ? this : c2092gc;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, C2092gc c2092gc, int i, com.android.tools.r8.graph.proto.b bVar) {
        if (bVar.c()) {
            com.android.tools.r8.graph.proto.h hVarA = bVar.a();
            if (hVarA.h() && hVarA.f().B()) {
                com.android.tools.r8.graph.T0 t0A = hVarA.f().p().a((com.android.tools.r8.graph.Q0) c0702m);
                if (t0A != null) {
                    c2092gc.b(t0A);
                } else if (!b) {
                    throw new AssertionError();
                }
            }
        }
    }
}
