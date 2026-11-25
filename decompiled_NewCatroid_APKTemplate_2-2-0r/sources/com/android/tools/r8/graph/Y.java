package com.android.tools.r8.graph;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.AbstractC1915eM;
import com.android.tools.r8.internal.C1141Ks;
import com.android.tools.r8.internal.C1166Ls;
import com.android.tools.r8.internal.C2102gl;
import com.android.tools.r8.internal.WY;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Y extends WY {
    Y(C0684j2 c0684j2, AbstractC1915eM abstractC1915eM) {
        super(g(), ((AbstractC1915eM.c.a) AbstractC1915eM.c.x().a(0).a(c0684j2).a(abstractC1915eM)).a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void d(C2102gl c2102gl) {
        int i = C1166Ls.n;
        C1141Ks c1141Ks = new C1141Ks();
        o5 o5VarI = c2102gl.i();
        C0722p1 c0722p1A = c2102gl.o.a();
        int i2 = Z.f;
        c2102gl.a(((C1141Ks) ((C1141Ks) c1141Ks.a(c0722p1A.a(o5VarI.a().n1(), new C0767v2[0]))).a(AbstractC1639am.c(c2102gl.k()))).b());
    }

    private static AbstractC1639am g() {
        return AbstractC1639am.a(new Consumer() { // from class: com.android.tools.r8.graph.Y$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                Y.d((C2102gl) obj);
            }
        }, new Y$$ExternalSyntheticLambda1());
    }
}
