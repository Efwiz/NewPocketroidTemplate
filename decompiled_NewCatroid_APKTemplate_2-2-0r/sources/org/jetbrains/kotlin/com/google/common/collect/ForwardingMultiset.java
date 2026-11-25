package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.jetbrains.kotlin.com.google.common.base.Objects;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingMultiset<E> extends ForwardingCollection<E> implements Multiset<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection, org.jetbrains.kotlin.com.google.common.collect.ForwardingObject
    public abstract Multiset<E> delegate();

    protected ForwardingMultiset() {
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public int count(Object obj) {
        return delegate().count(obj);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public int add(E e, int i) {
        return delegate().add(e, i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public int remove(Object obj, int i) {
        return delegate().remove(obj, i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public Set<E> elementSet() {
        return delegate().elementSet();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public Set<Multiset.Entry<E>> entrySet() {
        return delegate().entrySet();
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.com.google.common.collect.Multiset
    public boolean equals(Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, org.jetbrains.kotlin.com.google.common.collect.Multiset
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public int setCount(E e, int i) {
        return delegate().setCount(e, i);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.Multiset
    public boolean setCount(E e, int i, int i2) {
        return delegate().setCount(e, i, i2);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardContains(Object obj) {
        return count(obj) > 0;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected void standardClear() {
        Iterators.clear(entrySet().iterator());
    }

    protected int standardCount(Object obj) {
        for (Multiset.Entry<E> entry : entrySet()) {
            if (Objects.equal(entry.getElement(), obj)) {
                return entry.getCount();
            }
        }
        return 0;
    }

    protected boolean standardAdd(E e) {
        add(e, 1);
        return true;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardAddAll(Collection<? extends E> collection) {
        return Multisets.addAllImpl(this, collection);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardRemove(Object obj) {
        return remove(obj, 1) > 0;
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Multisets.removeAllImpl(this, collection);
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardRetainAll(Collection<?> collection) {
        return Multisets.retainAllImpl(this, collection);
    }

    protected int standardSetCount(E e, int i) {
        return Multisets.setCountImpl(this, e, i);
    }

    protected boolean standardSetCount(E e, int i, int i2) {
        return Multisets.setCountImpl(this, e, i, i2);
    }

    protected Iterator<E> standardIterator() {
        return Multisets.iteratorImpl(this);
    }

    protected int standardSize() {
        return Multisets.linearTimeSizeImpl(this);
    }

    protected boolean standardEquals(Object obj) {
        return Multisets.equalsImpl(this, obj);
    }

    protected int standardHashCode() {
        return entrySet().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected String standardToString() {
        return entrySet().toString();
    }
}
