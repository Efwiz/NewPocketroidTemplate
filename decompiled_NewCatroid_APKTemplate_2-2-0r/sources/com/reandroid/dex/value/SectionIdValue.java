package com.reandroid.dex.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.data.AnnotationElement;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.SingleIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SectionIdValue<T extends IdItem> extends SectionValue<T> {
    @Override // com.reandroid.dex.common.EditableItem
    public void editInternal(Block block) {
    }

    public SectionIdValue(SectionType<T> sectionType, DexValueType<?> dexValueType) {
        super(sectionType, dexValueType);
    }

    public void setKey(Key key) {
        setItem(key);
    }

    @Override // com.reandroid.dex.value.SectionValue, com.reandroid.dex.key.KeyItem
    public Key getKey() {
        T item = getItem();
        if (item != null) {
            return item.getKey();
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.value.SectionValue
    public int getSectionValue(T t) {
        if (t == null) {
            throw new NullPointerException("Section data can not be null: " + getSectionType().getName());
        }
        return t.getIndex();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.value.SectionValue
    public T getReplacement(T t) {
        if (t != null) {
            t = (T) t.getReplace();
        }
        if (t != null) {
            return t;
        }
        throw new NullPointerException("Section data can not be null: " + getSectionType().getName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.value.SectionValue
    public void updateUsageType(T t) {
        int i;
        if (t != null) {
            if (getParent(AnnotationElement.class) != null) {
                i = UsageMarker.USAGE_ANNOTATION;
            } else {
                i = UsageMarker.USAGE_ENCODED_VALUE;
            }
            t.addUsageType(i);
        }
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void replaceKeys(Key key, Key key2) {
        Key key3 = getKey();
        Key keyReplaceKey = key3.replaceKey(key, key2);
        if (key3 != keyReplaceKey) {
            setItem(keyReplaceKey);
        }
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public Iterator<IdItem> usedIds() {
        return SingleIterator.of(getItem());
    }
}
