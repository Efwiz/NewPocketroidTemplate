package com.reandroid.dex.value;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueByte;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes11.dex */
public class ByteValue extends PrimitiveValueBlock {
    public ByteValue() {
        super(DexValueType.BYTE);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Byte getData() {
        return Byte.valueOf(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        set(((Byte) number).byteValue());
    }

    public byte get() {
        return (byte) getSignedValue();
    }

    public void set(byte b) {
        setNumberValue(b);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.BYTE;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toSignedHex((int) get()) + "t";
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_B;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueByte) smaliValue).getValue());
    }
}
