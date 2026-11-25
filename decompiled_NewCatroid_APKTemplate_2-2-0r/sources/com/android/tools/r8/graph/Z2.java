package com.android.tools.r8.graph;

import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class Z2 {
    static final /* synthetic */ boolean b = true;
    private final Q0 a;

    public Z2(Q0 q0) {
        this.a = q0;
    }

    public final AbstractC0677i3 a(C0767v2 c0767v2, final Y0 y0) {
        final C0596a3 c0596a3O = AbstractC0677i3.o();
        this.a.f(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.Z2$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(y0, c0596a3O, (AbstractC0727q0) obj);
            }
        });
        return c0596a3O.a(AbstractC0677i3.p());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(Y0 y0, C0596a3 c0596a3, AbstractC0727q0 abstractC0727q0) {
        a(abstractC0727q0, y0, abstractC0727q0, com.android.tools.r8.utils.T2.b(8), c0596a3);
    }

    public final AbstractC0677i3 a(AbstractC0727q0 abstractC0727q0, Y0 y0) {
        if (!b && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        C0596a3 c0596a3O = AbstractC0677i3.o();
        a(abstractC0727q0, y0, abstractC0727q0, com.android.tools.r8.utils.T2.b(8), c0596a3O);
        return c0596a3O.a(AbstractC0677i3.p());
    }

    private void a(final AbstractC0727q0 abstractC0727q0, final Y0 y0, final AbstractC0727q0 abstractC0727q1, final Set set, final C0596a3 c0596a3) {
        if (!b && abstractC0727q0 == null) {
            throw new AssertionError();
        }
        T0 t0A = abstractC0727q0.j.a(y0);
        if (t0A != null) {
            c0596a3.a(AbstractC0677i3.a(abstractC0727q1, abstractC0727q0, t0A));
            return;
        }
        AbstractC0677i3 abstractC0677i3A = a(abstractC0727q1, abstractC0727q0, y0, set);
        if (abstractC0677i3A != null) {
            c0596a3.a(abstractC0677i3A);
            return;
        }
        C0767v2 c0767v2 = abstractC0727q0.f;
        if (c0767v2 != null) {
            this.a.f(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.Z2$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    this.f$0.a(abstractC0727q0, y0, abstractC0727q1, set, c0596a3, (AbstractC0727q0) obj);
                }
            });
        } else {
            c0596a3.a((AbstractC0677i3) AbstractC0677i3.p());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(AbstractC0727q0 abstractC0727q0, Y0 y0, AbstractC0727q0 abstractC0727q1, Set set, C0596a3 c0596a3, AbstractC0727q0 abstractC0727q2) {
        if (!abstractC0727q0.f0() || abstractC0727q2.f0()) {
            a(abstractC0727q2, y0, abstractC0727q1, set, c0596a3);
        }
    }

    private AbstractC0677i3 a(final AbstractC0727q0 abstractC0727q0, final AbstractC0727q0 abstractC0727q1, final Y0 y0, final Set set) {
        for (C0767v2 c0767v2 : abstractC0727q1.g.a) {
            if (set.add(c0767v2)) {
                final C0596a3 c0596a3O = AbstractC0677i3.o();
                this.a.f(c0767v2).b(new Consumer() { // from class: com.android.tools.r8.graph.Z2$$ExternalSyntheticLambda2
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        this.f$0.a(abstractC0727q1, abstractC0727q0, y0, set, c0596a3O, (AbstractC0727q0) obj);
                    }
                });
                AbstractC0677i3 abstractC0677i3A = c0596a3O.a((C0611b3) null);
                if (abstractC0677i3A != null) {
                    return abstractC0677i3A;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(AbstractC0727q0 abstractC0727q0, AbstractC0727q0 abstractC0727q1, Y0 y0, Set set, C0596a3 c0596a3, AbstractC0727q0 abstractC0727q2) {
        AbstractC0677i3 abstractC0677i3A;
        if (!abstractC0727q0.f0() || abstractC0727q2.f0()) {
            T0 t0A = abstractC0727q2.j.a(y0);
            if (t0A != null) {
                abstractC0677i3A = AbstractC0677i3.a(abstractC0727q1, abstractC0727q2, t0A);
            } else {
                abstractC0677i3A = a(abstractC0727q1, abstractC0727q2, y0, set);
            }
            if (abstractC0677i3A != null) {
                c0596a3.a(abstractC0677i3A);
            }
        }
    }
}
