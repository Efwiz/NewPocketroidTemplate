package com.android.tools.r8.shaking;

import com.android.tools.r8.graph.AbstractC0642e2;
import com.android.tools.r8.graph.AbstractC0741s0;
import com.android.tools.r8.graph.AbstractC0748t0;
import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.internal.AbstractC1154Lf;
import com.android.tools.r8.internal.C1910eI;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.shaking.k, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3743k {
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    public final C3743k a(boolean z, Consumer consumer) {
        if (z) {
            consumer.accept(this);
        }
        return this;
    }

    public final boolean b() {
        return this.a.isEmpty();
    }

    public final com.android.tools.r8.internal.Q3 a(AbstractC0748t0 abstractC0748t0) {
        com.android.tools.r8.internal.P3 p3 = (com.android.tools.r8.internal.P3) this.a.get(abstractC0748t0.getReference());
        return p3 != null ? p3.a() : com.android.tools.r8.internal.Q3.b();
    }

    private com.android.tools.r8.internal.P3 a(AbstractC0642e2 abstractC0642e2) {
        return (com.android.tools.r8.internal.P3) this.a.computeIfAbsent(abstractC0642e2, com.android.tools.r8.utils.Z1.a(new Supplier() { // from class: com.android.tools.r8.shaking.k$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return com.android.tools.r8.internal.Q3.a();
            }
        }));
    }

    public final C3743k a(AbstractC0741s0 abstractC0741s0, AbstractC1154Lf abstractC1154Lf) {
        a(abstractC0741s0.getReference()).a(abstractC1154Lf);
        return this;
    }

    public final void a(C0684j2 c0684j2, com.android.tools.r8.internal.Q3 q3) {
        a(c0684j2).a(q3);
    }

    public final C3743k a(com.android.tools.r8.graph.Y0 y0, C1910eI c1910eI) {
        a((AbstractC0642e2) y0).a(c1910eI);
        return this;
    }

    public final void a(AbstractC0741s0 abstractC0741s0, com.android.tools.r8.internal.B1 b1) {
        a(abstractC0741s0.getReference()).a(b1);
    }

    public final void a(com.android.tools.r8.graph.Y0 y0) {
        a((AbstractC0642e2) y0).b();
    }

    public final void a(AbstractC0741s0 abstractC0741s0) {
        a(abstractC0741s0.getReference()).b();
    }

    public final C3749l a() {
        return new C3749l(com.android.tools.r8.utils.Z1.a(new com.android.tools.r8.utils.F() { // from class: com.android.tools.r8.shaking.k$$ExternalSyntheticLambda0
            @Override // com.android.tools.r8.utils.F
            public final void forEach(BiConsumer biConsumer) {
                this.f$0.a(biConsumer);
            }
        }, this.a.size()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.shaking.k$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3743k.a(biConsumer, (AbstractC0642e2) obj, (com.android.tools.r8.internal.P3) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(BiConsumer biConsumer, AbstractC0642e2 abstractC0642e2, com.android.tools.r8.internal.P3 p3) {
        com.android.tools.r8.internal.Q3 q3A = p3.a();
        if (q3A.e()) {
            return;
        }
        biConsumer.accept(abstractC0642e2, q3A);
    }
}
