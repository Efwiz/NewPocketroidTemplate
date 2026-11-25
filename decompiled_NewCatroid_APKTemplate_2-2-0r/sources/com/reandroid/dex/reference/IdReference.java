package com.reandroid.dex.reference;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.base.Block;
import com.reandroid.dex.id.IdItem;

/* JADX INFO: loaded from: classes11.dex */
public interface IdReference<T extends IdItem> extends DexReference<T> {
    @Override // com.reandroid.dex.common.EditableItem
    default void editInternal(Block block) {
        T item = getItem();
        if (item != null) {
            item.editInternal(block);
        }
    }

    default void checkNonNullItem(T t) {
        if (t == null) {
            throw new NullPointerException("Null item for: " + getSectionType().getName());
        }
    }

    default void checkNonNullItem(T t, int i) {
        if (t == null) {
            throw new NullPointerException("Null item (" + i + Constant.AFTER_QUTO + getSectionType().getName());
        }
    }
}
