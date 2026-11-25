package com.reandroid.dex.key;

import com.reandroid.dex.data.DataItem;
import com.reandroid.utils.CompareUtil;

/* JADX INFO: loaded from: classes11.dex */
public class DataKey<T extends DataItem> implements Key {
    private final T item;

    public DataKey(T t) {
        this.item = t;
    }

    public T getItem() {
        return this.item;
    }

    private int getOffset() {
        return getItem().getOffset();
    }

    @Override // java.lang.Comparable
    public int compareTo(Object obj) {
        if (obj != null && getClass() == obj.getClass()) {
            DataKey dataKey = (DataKey) obj;
            if (getItem().equals(dataKey.getItem())) {
                return 0;
            }
            int iCompareUnsigned = CompareUtil.compareUnsigned(getItem().getIndex(), dataKey.getItem().getIndex());
            if (iCompareUnsigned != 0) {
                return iCompareUnsigned;
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
        return this.item.equals(((DataKey) obj).item);
    }

    public int hashCode() {
        return this.item.hashCode();
    }

    public String toString() {
        return getOffset() + ": {" + getItem() + "}";
    }
}
