package com.android.tools.r8.internal;

import com.android.tools.r8.errors.UnsupportedConstDynamicDiagnostic;
import com.android.tools.r8.errors.UnsupportedFeatureDiagnostic;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.origin.Origin;
import com.android.tools.r8.position.MethodPosition;
import java.util.Collection;

/* JADX INFO: renamed from: com.android.tools.r8.internal.z10, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3474z10 extends C10 {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    C3474z10(C0702m c0702m) {
        super(c0702m, "const-dynamic", null);
        boolean z = com.android.tools.r8.utils.A1.O1;
    }

    static void a(C0702m c0702m, C1507Xl c1507Xl) {
        if (c0702m.Y().J()) {
            return;
        }
        c1507Xl.a(new C3474z10(c0702m));
    }

    @Override // com.android.tools.r8.internal.C10
    final UnsupportedFeatureDiagnostic a(Origin origin, MethodPosition methodPosition) {
        return new UnsupportedConstDynamicDiagnostic(origin, methodPosition);
    }

    @Override // com.android.tools.r8.internal.C10
    final C3224ve a(B7 b7) {
        final Y6 y6D = b7.d();
        if (y6D == null) {
            return null;
        }
        return new C3005se((C3075te) new C3075te().a(new InterfaceC2864qe() { // from class: com.android.tools.r8.internal.z10$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.internal.InterfaceC2864qe
            public final Collection a(InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
                return this.f$0.a(y6D, interfaceC1374Ti, ac, g7, o5Var, c1230Ob, d7, c0722p1);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Collection a(Y6 y6, InterfaceC1374Ti interfaceC1374Ti, AC ac, G7 g7, com.android.tools.r8.graph.o5 o5Var, C1230Ob c1230Ob, D7 d7, C0722p1 c0722p1) {
        a(o5Var);
        C1507Xl c1507XlG = AbstractC1639am.g();
        a(c1230Ob, g7, c1507XlG);
        return C10.b(c1507XlG, y6.getType()).a();
    }
}
