package com.reandroid.arsc.value;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.ParentChunk;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.graphics.AndroidColor;

/* JADX INFO: loaded from: classes10.dex */
public interface Value {
    int getData();

    PackageBlock getPackageBlock();

    ParentChunk getParentChunk();

    String getValueAsString();

    ValueType getValueType();

    void setData(int i);

    void setValue(EncodeResult encodeResult);

    void setValueAsString(String str);

    void setValueType(ValueType valueType);

    default void setTypeAndData(ValueType valueType, int i) {
        setData(i);
        setValueType(valueType);
    }

    default AndroidColor getValueAsColor() {
        ValueType valueType = getValueType();
        if (valueType == null || !valueType.isColor()) {
            return null;
        }
        return AndroidColor.decode(ValueCoder.decode(valueType, getData()));
    }

    default void setValue(AndroidColor androidColor) {
        setValue(ValueCoder.encode(androidColor.toHexString()));
    }

    default ResourceEntry getValueAsReference() {
        PackageBlock packageBlock;
        TableBlock tableBlock;
        ValueType valueType = getValueType();
        if (valueType == null || !valueType.isReference() || (packageBlock = getPackageBlock()) == null) {
            return null;
        }
        int data = getData();
        ResourceEntry resource = packageBlock.getResource(data);
        return (resource != null || (tableBlock = packageBlock.getTableBlock()) == null) ? resource : tableBlock.getResource(packageBlock, data);
    }

    default Float getValueAsFloat() {
        if (getValueType() != ValueType.FLOAT) {
            return null;
        }
        return Float.valueOf(Float.intBitsToFloat(getData()));
    }

    default void setValueAsFloat(float f) {
        setTypeAndData(ValueType.FLOAT, Float.floatToIntBits(f));
    }

    default Integer getValueAsInteger() {
        ValueType valueType = getValueType();
        if (valueType == ValueType.DEC || valueType == ValueType.HEX) {
            return Integer.valueOf(getData());
        }
        return null;
    }

    default void setValueAsDecimal(int i) {
        setTypeAndData(ValueType.DEC, i);
    }

    default void setValueAsHex(int i) {
        setTypeAndData(ValueType.HEX, i);
    }

    default int getValueAsResourceId() {
        ValueType valueType = getValueType();
        if (valueType == null || !valueType.isReference()) {
            return 0;
        }
        return getData();
    }

    default void setValueAsResourceId(int i) {
        setTypeAndData(ValueType.REFERENCE, i);
    }

    default void setValueAsResourceAttributeId(int i) {
        setTypeAndData(ValueType.ATTRIBUTE, i);
    }
}
