package com.reandroid.dex.reference;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.IndirectItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes11.dex */
public class DataItemIndirectReference<T extends DataItem> extends IndirectItem<SectionItem> implements DataReference<T> {
    private T item;
    private final SectionType<T> sectionType;
    private final int usageType;

    public DataItemIndirectReference(SectionType<T> sectionType, SectionItem sectionItem, int i, int i2) {
        super(sectionItem, i);
        this.sectionType = sectionType;
        this.usageType = i2;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public T getItem() {
        DataItem dataItem;
        T t = this.item;
        if (t == null || (dataItem = (DataItem) t.getReplace()) == t) {
            return t;
        }
        setItem(dataItem);
        return this.item;
    }

    @Override // com.reandroid.dex.reference.DataReference
    public T getOrCreate() {
        T t = (T) getItem();
        if (t != null) {
            return t;
        }
        T t2 = (T) getBlockItem().createSectionItem(getSectionType());
        setItem((DataItem) t2);
        return t2;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(T t) {
        if (t == this.item) {
            return;
        }
        set(t != null ? t.getIdx() : 0);
        this.item = t;
        updateItemUsage();
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(Key key) {
        setItem((DataItem) getBlockItem().getOrCreateSectionItem(getSectionType(), key));
    }

    @Override // com.reandroid.dex.reference.DexReference
    public Key getKey() {
        DataItem item = getItem();
        if (item != null) {
            return item.getKey();
        }
        return null;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public SectionType<T> getSectionType() {
        return this.sectionType;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void pullItem() {
        int i = get();
        this.item = i == 0 ? null : (T) getBlockItem().getSectionItem(getSectionType(), i);
        updateItemUsage();
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        int idx;
        T t = (T) getItem();
        if (t != null) {
            idx = t.getIdx();
            if (idx == 0) {
                throw new RuntimeException("Invalid reference");
            }
        } else {
            idx = 0;
        }
        this.item = t;
        set(idx);
        updateItemUsage();
    }

    @Override // com.reandroid.dex.reference.DataReference
    public void unlink() {
        this.item = null;
        set(0);
    }

    private void updateItemUsage() {
        T t;
        int i = this.usageType;
        if (i == UsageMarker.USAGE_NONE || (t = this.item) == null) {
            return;
        }
        t.addUsageType(i);
    }

    public T getUniqueItem(Block block) {
        T t = (T) getItem();
        if (t == null) {
            return null;
        }
        if (t.isSharedItem(block)) {
            t = (T) createNewCopy();
        }
        t.addUniqueUser(block);
        return t;
    }

    public T getOrCreateUniqueItem(Block block) {
        T t = (T) getUniqueItem(block);
        if (t != null) {
            return t;
        }
        T t2 = (T) getBlockItem().createSectionItem(getSectionType());
        setItem((DataItem) t2);
        addUniqueUser(block);
        return t2;
    }

    public void addUniqueUser(Block block) {
        DataItem item = getItem();
        if (item != null) {
            item.addUniqueUser(block);
        }
    }

    private T createNewCopy() {
        T t = (T) getBlockItem().createSectionItem(getSectionType());
        copyToIfPresent(t);
        setItem((DataItem) t);
        return t;
    }

    private void copyToIfPresent(T t) {
        DataItem item = getItem();
        if (item != null) {
            t.copyFrom(item);
        }
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        Block.putInteger(getBytesInternal(), getOffset(), i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return Block.getInteger(getBytesInternal(), getOffset());
    }

    public void replaceKeys(Key key, Key key2) {
        Key keyReplaceKey;
        Key key3 = getKey();
        if (key3 == null || key3 == (keyReplaceKey = key3.replaceKey(key, key2))) {
            return;
        }
        setItem(keyReplaceKey);
    }

    @Override // com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
        DataItem uniqueItem = getUniqueItem(block);
        if (uniqueItem != null) {
            uniqueItem.editInternal(block);
        }
    }

    public String toString() {
        if (this.item != null) {
            return get() + ":" + this.item.toString();
        }
        return getSectionType().getName() + ": " + get();
    }
}
