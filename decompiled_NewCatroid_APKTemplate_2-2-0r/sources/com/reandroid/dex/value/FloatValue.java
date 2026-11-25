package com.reandroid.dex.value;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueFloat;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class FloatValue extends PrimitiveValueBlock {
    public FloatValue() {
        super(DexValueType.FLOAT);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Float getData() {
        return Float.valueOf(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        set(((Float) number).floatValue());
    }

    public float get() {
        return Float.intBitsToFloat(getFloatBits());
    }

    public void set(float f) {
        setFloatBits(Float.floatToIntBits(f));
    }

    private int getFloatBits() {
        return (int) (getUnsigned() << ((3 - getValueSize()) * 8));
    }

    private void setFloatBits(int i) {
        int i2 = 0;
        while (i2 < 3 && (i & 255) == 0) {
            i >>>= 8;
            i2++;
        }
        setValue(((long) i) & 4294967295L, 4 - i2);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.FLOAT;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toHex(getUnsigned(), getValueSize()) + "L";
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_F;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueFloat) smaliValue).getValue());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return getHex() + " # " + get();
    }
}
