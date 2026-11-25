package com.reandroid.dex.reference;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.EditableItem;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;

/* JADX INFO: loaded from: classes11.dex */
public interface DexReference<T extends SectionItem> extends IntegerReference, BlockRefresh, EditableItem {
    T getItem();

    Key getKey();

    SectionType<T> getSectionType();

    void pullItem();

    void setItem(T t);

    void setItem(Key key);
}
