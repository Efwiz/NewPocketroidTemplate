package com.android.tools.r8.synthesis;

import com.android.tools.r8.graph.C0767v2;
import com.android.tools.r8.graph.InterfaceC0694k5;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class v implements InterfaceC3862u {
    private final ConcurrentHashMap a;

    private v() {
        this.a = new ConcurrentHashMap();
    }

    @Override // com.android.tools.r8.synthesis.InterfaceC3862u
    public final void a(C0767v2 c0767v2, Collection collection) {
        final Set set = (Set) this.a.computeIfAbsent(c0767v2, new Function() { // from class: com.android.tools.r8.synthesis.v$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return v.a((C0767v2) obj);
            }
        });
        collection.forEach(new Consumer() { // from class: com.android.tools.r8.synthesis.v$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                v.a(set, (InterfaceC0694k5) obj);
            }
        });
    }

    @Override // com.android.tools.r8.synthesis.InterfaceC3862u
    public final void forEach(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    @Override // com.android.tools.r8.synthesis.InterfaceC3862u
    public final boolean isEmpty() {
        return this.a.isEmpty();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ Set a(C0767v2 c0767v2) {
        return ConcurrentHashMap.newKeySet();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Set set, InterfaceC0694k5 interfaceC0694k5) {
        set.add(interfaceC0694k5.C());
    }
}
