package com.reandroid.dex.reference;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.IndirectItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes11.dex */
public class IdItemIndirectReference<T extends IdItem> extends IndirectItem<SectionItem> implements IdReference<T>, Comparable<IdReference<T>> {
    private T item;
    private final SectionType<T> sectionType;
    private final int usageType;

    public IdItemIndirectReference(SectionType<T> sectionType, SectionItem sectionItem, int i, int i2) {
        super(sectionItem, i);
        this.sectionType = sectionType;
        this.usageType = i2;
        Block.putInteger(getBytesInternal(), getOffset(), -1);
    }

    public IdItemIndirectReference(SectionType<T> sectionType, SectionItem sectionItem, int i) {
        this(sectionType, sectionItem, i, IdItem.USAGE_NONE);
    }

    public Key getKey() {
        IdItem item = getItem();
        if (item != null) {
            return item.getKey();
        }
        return null;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public T getItem() {
        return this.item;
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(T t) {
        if (t == this.item) {
            return;
        }
        set(getItemIndex(t));
        this.item = t;
        updateItemUsage();
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void pullItem() {
        this.item = (T) pullItem(get());
        updateItemUsage();
    }

    protected T pullItem(int i) {
        return (T) getBlockItem().getSectionItem(getSectionType(), i);
    }

    @Override // com.reandroid.dex.reference.DexReference
    public void setItem(Key key) {
        setItem((IdItem) getBlockItem().getOrCreateSectionItem(getSectionType(), key));
    }

    @Override // com.reandroid.dex.reference.DexReference
    public SectionType<T> getSectionType() {
        return this.sectionType;
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        Block.putInteger(getBytesInternal(), getOffset(), i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return Block.getInteger(getBytesInternal(), getOffset());
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        T t = (T) getItem();
        if (t != null) {
            t = (T) t.getReplace();
        }
        checkNonNullItem(t);
        if (t != null) {
            set(t.getIdx());
        }
        this.item = t;
        updateItemUsage();
    }

    public void checkNonNullItem(T t) {
        if (t == null) {
            throw new NullPointerException(buildMessage());
        }
    }

    private String buildMessage() {
        SectionItem blockItem = getBlockItem();
        StringBuilder sb = new StringBuilder("Parent = ");
        sb.append(blockItem);
        for (Field field : blockItem.getClass().getFields()) {
            try {
                field.setAccessible(true);
                if (field.get(blockItem) == this) {
                    sb.append(", Field = ");
                    sb.append(field.getName());
                    break;
                }
                continue;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
        sb.append(", section = ");
        sb.append(getSectionType().getName());
        return sb.toString();
    }

    protected int getItemIndex(T t) {
        if (t == null) {
            throw new NullPointerException("Can't set null for reference of: " + getSectionType().getName());
        }
        return t.getIdx();
    }

    private void updateItemUsage() {
        T t;
        int i = this.usageType;
        if (i == UsageMarker.USAGE_NONE || (t = this.item) == null) {
            return;
        }
        t.addUsageType(i);
    }

    public void unlink() {
        this.item = null;
        set(0);
    }

    public void replaceKeys(Key key, Key key2) {
        Key keyReplaceKey;
        Key key3 = getKey();
        if (key3 == null || key3 == (keyReplaceKey = key3.replaceKey(key, key2))) {
            return;
        }
        setItem(keyReplaceKey);
    }

    @Override // java.lang.Comparable
    public int compareTo(IdReference<T> idReference) {
        return SectionTool.compareIdx((T) getItem(), idReference.getItem());
    }

    public String toString() {
        if (this.item != null) {
            return get() + ": " + this.item.toString();
        }
        return this.sectionType.getName() + ": " + get();
    }
}
