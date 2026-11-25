package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class ND extends MD {
    final /* synthetic */ OD a;

    ND(OD od) {
        this.a = od;
    }

    @Override // com.android.tools.r8.internal.MD
    final Map a() {
        return this.a;
    }

    @Override // java.lang.Iterable
    public final void forEach(Consumer consumer) {
        this.a.a(consumer);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return this.a.e();
    }

    @Override // java.util.Collection, java.lang.Iterable, java.util.Set
    public final Spliterator spliterator() {
        return this.a.k();
    }
}
