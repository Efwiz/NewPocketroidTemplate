package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0722p1;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.dt, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1870dt implements C7 {
    static final /* synthetic */ boolean a = true;

    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        com.android.tools.r8.graph.W0 w0B;
        if (!b7.L() && !b7.I()) {
            return C3224ve.c();
        }
        I7 i7L = b7.l();
        C0684j2 c0684j2T = i7L.T();
        C0684j2 c0684j2T2 = i7L.T();
        if (c0684j2T2.B0() != o5Var.t() || (w0B = o5Var.a().b(c0684j2T2)) == null || w0B.E0()) {
            w0B = null;
        } else if (!a && c0684j2T2.e != w0B.J0()) {
            throw new AssertionError();
        }
        return w0B == null ? C3224ve.c() : a(i7L, c0684j2T);
    }

    private static C3224ve a(final I7 i7, final C0684j2 c0684j2) {
        return new C3005se((C3075te) new C3075te().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.dt$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return C1870dt.a(c0684j2, i7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(C0684j2 c0684j2, I7 i7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return AbstractC1639am.c(new I7(183, c0684j2, i7.V()));
    }
}
