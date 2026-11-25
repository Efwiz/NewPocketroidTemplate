package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0680j;
import com.android.tools.r8.graph.C0702m;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.utils.C3879c3;
import com.android.tools.r8.utils.InterfaceC3905h3;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.hV, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C2159hV {
    private final C0702m a;
    final AN b = AN.d();
    final ConcurrentHashMap c = new ConcurrentHashMap();

    C2159hV(C0702m c0702m) {
        this.a = c0702m;
    }

    public final C2159hV a(ExecutorService executorService) {
        C0680j c0680j = (C0680j) this.a.f();
        Objects.requireNonNull(c0680j);
        C3879c3.a(new hV$$ExternalSyntheticLambda4(c0680j), new InterfaceC3905h3() { // from class: com.android.tools.r8.internal.hV$$ExternalSyntheticLambda5
            @Override // com.android.tools.r8.utils.InterfaceC3905h3
            public final void accept(Object obj) {
                this.f$0.a((com.android.tools.r8.graph.o5) obj);
            }
        }, executorService);
        return this;
    }

    public final C2239iV a() {
        final AN anC = AN.c();
        this.b.a(new BiConsumer() { // from class: com.android.tools.r8.internal.hV$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2159hV.a(anC, (com.android.tools.r8.graph.o5) obj, (Optional) obj2);
            }
        });
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.c.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.hV$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C2159hV.a(identityHashMap, (C0729q2) obj, (Optional) obj2);
            }
        });
        return new C2239iV(anC, identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final AN an, final com.android.tools.r8.graph.o5 o5Var, Optional optional) {
        optional.ifPresent(new Consumer() { // from class: com.android.tools.r8.internal.hV$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                an.a(o5Var, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(final Map map, final C0729q2 c0729q2, Optional optional) {
        optional.ifPresent(new Consumer() { // from class: com.android.tools.r8.internal.hV$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                map.put(c0729q2, (com.android.tools.r8.graph.o5) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(com.android.tools.r8.graph.o5 o5Var) {
        o5Var.a(new C2083gV(this, this.a, o5Var));
    }
}
