package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.shaking.C3733i;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dh, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C0923Dh {
    private final IdentityHashMap a = new IdentityHashMap();
    private final MR b = new MR();

    C0923Dh() {
    }

    public final void a(C0702m c0702m) {
        ((C3733i) c0702m.f()).z().a(new BiConsumer() { // from class: com.android.tools.r8.internal.Dh$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                this.f$0.a((C0729q2) obj, (Set) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0729q2 c0729q2, Set set) {
        Iterator it = set.iterator();
        while (it.hasNext()) {
            ((List) this.a.computeIfAbsent((com.android.tools.r8.graph.W0) it.next(), new Function() { // from class: com.android.tools.r8.internal.Dh$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return C0923Dh.a((com.android.tools.r8.graph.W0) obj);
                }
            })).add(c0729q2);
        }
        this.b.a(set.size(), c0729q2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ List a(com.android.tools.r8.graph.W0 w0) {
        return new ArrayList();
    }

    final void a(com.android.tools.r8.graph.o5 o5Var, Consumer consumer) {
        List<C0729q2> list = (List) this.a.get(o5Var.e());
        if (list != null) {
            for (C0729q2 c0729q2 : list) {
                int iD = this.b.d(c0729q2) - 1;
                if (iD > 0) {
                    this.b.a(iD, c0729q2);
                } else {
                    consumer.accept(c0729q2);
                }
            }
        }
    }
}
