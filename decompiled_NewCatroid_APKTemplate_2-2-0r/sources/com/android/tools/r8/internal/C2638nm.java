package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.nm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2638nm extends AbstractC1791cn {
    private final AbstractC2178hm d;

    C2638nm(AbstractC2178hm abstractC2178hm) {
        this.d = abstractC2178hm;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.d.containsKey(obj);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn, com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return this.d.p();
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn, java.lang.Iterable
    public final void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        this.d.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.nm$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(obj);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn
    final Object get(int i) {
        return ((Map.Entry) this.d.entrySet().a().get(i)).getKey();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.d.p();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        return this.d.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1791cn, com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return this.d.r();
    }
}
