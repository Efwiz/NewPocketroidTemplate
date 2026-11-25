package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0684j2;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.W0$$ExternalSyntheticLambda12;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3890e3;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* JADX INFO: renamed from: com.android.tools.r8.internal.uN, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3124uN {
    static final /* synthetic */ boolean b = true;
    private final ConcurrentHashMap a = new ConcurrentHashMap();

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ com.android.tools.r8.graph.o5 b(Supplier supplier, C0684j2 c0684j2) {
        com.android.tools.r8.graph.o5 o5Var = (com.android.tools.r8.graph.o5) supplier.get();
        if (b || o5Var.a().f(o5Var.getReference()) == null) {
            return o5Var;
        }
        throw new AssertionError();
    }

    public final com.android.tools.r8.graph.o5 a(final Supplier supplier, C0684j2 c0684j2) {
        return (com.android.tools.r8.graph.o5) ((Map) this.a.computeIfAbsent(c0684j2.B0(), new Function() { // from class: com.android.tools.r8.internal.uN$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3124uN.a((C0767v2) obj);
            }
        })).computeIfAbsent(c0684j2, new Function() { // from class: com.android.tools.r8.internal.uN$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return C3124uN.b(supplier, (C0684j2) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Map a(C0767v2 c0767v2) {
        return new ConcurrentHashMap();
    }

    public final void a(ExecutorService executorService) {
        C3879c3.a(this.a, new InterfaceC3890e3() { // from class: com.android.tools.r8.internal.uN$$ExternalSyntheticLambda2
            @Override // com.android.tools.r8.utils.InterfaceC3890e3
            public final void accept(Object obj, Object obj2) {
                C3124uN.a((C0767v2) obj, (Map) obj2);
            }
        }, executorService);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(C0767v2 c0767v2, Map map) {
        C0729q2 c0729q2A = ((com.android.tools.r8.graph.o5) map.values().iterator().next()).a();
        final ArrayList arrayList = new ArrayList();
        map.values().forEach(new Consumer() { // from class: com.android.tools.r8.internal.uN$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C3124uN.a(arrayList, (com.android.tools.r8.graph.o5) obj);
            }
        });
        arrayList.sort(Comparator.comparing(new W0$$ExternalSyntheticLambda12()));
        c0729q2A.Y().a(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(List list, com.android.tools.r8.graph.o5 o5Var) {
        list.add(o5Var.e());
    }
}
