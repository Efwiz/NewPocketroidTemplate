package com.android.tools.r8.graph;

import com.android.tools.r8.internal.C3405y10;
import com.android.tools.r8.internal.CM;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.graph.e0, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C0640e0 extends AbstractC0594a1 {
    private final C0729q2 a;
    private final ArrayList b = new ArrayList();
    private final ArrayList c = new ArrayList();
    private final ArrayList d = new ArrayList();
    private final boolean e;

    public C0640e0(C0729q2 c0729q2) {
        this.a = c0729q2;
        this.e = c0729q2.d2();
        c0729q2.Y().a(new Consumer() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((W0) obj);
            }
        });
        for (T0 t0 : c0729q2.W0()) {
            if (!t0.s0().isEmpty()) {
                this.d.add(t0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(W0 w0) {
        if (!w0.s0().isEmpty()) {
            this.b.add(w0);
        }
        if (w0.h.isEmpty()) {
            return;
        }
        this.c.add(w0);
    }

    public final ArrayList b(final com.android.tools.r8.utils.structural.a aVar) {
        this.b.sort(new Comparator() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0640e0.a(aVar, (W0) obj, (W0) obj2);
            }
        });
        return this.b;
    }

    public final ArrayList c(final com.android.tools.r8.utils.structural.a aVar) {
        this.c.sort(new Comparator() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda4
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0640e0.b(aVar, (W0) obj, (W0) obj2);
            }
        });
        return this.c;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof C0640e0)) {
            return false;
        }
        if (!this.e) {
            return super.equals(obj);
        }
        C0640e0 c0640e0 = (C0640e0) obj;
        if (c0640e0.a.d2()) {
            return this.a.s0().equals(c0640e0.a.s0());
        }
        return false;
    }

    public final int hashCode() {
        return this.e ? this.a.s0().hashCode() : super.hashCode();
    }

    public final C0658g0 s0() {
        return this.a.s0();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int b(com.android.tools.r8.utils.structural.a aVar, W0 w0, W0 w1) {
        C0684j2 reference = w0.getReference();
        C0684j2 reference2 = w1.getReference();
        reference.getClass();
        return aVar.a(reference, reference2);
    }

    public final void a(final Consumer consumer, final Consumer consumer2, final Consumer consumer3) {
        C0658g0 c0658g0S0 = this.a.s0();
        consumer2.accept(c0658g0S0);
        for (C0633d0 c0633d0 : c0658g0S0.c) {
            consumer.accept(c0633d0);
        }
        C0729q2 c0729q2 = this.a;
        Consumer consumer4 = new Consumer() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, consumer2, (T0) obj);
            }
        };
        c0729q2.getClass();
        c0729q2.c(consumer4, CM.b());
        this.a.f(new Consumer() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a(consumer, consumer2, consumer3, (W0) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Consumer consumer, Consumer consumer2, T0 t0) {
        C0658g0 c0658g0S0 = t0.s0();
        consumer2.accept(c0658g0S0);
        for (C0633d0 c0633d0 : c0658g0S0.c) {
            consumer.accept(c0633d0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Consumer consumer, Consumer consumer2, Consumer consumer3, W0 w0) {
        C0658g0 c0658g0S0 = w0.s0();
        consumer2.accept(c0658g0S0);
        for (C0633d0 c0633d0 : c0658g0S0.c) {
            consumer.accept(c0633d0);
        }
        C0663g5 c0663g5J1 = w0.j1();
        consumer3.accept(c0663g5J1);
        for (C0658g0 c0658g0 : c0663g5J1.u0()) {
            consumer2.accept(c0658g0);
            for (C0633d0 c0633d1 : c0658g0.c) {
                consumer.accept(c0633d1);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(com.android.tools.r8.utils.structural.a aVar, W0 w0, W0 w1) {
        C0684j2 reference = w0.getReference();
        C0684j2 reference2 = w1.getReference();
        reference.getClass();
        return aVar.a(reference, reference2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int a(com.android.tools.r8.utils.structural.a aVar, T0 t0, T0 t1) {
        Y0 reference = t0.getReference();
        Y0 reference2 = t1.getReference();
        reference.getClass();
        return aVar.a(reference, reference2);
    }

    public final ArrayList a(final com.android.tools.r8.utils.structural.a aVar) {
        this.d.sort(new Comparator() { // from class: com.android.tools.r8.graph.e0$$ExternalSyntheticLambda5
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return C0640e0.a(aVar, (T0) obj, (T0) obj2);
            }
        });
        return this.d;
    }

    @Override // com.android.tools.r8.graph.AbstractC0594a1
    public final void a(com.android.tools.r8.dex.V v) {
        throw new C3405y10();
    }
}
