package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Spliterator;
import java.util.Spliterators;

/* JADX INFO: loaded from: classes3.dex */
final class RegularImmutableSet<E> extends ImmutableSet<E> {
    static final RegularImmutableSet<Object> EMPTY = new RegularImmutableSet<>(new Object[0], 0, null, 0);
    private final transient Object[] elements;
    private final transient int hashCode;
    private final transient int mask;
    final transient Object[] table;

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int internalArrayStart() {
        return 0;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet
    boolean isHashCodeFast() {
        return true;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return false;
    }

    RegularImmutableSet(Object[] objArr, int i, Object[] objArr2, int i2) {
        this.elements = objArr;
        this.table = objArr2;
        this.mask = i2;
        this.hashCode = i;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        Object[] objArr = this.table;
        if (obj == null || objArr == null) {
            return false;
        }
        int iSmearedHash = Hashing.smearedHash(obj);
        while (true) {
            int i = iSmearedHash & this.mask;
            Object obj2 = objArr[i];
            if (obj2 == null) {
                return false;
            }
            if (obj2.equals(obj)) {
                return true;
            }
            iSmearedHash = i + 1;
        }
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
    public int size() {
        return this.elements.length;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set, org.jetbrains.kotlin.com.google.common.collect.SortedIterable, java.util.NavigableSet
    public UnmodifiableIterator<E> iterator() {
        return Iterators.forArray(this.elements);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.Collection, java.lang.Iterable
    public Spliterator<E> spliterator() {
        return Spliterators.spliterator(this.elements, 1297);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    Object[] internalArray() {
        return this.elements;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int internalArrayEnd() {
        return this.elements.length;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        Object[] objArr2 = this.elements;
        System.arraycopy(objArr2, 0, objArr, i, objArr2.length);
        return i + this.elements.length;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet
    ImmutableList<E> createAsList() {
        return this.table == null ? ImmutableList.of() : new RegularImmutableAsList(this, this.elements);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableSet, java.util.Collection, java.util.Set
    public int hashCode() {
        return this.hashCode;
    }
}
