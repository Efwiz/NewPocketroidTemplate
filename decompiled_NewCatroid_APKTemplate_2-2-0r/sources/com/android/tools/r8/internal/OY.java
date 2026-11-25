package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class OY {
    static final /* synthetic */ boolean b = true;
    private final C0702m a;

    OY(C0702m c0702m) {
        this.a = c0702m;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.synthesis.P p) {
    }

    private C0729q2 a(C0729q2 c0729q2, com.android.tools.r8.synthesis.C c) {
        return this.a.D().a(c, c0729q2, this.a, new Consumer() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OY.a((com.android.tools.r8.synthesis.P) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 b(C0729q2 c0729q2) {
        return a(c0729q2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.s;
            }
        }).getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 c(C0729q2 c0729q2) {
        return a(c0729q2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.t;
            }
        }).getType();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ C0767v2 a(C0729q2 c0729q2) {
        return a(c0729q2, new com.android.tools.r8.synthesis.C() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda6
            @Override // com.android.tools.r8.synthesis.C
            public final com.android.tools.r8.synthesis.L.b a(com.android.tools.r8.synthesis.L l) {
                return l.r;
            }
        }).getType();
    }

    public final PY a(LinkedList linkedList) {
        C2688oE c2688oE = (C2688oE) linkedList.iterator().next();
        if (!b && !c2688oE.j()) {
            throw new AssertionError();
        }
        final C0729q2 c0729q2F = c2688oE.f();
        ArrayList arrayList = new ArrayList();
        arrayList.add(BY.a(new InterfaceC3442yY() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.a(c0729q2F);
            }
        }));
        arrayList.add(BY.a(new InterfaceC3442yY() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.b(c0729q2F);
            }
        }));
        arrayList.add(BY.a(new InterfaceC3442yY() { // from class: com.android.tools.r8.internal.OY$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                return this.f$0.c(c0729q2F);
            }
        }));
        return new PY(arrayList);
    }
}
