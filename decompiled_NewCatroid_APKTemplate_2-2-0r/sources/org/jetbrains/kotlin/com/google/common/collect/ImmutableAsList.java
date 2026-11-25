package org.jetbrains.kotlin.com.google.common.collect;

import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;

/* JADX INFO: loaded from: classes3.dex */
abstract class ImmutableAsList<E> extends ImmutableList<E> {
    abstract ImmutableCollection<E> delegateCollection();

    ImmutableAsList() {
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection, java.util.AbstractCollection, java.util.Collection, java.util.Set
    public boolean contains(Object obj) {
        return delegateCollection().contains(obj);
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public int size() {
        return delegateCollection().size();
    }

    @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
    public boolean isEmpty() {
        return delegateCollection().isEmpty();
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    boolean isPartialView() {
        return delegateCollection().isPartialView();
    }

    static class SerializedForm implements Serializable {
        private static final long serialVersionUID = 0;
        final ImmutableCollection<?> collection;

        SerializedForm(ImmutableCollection<?> immutableCollection) {
            this.collection = immutableCollection;
        }

        Object readResolve() {
            return this.collection.asList();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    @Override // org.jetbrains.kotlin.com.google.common.collect.ImmutableList, org.jetbrains.kotlin.com.google.common.collect.ImmutableCollection
    Object writeReplace() {
        return new SerializedForm(delegateCollection());
    }
}
