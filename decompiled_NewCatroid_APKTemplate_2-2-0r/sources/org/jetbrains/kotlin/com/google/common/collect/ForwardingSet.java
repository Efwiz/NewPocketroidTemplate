package org.jetbrains.kotlin.com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import org.jetbrains.kotlin.com.google.common.base.Preconditions;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ForwardingSet<E> extends ForwardingCollection<E> implements Set<E> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection, org.jetbrains.kotlin.com.google.common.collect.ForwardingObject
    public abstract Set<E> delegate();

    protected ForwardingSet() {
    }

    @Override // java.util.Collection, java.util.Set
    public boolean equals(Object obj) {
        return obj == this || delegate().equals(obj);
    }

    @Override // java.util.Collection, java.util.Set
    public int hashCode() {
        return delegate().hashCode();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ForwardingCollection
    protected boolean standardRemoveAll(Collection<?> collection) {
        return Sets.removeAllImpl(this, (Collection<?>) Preconditions.checkNotNull(collection));
    }

    protected boolean standardEquals(Object obj) {
        return Sets.equalsImpl(this, obj);
    }

    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
    }
}
