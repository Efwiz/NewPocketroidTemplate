package com.android.tools.r8.graph;

import java.util.List;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class K4 extends J4 {
    static final /* synthetic */ boolean e = true;
    private final AbstractC0727q0 d;

    public K4(AbstractC0727q0 abstractC0727q0, List list) {
        super(list);
        this.d = abstractC0727q0;
    }

    private boolean A() {
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(true);
        a(new Consumer() { // from class: com.android.tools.r8.graph.K4$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                K4.a((C0767v2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.K4$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(k, (W0) obj);
            }
        });
        return k.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2) {
    }

    @Override // com.android.tools.r8.graph.I4
    public final boolean b(AbstractC0727q0 abstractC0727q0, C0702m c0702m, C0680j c0680j) {
        if (!z()) {
            return true;
        }
        if (a(abstractC0727q0, c0702m, c0680j)) {
            return false;
        }
        if (e || A()) {
            return true;
        }
        throw new AssertionError();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(final C0702m c0702m, final com.android.tools.r8.utils.K k, final AbstractC0727q0 abstractC0727q0, final C0680j c0680j, C0767v2 c0767v2) {
        c0702m.f().f(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.K4$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                K4.a(k, abstractC0727q0, c0680j, c0702m, (AbstractC0727q0) obj);
            }
        });
    }

    @Override // com.android.tools.r8.graph.I4
    public final boolean a(final AbstractC0727q0 abstractC0727q0, final C0702m c0702m, final C0680j c0680j) {
        if (!z()) {
            return false;
        }
        final com.android.tools.r8.utils.K k = new com.android.tools.r8.utils.K(false);
        a(new Consumer() { // from class: com.android.tools.r8.graph.K4$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                K4.a(c0702m, k, abstractC0727q0, c0680j, (C0767v2) obj);
            }
        }, new Consumer() { // from class: com.android.tools.r8.graph.K4$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(c0702m, k, abstractC0727q0, c0680j, (W0) obj);
            }
        });
        return k.a();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.utils.K k, AbstractC0727q0 abstractC0727q0, C0680j c0680j, C0702m c0702m, AbstractC0727q0 abstractC0727q1) {
        k.b(AbstractC0639e.a(abstractC0727q1, abstractC0727q0, c0680j.p(), c0702m.Y(), c0702m.C(), c0702m.D()).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(C0702m c0702m, com.android.tools.r8.utils.K k, AbstractC0727q0 abstractC0727q0, C0680j c0680j, W0 w0) {
        k.b(AbstractC0639e.a(AbstractC0748t0.a(c0702m.c(w0.J0()), w0), this.d, abstractC0727q0, c0702m, c0680j).b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(com.android.tools.r8.utils.K k, W0 w0) {
        k.a(w0.J0() != this.d.getType());
    }
}
