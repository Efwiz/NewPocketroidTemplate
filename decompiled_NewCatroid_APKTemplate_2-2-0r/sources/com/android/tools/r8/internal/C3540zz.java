package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0722p1;
import com.android.tools.r8.shaking.InterfaceC3680a0;
import com.android.tools.r8.utils.C3888e1;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.zz, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3540zz implements InterfaceC3680a0 {
    private static final C3540zz b = new C3540zz(AbstractC1639am.i());
    private final List a;

    private C3540zz(AbstractC1639am abstractC1639am) {
        this.a = abstractC1639am;
    }

    static C3540zz a(C1653ay c1653ay, C0722p1 c0722p1, com.android.tools.r8.utils.O2 o2) {
        if (c1653ay == null) {
            return b;
        }
        ArrayList<C2036fy> arrayListB = c1653ay.b();
        if (arrayListB.isEmpty()) {
            return b;
        }
        C1507Xl c1507XlG = AbstractC1639am.g();
        for (C2036fy c2036fy : arrayListB) {
            c1507XlG.a(new C0943Dz(c2036fy.g(), c2036fy.f(), C0915Cz.a(c2036fy.e(), c0722p1, o2), C0915Cz.a(c2036fy.d(), c0722p1, o2)));
        }
        return new C3540zz(c1507XlG.a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Consumer a(final C0943Dz c0943Dz) {
        Objects.requireNonNull(c0943Dz);
        return new Consumer() { // from class: com.android.tools.r8.internal.zz$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                c0943Dz.a((com.android.tools.r8.graph.Q0) obj);
            }
        };
    }

    @Override // com.android.tools.r8.shaking.InterfaceC3680a0
    public final void a(com.android.tools.r8.graph.Q0 q0) {
        C3888e1.a(this.a, new Function() { // from class: com.android.tools.r8.internal.zz$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3540zz.a((C0943Dz) obj);
            }
        }, (Object) q0);
    }

    final boolean a(Consumer consumer, C0702m c0702m) {
        if (this == b) {
            return false;
        }
        return AbstractC1745cA.a(c0702m, this.a, ((C1653ay) AbstractC1745cA.a(consumer, new C1653ay())).b(), new com.android.tools.r8.utils.G3() { // from class: com.android.tools.r8.internal.zz$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.G3
            public final Object a(Object obj, Object obj2, Object obj3) {
                return Boolean.valueOf(((C0943Dz) obj).a((Consumer) obj2, (C0702m) obj3));
            }
        });
    }
}
