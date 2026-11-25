package com.reandroid.dex.common;

import com.reandroid.dex.id.IdItem;
import java.util.Iterator;

/* JADX INFO: loaded from: classes7.dex */
public interface IdDefinition<T extends IdItem> {
    Iterator<? extends Modifier> getAccessFlags();

    int getAccessFlagsValue();

    T getId();

    void setAccessFlagsValue(int i);

    default Iterator<? extends Modifier> getModifiers() {
        return getAccessFlags();
    }

    default void addAccessFlag(AccessFlag accessFlag) {
        int accessFlagsValue = getAccessFlagsValue();
        int value = accessFlag.getValue();
        if ((value & 7) != 0) {
            accessFlagsValue &= -8;
        }
        setAccessFlagsValue(value | accessFlagsValue);
    }

    default void removeAccessFlag(AccessFlag accessFlag) {
        setAccessFlagsValue((~accessFlag.getValue()) & getAccessFlagsValue());
    }
}
