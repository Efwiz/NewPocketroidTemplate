package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.InterfaceC3877c1;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.mi, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2558mi extends SF {
    static final /* synthetic */ boolean d = true;
    private final C0702m b;
    private final EnumC0928Dk c;

    public C2558mi(C0702m c0702m, EnumC0928Dk enumC0928Dk) {
        this.b = c0702m;
        this.c = enumC0928Dk;
    }

    private static void b(final C2688oE c2688oE) {
        boolean z = d;
        if (!z && !c2688oE.j()) {
            throw new AssertionError();
        }
        if (!z && !c2688oE.b().contains(c2688oE.f())) {
            throw new AssertionError();
        }
        if (!z && !c2688oE.i()) {
            throw new AssertionError();
        }
        final Set setA = com.android.tools.r8.utils.T2.a(new InterfaceC3877c1() { // from class: com.android.tools.r8.internal.mi$$ExternalSyntheticLambda1
            @Override // com.android.tools.r8.utils.InterfaceC3877c1
            public final void forEach(Consumer consumer) {
                C2558mi.a(c2688oE, consumer);
            }
        });
        c2688oE.c().forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.mi$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2558mi.a(setA, (com.android.tools.r8.graph.T0) obj, (com.android.tools.r8.graph.T0) obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.SF
    public final Collection a(C2688oE c2688oE) {
        if (!this.b.q()) {
            boolean z = d;
            if (!z && c2688oE.j()) {
                throw new AssertionError();
            }
            if (!z && c2688oE.i()) {
                throw new AssertionError();
            }
            c2688oE.b(this.b);
            c2688oE.a(new C1371Tf());
        } else if (this.c.b() || c2688oE.l()) {
            c2688oE.b(this.b);
            c2688oE.a(this.b.h0());
        } else if (!d) {
            b(c2688oE);
        }
        LinkedList linkedList = new LinkedList();
        linkedList.add(c2688oE);
        return linkedList;
    }

    @Override // com.android.tools.r8.internal.WL
    public final String e() {
        return "FinalizeMergeGroup";
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Consumer consumer, C0729q2 c0729q2) {
        consumer.accept(c0729q2.getType());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C2688oE c2688oE, final Consumer consumer) {
        c2688oE.forEach(new Consumer() { // from class: com.android.tools.r8.internal.mi$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2558mi.a(consumer, (C0729q2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, com.android.tools.r8.graph.T0 t0, com.android.tools.r8.graph.T0 t1) {
        boolean z = d;
        if (!z && !set.contains(t0.J0())) {
            throw new AssertionError();
        }
        if (!z && !set.contains(t1.J0())) {
            throw new AssertionError();
        }
    }
}
