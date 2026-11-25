package com.reandroid.dex.value;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueShort;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes11.dex */
public class ShortValue extends PrimitiveValueBlock {
    public ShortValue() {
        super(DexValueType.SHORT);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Short getData() {
        return Short.valueOf(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        set(((Short) number).shortValue());
    }

    public short get() {
        return (short) getSignedValue();
    }

    public void set(short s) {
        setNumberValue(s);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.SHORT;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toSignedHex((int) get()) + ExifInterface.LATITUDE_SOUTH;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_S;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueShort) smaliValue).getValue());
    }
}
