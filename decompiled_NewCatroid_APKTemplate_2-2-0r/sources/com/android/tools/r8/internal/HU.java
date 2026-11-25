package com.android.tools.r8.internal;

import java.util.Iterator;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class HU extends NU {
    final /* synthetic */ Set a;
    final /* synthetic */ Set b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    HU(Set set, Set set2) {
        super(0);
        this.a = set;
        this.b = set2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean a(Set set, Object obj) {
        return !set.contains(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean b(Set set, Object obj) {
        return !set.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean contains(Object obj) {
        return this.a.contains(obj) && !this.b.contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final boolean isEmpty() {
        return this.b.containsAll(this.a);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
    public final Iterator iterator() {
        return new GU(this);
    }

    @Override // java.util.Collection
    public final Stream parallelStream() {
        Stream streamParallelStream = this.a.parallelStream();
        final Set set = this.b;
        return streamParallelStream.filter(new Predicate() { // from class: com.android.tools.r8.internal.HU$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return HU.a(set, obj);
            }
        });
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public final int size() {
        Iterator it = this.a.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (!this.b.contains(it.next())) {
                i++;
            }
        }
        return i;
    }

    @Override // java.util.Collection
    public final Stream stream() {
        Stream stream = this.a.stream();
        final Set set = this.b;
        return stream.filter(new Predicate() { // from class: com.android.tools.r8.internal.HU$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return HU.b(set, obj);
            }
        });
    }
}
