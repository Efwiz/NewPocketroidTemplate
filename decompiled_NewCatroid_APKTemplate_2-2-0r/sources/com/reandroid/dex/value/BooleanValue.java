package com.reandroid.dex.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueBoolean;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class BooleanValue extends DexValueBlock<Block> {
    public BooleanValue() {
        super(DexValueType.BOOLEAN);
    }

    public boolean get() {
        return getValueSize() == 1;
    }

    public void set(boolean z) {
        setValueSize(z ? 1 : 0);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.BOOLEAN;
    }

    @Override // com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) Boolean.toString(get()));
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String getAsString() {
        return Boolean.toString(get());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_Z;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public Boolean getData() {
        if (get()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void setData(Object obj) {
        set(((Boolean) obj).booleanValue());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueBoolean) smaliValue).getValue());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public int hashCode() {
        return ((getValueType().getType() + 31) * 31) + getValueSize();
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && get() == ((BooleanValue) obj).get();
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return getAsString();
    }
}
