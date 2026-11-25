package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0753u;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class Y9 {
    private int a;
    private int b;
    final /* synthetic */ C1622aa c;

    private Y9(C1622aa c1622aa) {
        this.c = c1622aa;
        this.a = 0;
        this.b = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public ArrayList a(final AbstractC1915eM.c cVar) {
        final ArrayList arrayList = new ArrayList();
        this.c.a.forEach(new Consumer() { // from class: com.android.tools.r8.internal.Y9$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(arrayList, cVar, (com.android.tools.r8.graph.o5) obj);
            }
        });
        arrayList.add(new C3186v8());
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(List list, AbstractC1915eM abstractC1915eM, com.android.tools.r8.graph.o5 o5Var) {
        C0753u c0753uK = o5Var.e().a1().K();
        this.a = Integer.max(this.a, c0753uK.L0());
        this.b = Integer.max(this.b, c0753uK.K0());
        M7 m7 = new M7();
        boolean z = false;
        int i = 1;
        for (B7 b7 : c0753uK.I0()) {
            b7.getClass();
            if (b7 instanceof C2051g8) {
                C2051g8 c2051g8S = b7.s();
                list.add(new C2051g8(c2051g8S.T(), c2051g8S.U().a(abstractC1915eM)));
            } else if (b7.Q()) {
                if (c0753uK.I0().size() != i) {
                    list.add(new C2899r7(m7));
                    z = true;
                }
            } else {
                list.add(b7);
            }
            i++;
        }
        if (z) {
            list.add(m7);
        }
    }
}
