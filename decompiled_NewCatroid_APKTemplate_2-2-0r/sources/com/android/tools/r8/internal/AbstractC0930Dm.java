package com.android.tools.r8.internal;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;

/* JADX INFO: renamed from: com.android.tools.r8.internal.Dm, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
abstract class AbstractC0930Dm extends AbstractC1048Hm {
    AbstractC0930Dm() {
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    final int a(int i, Object[] objArr) {
        return a().a(i, objArr);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl
    /* JADX INFO: renamed from: f */
    public final AbstractC2963s10 iterator() {
        return a().iterator();
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        int i = AbstractC3123uM.a;
        consumer.getClass();
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            consumer.accept(get(i2));
        }
    }

    abstract Object get(int i);

    @Override // com.android.tools.r8.internal.AbstractC1048Hm
    final AbstractC1639am i() {
        return new C0902Cm(this);
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return a().iterator();
    }

    @Override // com.android.tools.r8.internal.AbstractC1271Pl, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return AbstractC3142ub.a(size(), 1297, new IntFunction() { // from class: com.android.tools.r8.internal.Dm$$ExternalSyntheticLambda0
            @Override // java.util.function.IntFunction
            public final Object apply(int i) {
                return this.f$0.get(i);
            }
        }, (Comparator) null);
    }
}
