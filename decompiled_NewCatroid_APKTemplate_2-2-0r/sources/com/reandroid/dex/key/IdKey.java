package com.reandroid.dex.key;

import com.reandroid.dex.id.IdItem;

/* JADX INFO: loaded from: classes11.dex */
public class IdKey<T extends IdItem> implements Key {
    private final T item;

    public IdKey(T t) {
        this.item = t;
    }

    public T getItem() {
        return this.item;
    }

    private int getIndex() {
        return getItem().getIndex();
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            if (getItem().getClass() == ((IdKey) obj).getItem().getClass()) {
                return 0;
            }
        }
        return 1;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.item.equals(((IdKey) obj).item);
    }

    public int hashCode() {
        return this.item.hashCode();
    }

    public String toString() {
        return getIndex() + ": {" + getItem() + "}";
    }
}
