package com.reandroid.dex.value;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueLong;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class LongValue extends PrimitiveValueBlock {
    public LongValue() {
        super(DexValueType.LONG);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Long getData() {
        return Long.valueOf(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        set(((Long) number).longValue());
    }

    public long get() {
        return getSignedValue();
    }

    public void set(long j) {
        setNumberValue(j);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.LONG;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toSignedHex(get()) + "L";
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_J;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueLong) smaliValue).getValue());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        super.append(smaliWriter);
        smaliWriter.appendComment(Long.toString(get()));
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return getHex() + " # " + get();
    }
}
