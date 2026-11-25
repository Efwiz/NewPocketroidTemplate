package com.reandroid.dex.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.arsc.item.ByteItem;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class DexValueBlock<T extends Block> extends FixedBlockContainer implements SmaliFormat {
    private boolean mTemporary;
    private final T valueContainer;
    private final ByteItem valueTypeItem;

    public Object getData() {
        return null;
    }

    public void replaceKeys(Key key, Key key2) {
    }

    DexValueBlock(T t, DexValueType<?> dexValueType) {
        super(2);
        ByteItem byteItem = new ByteItem();
        this.valueTypeItem = byteItem;
        this.valueContainer = t;
        addChild(0, byteItem);
        addChild(1, t);
        byteItem.set((byte) dexValueType.getFlag(0));
    }

    DexValueBlock(DexValueType<?> dexValueType) {
        this(null, dexValueType);
    }

    public boolean isTemporary() {
        return this.mTemporary;
    }

    public void setTemporary(boolean z) {
        this.mTemporary = z;
    }

    T getValueContainer() {
        return this.valueContainer;
    }

    ByteItem getValueTypeItem() {
        return this.valueTypeItem;
    }

    public DexValueType<?> getValueType() {
        return getValueTypeReal();
    }

    private DexValueType<?> getValueTypeReal() {
        return DexValueType.fromFlag(this.valueTypeItem.unsignedInt());
    }

    int getValueSize() {
        return DexValueType.decodeSize(this.valueTypeItem.unsignedInt());
    }

    void setValueSize(int i) {
        this.valueTypeItem.set((byte) getValueType().getFlag(i));
    }

    public Iterator<IdItem> usedIds() {
        return EmptyIterator.of();
    }

    public void merge(DexValueBlock<?> dexValueBlock) {
        this.valueTypeItem.set(dexValueBlock.valueTypeItem.get());
    }

    public void fromSmali(SmaliValue smaliValue) {
        throw new RuntimeException("Method not implemented: " + getClass().getSimpleName());
    }

    public void append(SmaliWriter smaliWriter) throws IOException {
        Object valueContainer = getValueContainer();
        if (valueContainer instanceof SmaliFormat) {
            ((SmaliFormat) valueContainer).append(smaliWriter);
        }
    }

    public String getAsString() {
        return String.valueOf(getValueContainer());
    }

    public boolean is(DexValueType<?> dexValueType) {
        return dexValueType == getValueType();
    }

    public TypeKey getDataTypeKey() {
        return TypeKey.OBJECT;
    }

    public void setData(Object obj) {
        throw new RuntimeException("Method not implemented");
    }

    public int hashCode() {
        return ((getValueType().getType() + 31) * 31) + getValueContainer().hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(getValueContainer(), ((DexValueBlock) obj).getValueContainer());
    }

    public String toString() {
        return String.valueOf(getValueContainer());
    }
}
