package com.android.tools.r8.internal;

import com.android.tools.r8.errors.UnsupportedFeatureDiagnostic;
import com.android.tools.r8.errors.UnsupportedInvokeCustomDiagnostic;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0721p0;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import java.util.Collection;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class D10 extends C10 {
    D10(C0702m c0702m) {
        super(c0702m, "invoke-dynamic", com.android.tools.r8.utils.A1.Q0());
    }

    static void a(C0702m c0702m, C1507Xl c1507Xl) {
        if (c0702m.Y().O()) {
            return;
        }
        c1507Xl.a(new D10(c0702m));
    }

    @Override // com.android.tools.r8.internal.C10
    final UnsupportedFeatureDiagnostic a(Origin origin, MethodPosition methodPosition) {
        return new UnsupportedInvokeCustomDiagnostic(origin, methodPosition);
    }

    @Override // com.android.tools.r8.internal.C10
    final C3224ve a(B7 b7) {
        final J7 j7N = b7.n();
        if (j7N == null) {
            return null;
        }
        return new C3005se((C3075te) new C3075te().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.D10$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(j7N, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Collection a(J7 j7, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        a(o5Var);
        C1507Xl c1507XlG = AbstractC1639am.g();
        C0721p0 c0721p0T = j7.T();
        C10.a(c0721p0T.e, c1507XlG);
        ac.a(1);
        a(c1230Ob, g7, c1507XlG);
        C10.b(c1507XlG, c0721p0T.e.v0());
        return c1507XlG.a();
    }
}
