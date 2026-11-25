package com.reandroid.arsc.value;

import com.reandroid.utils.StringsUtil;

/* JADX INFO: loaded from: classes10.dex */
public enum AttributeDataFormat {
    REFERENCE(1, new ValueType[]{ValueType.REFERENCE, ValueType.ATTRIBUTE, ValueType.DYNAMIC_REFERENCE, ValueType.DYNAMIC_ATTRIBUTE, ValueType.NULL}),
    INTEGER(4, new ValueType[]{ValueType.DEC, ValueType.HEX}),
    BOOL(8, new ValueType[]{ValueType.BOOLEAN}),
    COLOR(16, new ValueType[]{ValueType.COLOR_ARGB8, ValueType.COLOR_RGB8, ValueType.COLOR_RGB4, ValueType.COLOR_ARGB4}),
    FLOAT(32, new ValueType[]{ValueType.FLOAT}),
    DIMENSION(64, new ValueType[]{ValueType.DIMENSION}),
    FRACTION(128, new ValueType[]{ValueType.FRACTION}),
    ANY(65535, (ValueType[]) ValueType.values().clone()),
    ENUM(65536, new ValueType[]{ValueType.DEC, ValueType.HEX}),
    FLAG(131072, new ValueType[]{ValueType.HEX, ValueType.DEC}),
    STRING(2, new ValueType[]{ValueType.STRING});

    private static final AttributeDataFormat[] BAG_TYPES;
    private static final AttributeDataFormat[] VALUE_TYPES;
    private final int mask;
    private final ValueType[] valueTypes;

    static {
        AttributeDataFormat attributeDataFormat = REFERENCE;
        AttributeDataFormat attributeDataFormat2 = INTEGER;
        AttributeDataFormat attributeDataFormat3 = BOOL;
        AttributeDataFormat attributeDataFormat4 = COLOR;
        AttributeDataFormat attributeDataFormat5 = FLOAT;
        AttributeDataFormat attributeDataFormat6 = DIMENSION;
        AttributeDataFormat attributeDataFormat7 = FRACTION;
        AttributeDataFormat attributeDataFormat8 = ANY;
        AttributeDataFormat attributeDataFormat9 = ENUM;
        AttributeDataFormat attributeDataFormat10 = FLAG;
        VALUE_TYPES = new AttributeDataFormat[]{attributeDataFormat, STRING, attributeDataFormat2, attributeDataFormat3, attributeDataFormat4, attributeDataFormat5, attributeDataFormat6, attributeDataFormat7, attributeDataFormat8};
        BAG_TYPES = new AttributeDataFormat[]{attributeDataFormat9, attributeDataFormat10};
    }

    AttributeDataFormat(int i, ValueType[] valueTypeArr) {
        this.mask = i;
        this.valueTypes = valueTypeArr;
    }

    public int getMask() {
        return this.mask;
    }

    public boolean matches(int i) {
        int i2 = this.mask;
        return (i & i2) == i2;
    }

    public ValueType[] valueTypes() {
        return this.valueTypes;
    }

    public ValueType[] getValueTypes() {
        return (ValueType[]) this.valueTypes.clone();
    }

    public boolean contains(ValueType valueType) {
        for (ValueType valueType2 : this.valueTypes) {
            if (valueType == valueType2) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name().toLowerCase();
    }

    @Override // java.lang.Enum
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        sb.append('{');
        ValueType[] valueTypeArr = this.valueTypes;
        for (int i = 0; i < valueTypeArr.length; i++) {
            if (i != 0) {
                sb.append(',');
            }
            sb.append(valueTypeArr[i]);
        }
        sb.append('}');
        return sb.toString();
    }

    public static String toStringValueTypes(int i) {
        return toString(decodeValueTypes(i));
    }

    public static String toString(AttributeDataFormat[] attributeDataFormatArr) {
        if (attributeDataFormatArr == null || attributeDataFormatArr.length == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        boolean z = false;
        for (AttributeDataFormat attributeDataFormat : attributeDataFormatArr) {
            if (attributeDataFormat != ENUM && attributeDataFormat != FLAG) {
                AttributeDataFormat attributeDataFormat2 = ANY;
                if (attributeDataFormat == attributeDataFormat2) {
                    return attributeDataFormat2.getName();
                }
                int mask = attributeDataFormat.getMask();
                if ((i & mask) != mask) {
                    if (z) {
                        sb.append('|');
                    }
                    sb.append(attributeDataFormat.getName());
                    i |= mask;
                    z = true;
                }
            }
        }
        return sb.toString();
    }

    public static int sum(AttributeDataFormat[] attributeDataFormatArr) {
        if (attributeDataFormatArr == null) {
            return 0;
        }
        int mask = 0;
        for (AttributeDataFormat attributeDataFormat : attributeDataFormatArr) {
            if (attributeDataFormat != null) {
                mask |= attributeDataFormat.getMask();
            }
        }
        return mask;
    }

    public static AttributeDataFormat[] decodeValueTypes(int i) {
        AttributeDataFormat[] attributeDataFormatArr = VALUE_TYPES;
        AttributeDataFormat[] attributeDataFormatArr2 = new AttributeDataFormat[attributeDataFormatArr.length];
        int i2 = 0;
        for (AttributeDataFormat attributeDataFormat : attributeDataFormatArr) {
            int mask = attributeDataFormat.getMask();
            if (mask == i) {
                return new AttributeDataFormat[]{attributeDataFormat};
            }
            if (attributeDataFormat != ANY && (i & mask) == mask) {
                attributeDataFormatArr2[i2] = attributeDataFormat;
                i2++;
            }
        }
        if (i2 == 0) {
            return null;
        }
        AttributeDataFormat[] attributeDataFormatArr3 = new AttributeDataFormat[i2];
        System.arraycopy(attributeDataFormatArr2, 0, attributeDataFormatArr3, 0, i2);
        return attributeDataFormatArr3;
    }

    public static AttributeDataFormat[] parseValueTypes(String str) {
        if (str == null) {
            return null;
        }
        String[] strArrSplit = str.trim().split("\\s*\\|\\s*");
        AttributeDataFormat[] attributeDataFormatArr = new AttributeDataFormat[VALUE_TYPES.length];
        int i = 0;
        for (String str2 : strArrSplit) {
            AttributeDataFormat attributeDataFormatFromValueTypeName = fromValueTypeName(str2);
            if (attributeDataFormatFromValueTypeName != null) {
                attributeDataFormatArr[i] = attributeDataFormatFromValueTypeName;
                i++;
            }
        }
        if (i == 0) {
            return null;
        }
        AttributeDataFormat[] attributeDataFormatArr2 = new AttributeDataFormat[i];
        System.arraycopy(attributeDataFormatArr, 0, attributeDataFormatArr2, 0, i);
        return attributeDataFormatArr2;
    }

    public static AttributeDataFormat valueOf(int i) {
        for (AttributeDataFormat attributeDataFormat : VALUE_TYPES) {
            if (attributeDataFormat.getMask() == i) {
                return attributeDataFormat;
            }
        }
        return null;
    }

    public static AttributeDataFormat typeOfBag(int i) {
        for (AttributeDataFormat attributeDataFormat : BAG_TYPES) {
            if (attributeDataFormat.matches(i)) {
                return attributeDataFormat;
            }
        }
        return null;
    }

    public static AttributeDataFormat fromValueTypeName(String str) {
        if (str == null) {
            return null;
        }
        String upperCase = StringsUtil.toUpperCase(str.trim());
        for (AttributeDataFormat attributeDataFormat : VALUE_TYPES) {
            if (upperCase.equals(attributeDataFormat.name())) {
                return attributeDataFormat;
            }
        }
        return null;
    }

    public static AttributeDataFormat fromBagTypeName(String str) {
        if (str == null) {
            return null;
        }
        String upperCase = StringsUtil.toUpperCase(str.trim());
        for (AttributeDataFormat attributeDataFormat : BAG_TYPES) {
            if (upperCase.equals(attributeDataFormat.name())) {
                return attributeDataFormat;
            }
        }
        return null;
    }

    public static ValueType[] getExpectedValueTypes(String str) {
        AttributeDataFormat attributeDataFormatFromValueTypeName = fromValueTypeName(str);
        if (attributeDataFormatFromValueTypeName != null) {
            return attributeDataFormatFromValueTypeName.valueTypes();
        }
        return null;
    }

    public static boolean contains(AttributeDataFormat[] attributeDataFormatArr, ValueType valueType) {
        if (attributeDataFormatArr != null && valueType != null) {
            for (AttributeDataFormat attributeDataFormat : attributeDataFormatArr) {
                if (attributeDataFormat != null && attributeDataFormat.contains(valueType)) {
                    return true;
                }
            }
        }
        return false;
    }
}
