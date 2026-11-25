package com.android.tools.r8.naming;

import com.android.tools.r8.graph.AbstractC0727q0;
import com.android.tools.r8.graph.B5;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0754u0;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.shaking.C3733i;
import com.android.tools.r8.utils.T2;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.naming.j0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public abstract class AbstractC3633j0 {
    public static B5 a(final C0702m c0702m) {
        final Set setA = T2.a((Iterable) c0702m.e().d());
        ((C3733i) c0702m.f()).z().a(new Consumer() { // from class: com.android.tools.r8.naming.j0$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AbstractC3633j0.a(c0702m, setA, (C0767v2) obj);
            }
        });
        C3733i c3733i = (C3733i) c0702m.f();
        Objects.requireNonNull(setA);
        c3733i.b(new Consumer() { // from class: com.android.tools.r8.naming.j0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                setA.add((C0754u0) obj);
            }
        });
        return B5.a(setA, c0702m);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0702m c0702m, Set set, C0767v2 c0767v2) {
        AbstractC0727q0 abstractC0727q0A = c0702m.a(c0767v2);
        if (abstractC0727q0A != null) {
            set.add(abstractC0727q0A);
        }
    }
}
