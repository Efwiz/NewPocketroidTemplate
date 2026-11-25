package com.reandroid.arsc.item;

import com.reandroid.arsc.item.BlockItem;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class IndirectItem<T extends BlockItem> {
    private final T blockItem;
    private final int offset;

    public IndirectItem(T t, int i) {
        this.blockItem = t;
        this.offset = i;
    }

    public byte[] getBytesInternal() {
        return this.blockItem.getBytesInternal();
    }

    public T getBlockItem() {
        return this.blockItem;
    }

    public int getOffset() {
        return this.offset;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IndirectItem indirectItem = (IndirectItem) obj;
        return getOffset() == indirectItem.getOffset() && getBlockItem() == indirectItem.getBlockItem();
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(getOffset()), getBlockItem());
    }
}
