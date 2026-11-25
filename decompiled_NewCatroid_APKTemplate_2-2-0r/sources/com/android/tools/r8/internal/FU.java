package com.android.tools.r8.internal;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class FU extends NU {
    final /* synthetic */ Set a;
    final /* synthetic */ Set b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FU(Set set, Set set2) {
        super(0);
        this.a = set;
        this.b = set2;
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.a.contains(obj) && this.b.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean containsAll(Collection collection) {
        return this.a.containsAll(collection) && this.b.containsAll(collection);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return Collections.disjoint(this.b, this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new EU(this);
    }

    @Override // java.util.Collection
    public final Stream parallelStream() {
        Stream streamParallelStream = this.a.parallelStream();
        Set set = this.b;
        Objects.requireNonNull(set);
        return streamParallelStream.filter(new FU$$ExternalSyntheticLambda0(set));
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Iterator it = this.a.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (this.b.contains(it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // java.util.Collection
    public final Stream stream() {
        Stream stream = this.a.stream();
        Set set = this.b;
        Objects.requireNonNull(set);
        return stream.filter(new FU$$ExternalSyntheticLambda0(set));
    }
}
