package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.graph.C0767v2;
import java.util.Collection;
import java.util.Iterator;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Be, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0854Be implements C7 {
    private final C0702m a;
    private final C0891Ce b;

    public C0854Be(C0702m c0702m) {
        this.a = c0702m;
        this.b = new C0891Ce(c0702m);
    }

    public static C0854Be a(C0702m c0702m) {
        Iterator it = c0702m.a().f3.iterator();
        while (it.hasNext()) {
            if (c0702m.f().d((C0767v2) it.next()) != null) {
                return new C0854Be(c0702m);
            }
        }
        return null;
    }

    private static C3224ve a(final B7 b7) {
        return C3224ve.a().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.Be$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return C0854Be.a(b7, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }).a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Collection a(B7 b7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        return AbstractC1639am.c(b7);
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0076  */
    @Override // com.android.tools.r8.internal.C7
    public final C3224ve a(com.android.tools.r8.graph.o5 o5Var, B7 b7) {
        B7 i7;
        if (!this.a.a().f3.contains(o5Var.t())) {
            i7 = null;
        } else if (b7.S()) {
            X8 x8W = b7.w();
            C0767v2 c0767v2A = this.b.a(x8W.getType());
            if (c0767v2A != x8W.getType()) {
                i7 = x8W.a(c0767v2A);
            } else {
                i7 = null;
            }
        } else if (b7.E()) {
            AbstractC2206i7 abstractC2206i7H = b7.h();
            com.android.tools.r8.graph.Y0 y0A = this.b.a(abstractC2206i7H.getField(), o5Var);
            if (y0A != null) {
                i7 = abstractC2206i7H.a(y0A);
            } else {
                i7 = null;
            }
        } else if (b7.H()) {
            I7 i7L = b7.l();
            C0684j2 c0684j2A = this.b.a(i7L.V(), o5Var, i7L.T());
            if (c0684j2A != null) {
                i7 = new I7(i7L.U(), c0684j2A, i7L.V());
            } else {
                i7 = null;
            }
        } else {
            i7 = null;
        }
        if (i7 == null) {
            return C3224ve.c();
        }
        return a(i7);
    }
}
