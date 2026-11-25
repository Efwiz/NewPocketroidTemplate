package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class EH extends AbstractC1049Hn {
    static final /* synthetic */ boolean b = true;
    private final TreeMap a;

    EH(TreeMap treeMap) {
        boolean z = b;
        if (!z && treeMap.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !treeMap.values().stream().noneMatch(new Predicate() { // from class: com.android.tools.r8.internal.EH$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((InterfaceC0989Fn) obj).isUnknown();
            }
        })) {
            throw new AssertionError();
        }
        this.a = treeMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final AbstractC1049Hn a(final com.android.tools.r8.graph.proto.c cVar) {
        final C1017Gn c1017Gn = new C1017Gn();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.EH$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                EH.a(c1017Gn, cVar, (com.android.tools.r8.graph.Y0) obj, (InterfaceC0989Fn) obj2);
            }
        });
        return c1017Gn.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final boolean b() {
        return false;
    }

    public final String toString() {
        final ArrayList arrayList = new ArrayList();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.EH$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                EH.a(arrayList, (com.android.tools.r8.graph.Y0) obj, (InterfaceC0989Fn) obj2);
            }
        });
        return "NonTrivialInstanceFieldInitializationInfoCollection(" + com.android.tools.r8.utils.Y2.a("; ", arrayList) + Constant.AFTER_QUTO;
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final AbstractC1049Hn a(final C0702m c0702m, final DH dh, final AbstractC1476Wj abstractC1476Wj) {
        final C1017Gn c1017Gn = new C1017Gn();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.EH$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                EH.a(c1017Gn, dh, abstractC1476Wj, c0702m, (com.android.tools.r8.graph.Y0) obj, (InterfaceC0989Fn) obj2);
            }
        });
        return c1017Gn.a();
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final void a(final com.android.tools.r8.graph.Q0 q0, final BiConsumer biConsumer) {
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.EH$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                EH.a(q0, biConsumer, (com.android.tools.r8.graph.Y0) obj, (InterfaceC0989Fn) obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(com.android.tools.r8.graph.Q0 q0, BiConsumer biConsumer, com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        com.android.tools.r8.graph.T0 t0C = y0.c(q0.a(y0));
        if (t0C != null) {
            biConsumer.accept(t0C, interfaceC0989Fn);
        } else if (!b) {
            throw new AssertionError();
        }
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final void a(C0702m c0702m, BiConsumer biConsumer) {
        a((com.android.tools.r8.graph.Q0) c0702m, biConsumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC1049Hn
    public final InterfaceC0989Fn a(com.android.tools.r8.graph.T0 t0) {
        return (InterfaceC0989Fn) this.a.getOrDefault(t0.getReference(), C2351k10.b());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1017Gn c1017Gn, com.android.tools.r8.graph.proto.c cVar, com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        c1017Gn.a(y0, interfaceC0989Fn.a(cVar));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C1017Gn c1017Gn, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C0702m c0702m, com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        c1017Gn.a(abstractC1476Wj.c(abstractC1476Wj2, y0), interfaceC0989Fn.b(c0702m, abstractC1476Wj, abstractC1476Wj2));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, com.android.tools.r8.graph.Y0 y0, InterfaceC0989Fn interfaceC0989Fn) {
        list.add(y0.r0() + " -> " + interfaceC0989Fn);
    }
}
