package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.qm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2872qm extends AbstractC1271Pl {
    private final AbstractC2178hm b;

    C2872qm(AbstractC2178hm abstractC2178hm) {
        this.b = abstractC2178hm;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        if (obj != null) {
            AbstractC2963s10 it = this.b.entrySet().iterator();
            while (it.hasNext()) {
                if (obj.equals(((Map.Entry) it.next()).getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final boolean e() {
        return true;
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return new C2727om(this);
    }

    @Override // java.lang.Iterable
    public final void forEach(final Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        this.b.forEach(new BiConsumer() { // from class: com.android.tools.r8.internal.qm$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                consumer.accept(obj2);
            }
        });
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new C2727om(this);
    }

    @Override // java.util.AbstractCollection, java.util.Collection
    public final int size() {
        return this.b.size();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return AbstractC3142ub.a(this.b.entrySet().spliterator(), new C2874qm$$ExternalSyntheticLambda1());
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    public final AbstractC1639am a() {
        return new C2803pm(this, this.b.entrySet().a());
    }
}
