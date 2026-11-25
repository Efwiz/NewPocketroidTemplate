package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Oz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C1255Oz implements InterfaceC3680a0 {
    private static final C1255Oz b = new C1255Oz(AbstractC1639am.i());
    static final /* synthetic */ boolean c = true;
    private final List a;

    private C1255Oz(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    static C1255Oz a(ArrayList arrayList, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        if (arrayList == null || arrayList.size() == 0) {
            return b;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            C2212iA c2212iAA = C2212iA.a((C3539zy) it.next(), c0722p1, o2);
            boolean z = c;
            if (!z && c2212iAA.u() != null) {
                throw new AssertionError();
            }
            if (!z && c2212iAA.v() != null) {
                throw new AssertionError();
            }
            if (!z && c2212iAA.w() != null) {
                throw new AssertionError();
            }
            c1507XlG.a(c2212iAA);
        }
        return new C1255Oz(c1507XlG.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(C2212iA c2212iA) {
        Objects.requireNonNull(c2212iA);
        return new C0879Bz$$ExternalSyntheticLambda2(c2212iA);
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.a, new Function() { // from class: com.android.tools.r8.internal.Oz$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C1255Oz.a((C2212iA) obj);
            }
        }, (Object) q0);
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        return AbstractC1745cA.a(c0702m, this.a, consumer, new C0878Bz$$ExternalSyntheticLambda10());
    }
}
