package com.reandroid.dex.value;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.smali.SmaliWriter;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class PrimitiveValueBlock extends DexValueBlock<NumberValue> {
    @Override // com.reandroid.dex.value.DexValueBlock
    public abstract Number getData();

    @Override // com.reandroid.dex.value.DexValueBlock
    public abstract TypeKey getDataTypeKey();

    public abstract String getHex();

    public abstract void setData(Number number);

    public PrimitiveValueBlock(DexValueType<?> dexValueType) {
        super(new NumberValue(), dexValueType);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void setData(Object obj) {
        setData((Number) obj);
    }

    public long getSignedValue() {
        return getValueContainer().getSignedNumber();
    }

    public long getUnsigned() {
        return getValueContainer().getUnsignedNumber();
    }

    void setNumberValue(byte b) {
        NumberValue valueContainer = getValueContainer();
        valueContainer.setNumberValue(b);
        setValueSize(valueContainer.getSize() - 1);
    }

    void setNumberValue(short s) {
        byte b;
        if (s < 0 && (b = (byte) s) == s) {
            setNumberValue(b);
            return;
        }
        NumberValue valueContainer = getValueContainer();
        valueContainer.setNumberValue(s);
        setValueSize(valueContainer.getSize() - 1);
    }

    void setNumberValue(int i) {
        short s;
        if (i < 0 && (s = (short) i) == i) {
            setNumberValue(s);
            return;
        }
        NumberValue valueContainer = getValueContainer();
        valueContainer.setNumberValue(i);
        setValueSize(valueContainer.getSize() - 1);
    }

    void setNumberValue(long j) {
        if (j < 0) {
            int i = (int) j;
            if (i == j) {
                setNumberValue(i);
                return;
            }
        }
        NumberValue valueContainer = getValueContainer();
        valueContainer.setNumberValue(j);
        setValueSize(valueContainer.getSize() - 1);
    }

    void setUnsignedValue(long j) {
        NumberValue valueContainer = getValueContainer();
        valueContainer.setUnsignedNumber(j);
        setValueSize(valueContainer.getSize() - 1);
    }

    void setValue(long j, int i) {
        NumberValue valueContainer = getValueContainer();
        valueContainer.setNumber(j, i);
        setValueSize(valueContainer.getSize() - 1);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String getAsString() {
        return getHex();
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        getValueTypeItem().onReadBytes(blockReader);
        NumberValue valueContainer = getValueContainer();
        valueContainer.setSize(getValueSize() + 1);
        valueContainer.readBytes(blockReader);
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public void merge(DexValueBlock<?> dexValueBlock) {
        super.merge(dexValueBlock);
        getValueContainer().merge(((PrimitiveValueBlock) dexValueBlock).getValueContainer());
    }

    @Override // com.reandroid.dex.value.DexValueBlock, com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getHex());
    }

    @Override // com.reandroid.dex.value.DexValueBlock
    public String toString() {
        return getHex();
    }
}
