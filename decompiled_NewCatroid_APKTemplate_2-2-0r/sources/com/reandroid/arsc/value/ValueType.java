package com.reandroid.arsc.value;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.reandroid.graphics.AndroidColor;
import com.reandroid.utils.StringsUtil;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public enum ValueType {
    NULL((byte) 0, ""),
    REFERENCE((byte) 1, "reference"),
    ATTRIBUTE((byte) 2, "reference"),
    FLOAT((byte) 4, "float"),
    DIMENSION((byte) 5, TypedValues.Custom.S_DIMENSION),
    FRACTION((byte) 6, "fraction"),
    DEC((byte) 16, "integer"),
    HEX((byte) 17, "integer"),
    BOOLEAN((byte) 18, "bool"),
    COLOR_ARGB8((byte) 28, TypedValues.Custom.S_COLOR),
    COLOR_RGB8((byte) 29, TypedValues.Custom.S_COLOR),
    COLOR_ARGB4((byte) 30, TypedValues.Custom.S_COLOR),
    COLOR_RGB4((byte) 31, TypedValues.Custom.S_COLOR),
    STRING((byte) 3, "string"),
    DYNAMIC_REFERENCE((byte) 7, "reference"),
    DYNAMIC_ATTRIBUTE((byte) 8, "reference");

    private static ValueType[] sortedValues;
    private static Map<String, ValueType> valueTypeMap;
    private final byte mByte;
    private final String typeName;

    ValueType(byte b, String str) {
        this.mByte = b;
        this.typeName = str;
    }

    public byte getByte() {
        return this.mByte;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public boolean isColor() {
        return this == COLOR_ARGB8 || this == COLOR_RGB8 || this == COLOR_ARGB4 || this == COLOR_RGB4;
    }

    public boolean isInteger() {
        return this == DEC || this == HEX;
    }

    public boolean isReference() {
        return this == REFERENCE || this == ATTRIBUTE || this == DYNAMIC_REFERENCE || this == DYNAMIC_ATTRIBUTE;
    }

    public static ValueType valueOf(byte b) {
        return valueOf(b & 255);
    }

    public static ValueType valueOf(int i) {
        if (i < 0) {
            return null;
        }
        ValueType[] sortedValues2 = getSortedValues();
        if (i < sortedValues2.length) {
            return sortedValues2[i];
        }
        return null;
    }

    public static ValueType fromName(String str) {
        return getValueTypeMap().get(StringsUtil.toUpperCase(str));
    }

    public static AndroidColor.Type colorType(ValueType valueType) {
        if (valueType == COLOR_RGB4) {
            return AndroidColor.Type.RGB4;
        }
        if (valueType == COLOR_ARGB4) {
            return AndroidColor.Type.ARGB4;
        }
        if (valueType == COLOR_RGB8) {
            return AndroidColor.Type.RGB8;
        }
        if (valueType == COLOR_ARGB8) {
            return AndroidColor.Type.ARGB8;
        }
        return null;
    }

    public static ValueType colorType(AndroidColor.Type type) {
        if (type == AndroidColor.Type.RGB4) {
            return COLOR_RGB4;
        }
        if (type == AndroidColor.Type.ARGB4) {
            return COLOR_ARGB4;
        }
        if (type == AndroidColor.Type.RGB8) {
            return COLOR_RGB8;
        }
        if (type == AndroidColor.Type.ARGB8) {
            return COLOR_ARGB8;
        }
        return null;
    }

    private static ValueType[] getSortedValues() {
        ValueType[] valueTypeArr;
        ValueType[] valueTypeArr2 = sortedValues;
        if (valueTypeArr2 != null) {
            return valueTypeArr2;
        }
        synchronized (ValueType.class) {
            valueTypeArr = new ValueType[32];
            for (ValueType valueType : values()) {
                valueTypeArr[valueType.getByte() & 255] = valueType;
            }
            sortedValues = valueTypeArr;
        }
        return valueTypeArr;
    }

    private static Map<String, ValueType> getValueTypeMap() {
        HashMap map;
        Map<String, ValueType> map2 = valueTypeMap;
        if (map2 != null) {
            return map2;
        }
        synchronized (ValueType.class) {
            map = new HashMap();
            for (ValueType valueType : values()) {
                map.put(valueType.name(), valueType);
            }
            valueTypeMap = map;
        }
        return map;
    }
}
