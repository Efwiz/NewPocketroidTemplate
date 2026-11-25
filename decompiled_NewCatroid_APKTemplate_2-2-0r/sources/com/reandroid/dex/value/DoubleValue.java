package com.reandroid.dex.value;

import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliValue;
import com.reandroid.dex.smali.model.SmaliValueDouble;
import com.reandroid.utils.HexUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class DoubleValue extends PrimitiveValueBlock {
    public DoubleValue() {
        super(DexValueType.DOUBLE);
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public Double getData() {
        return Double.valueOf(get());
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public void setData(Number number) {
        set(((Double) number).doubleValue());
    }

    public double get() {
        return Double.longBitsToDouble(getLongBits());
    }

    public void set(double d) {
        setLongBits(Double.doubleToLongBits(d));
    }

    private long getLongBits() {
        return getUnsigned() << ((7 - getValueSize()) * 8);
    }

    private void setLongBits(long j) {
        int i = 0;
        while (i < 7 && (255 & j) == 0) {
            j >>>= 8;
            i++;
        }
        setValue(j, 8 - i);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public DexValueType<?> getValueType() {
        return DexValueType.DOUBLE;
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock
    public String getHex() {
        return HexUtil.toHex(getUnsigned() << ((7 - getValueSize()) * 8), 8) + "L";
    }

    @Override // com.reandroid.dex.value.PrimitiveValueBlock, com.reandroid.dex.value.DexValueBlock
    public TypeKey getDataTypeKey() {
        return TypeKey.TYPE_D;
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void fromSmali(SmaliValue smaliValue) {
        set(((SmaliValueDouble) smaliValue).getValue());
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
