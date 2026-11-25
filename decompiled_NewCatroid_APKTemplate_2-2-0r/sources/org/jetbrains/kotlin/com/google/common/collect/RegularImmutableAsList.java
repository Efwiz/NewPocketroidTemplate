package org.jetbrains.kotlin.com.google.common.collect;

import java.util.function.Consumer;

/* JADX INFO: loaded from: classes3.dex */
class RegularImmutableAsList<E> extends ImmutableAsList<E> {
    private final ImmutableCollection<E> delegate;
    private final ImmutableList<? extends E> delegateList;

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, ImmutableList<? extends E> immutableList) {
        this.delegate = immutableCollection;
        this.delegateList = immutableList;
    }

    RegularImmutableAsList(ImmutableCollection<E> immutableCollection, Object[] objArr) {
        this(immutableCollection, ImmutableList.asImmutableList(objArr));
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableAsList
    ImmutableCollection<E> delegateCollection() {
        return this.delegate;
    }

    ImmutableList<? extends E> delegateList() {
        return this.delegateList;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, java.util.List
    public UnmodifiableListIterator<E> listIterator(int i) {
        return this.delegateList.listIterator(i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, java.lang.Iterable
    public void forEach(Consumer<? super E> consumer) {
        this.delegateList.forEach(consumer);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int copyIntoArray(Object[] objArr, int i) {
        return this.delegateList.copyIntoArray(objArr, i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    Object[] internalArray() {
        return this.delegateList.internalArray();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int internalArrayStart() {
        return this.delegateList.internalArrayStart();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    int internalArrayEnd() {
        return this.delegateList.internalArrayEnd();
    }

    @Override // java.util.List
    public E get(int i) {
        return this.delegateList.get(i);
    }
}
