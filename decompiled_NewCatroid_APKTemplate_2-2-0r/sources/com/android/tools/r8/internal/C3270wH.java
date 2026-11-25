package com.android.tools.r8.internal;

import com.android.tools.r8.graph.C0702m;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.wH, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class C3270wH extends AbstractC3121uK {
    static final /* synthetic */ boolean b = true;
    private final Map a;

    C3270wH(IdentityHashMap identityHashMap) {
        boolean z = b;
        if (!z && identityHashMap.isEmpty()) {
            throw new AssertionError();
        }
        if (!z && !identityHashMap.values().stream().noneMatch(new wH$$ExternalSyntheticLambda0())) {
            throw new AssertionError();
        }
        this.a = identityHashMap;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final void a(BiConsumer biConsumer) {
        this.a.forEach(biConsumer);
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final boolean e() {
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final boolean equals(Object obj) {
        if (obj == null || C3270wH.class != obj.getClass()) {
            return false;
        }
        C3270wH c3270wH = (C3270wH) obj;
        if (this.a.size() != c3270wH.a.size()) {
            return false;
        }
        for (com.android.tools.r8.graph.Y0 y0 : this.a.keySet()) {
            if (!((B1) this.a.get(y0)).equals((B1) c3270wH.a.get(y0))) {
                return false;
            }
        }
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final int hashCode() {
        return this.a.hashCode();
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final B1 a(com.android.tools.r8.graph.T0 t0) {
        return (B1) this.a.getOrDefault(t0.getReference(), C2892r10.F());
    }

    @Override // com.android.tools.r8.internal.AbstractC3121uK
    public final AbstractC3121uK a(final C0702m c0702m, final AbstractC1476Wj abstractC1476Wj, final AbstractC1476Wj abstractC1476Wj2) {
        final IdentityHashMap identityHashMap = new IdentityHashMap();
        this.a.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.wH$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                C3270wH.a(identityHashMap, abstractC1476Wj, abstractC1476Wj2, c0702m, (com.android.tools.r8.graph.Y0) obj, (B1) obj2);
            }
        });
        return new C3270wH(identityHashMap);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void a(Map map, AbstractC1476Wj abstractC1476Wj, AbstractC1476Wj abstractC1476Wj2, C0702m c0702m, com.android.tools.r8.graph.Y0 y0, B1 b1) {
        map.put(abstractC1476Wj.c(abstractC1476Wj2, y0), b1.b(c0702m, abstractC1476Wj, abstractC1476Wj2));
    }
}
