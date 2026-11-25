package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.tA, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C3042tA implements InterfaceC3680a0 {
    private static final List e = AbstractC1639am.i();
    final String a;
    final int b;
    final C2759pA c;
    final C2759pA d;

    private C3042tA(int i, String str, C2759pA c2759pA, C2759pA c2759pA2) {
        this.a = str;
        this.b = i;
        this.c = c2759pA;
        this.d = c2759pA2;
    }

    static C3042tA a(C1284Py c1284Py, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        if (c1284Py == null) {
            return null;
        }
        return new C3042tA(c1284Py.b(), c1284Py.c(), C2759pA.a(c0722p1, o2, c1284Py.d()), C2759pA.a(c0722p1, o2, c1284Py.e()));
    }

    static AbstractC1639am a(List list, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        if (list.isEmpty()) {
            return (AbstractC1639am) e;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            c1507XlG.a(a((C1284Py) it.next(), c0722p1, o2));
        }
        return c1507XlG.a();
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        final C1284Py c1284Py = (C1284Py) AbstractC1745cA.a(consumer, new C1284Py(this.b, this.a));
        return AbstractC1745cA.a(c0702m, this.d, new Consumer() { // from class: com.android.tools.r8.internal.tA$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1284Py.b((C0942Dy) obj);
            }
        }, new Cz$$ExternalSyntheticLambda4()) | this.c.a(new Consumer() { // from class: com.android.tools.r8.internal.tA$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c1284Py.a((C0942Dy) obj);
            }
        }, c0702m);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        this.c.a(q0);
        C2759pA c2759pA = this.d;
        if (c2759pA != null) {
            c2759pA.a(q0);
        }
    }
}
