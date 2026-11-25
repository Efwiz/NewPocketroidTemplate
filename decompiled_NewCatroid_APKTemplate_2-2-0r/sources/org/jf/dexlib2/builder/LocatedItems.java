package org.jf.dexlib2.builder;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.jf.dexlib2.builder.ItemWithLocation;
import org.jf.util.collection.EmptyList;

/* JADX INFO: loaded from: classes6.dex */
public abstract class LocatedItems<T extends ItemWithLocation> {
    private List<T> items = null;

    protected abstract String getAddLocatedItemError();

    /* JADX INFO: Access modifiers changed from: private */
    public List<T> getItems() {
        List<T> list = this.items;
        return list == null ? EmptyList.of() : list;
    }

    public Set<T> getModifiableItems(final MethodLocation methodLocation) {
        return new AbstractSet<T>() { // from class: org.jf.dexlib2.builder.LocatedItems.1
            @Override // java.util.AbstractCollection, java.util.Collection, java.lang.Iterable, java.util.Set
            public Iterator<T> iterator() {
                final Iterator it = LocatedItems.this.getItems().iterator();
                return (Iterator<T>) new Iterator<T>() { // from class: org.jf.dexlib2.builder.LocatedItems.1.1
                    private T currentItem = null;

                    @Override // java.util.Iterator
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override // java.util.Iterator
                    public T next() {
                        T t = (T) it.next();
                        this.currentItem = t;
                        return t;
                    }

                    @Override // java.util.Iterator
                    public void remove() {
                        T t = this.currentItem;
                        if (t != null) {
                            t.setLocation(null);
                        }
                        it.remove();
                    }
                };
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public int size() {
                return LocatedItems.this.getItems().size();
            }

            @Override // java.util.AbstractCollection, java.util.Collection, java.util.Set
            public boolean add(T t) {
                if (t.isPlaced()) {
                    throw new IllegalArgumentException(LocatedItems.this.getAddLocatedItemError());
                }
                t.setLocation(methodLocation);
                LocatedItems.this.addItem(t);
                return true;
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addItem(T t) {
        if (this.items == null) {
            this.items = new ArrayList(1);
        }
        this.items.add(t);
    }

    public void mergeItemsIntoNext(MethodLocation methodLocation, LocatedItems<T> locatedItems) {
        List<T> list;
        if (locatedItems == this || (list = this.items) == null) {
            return;
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            it.next().setLocation(methodLocation);
        }
        List<T> list2 = this.items;
        list2.addAll(locatedItems.getItems());
        locatedItems.items = list2;
        this.items = null;
    }
}
