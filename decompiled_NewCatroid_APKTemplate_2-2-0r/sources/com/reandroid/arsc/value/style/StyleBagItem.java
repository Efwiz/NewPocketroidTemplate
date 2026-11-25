package com.reandroid.arsc.value.style;

import com.reandroid.arsc.coder.CommonType;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.item.TableString;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.attribute.AttributeBag;
import com.reandroid.arsc.value.attribute.AttributeBagItem;
import com.reandroid.arsc.value.bag.BagItem;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class StyleBagItem extends BagItem {
    private StyleBagItem(ResValueMap resValueMap) {
        super(resValueMap);
    }

    private StyleBagItem(ValueType valueType, int i) {
        super(valueType, i);
    }

    private StyleBagItem(StringItem stringItem) {
        super(stringItem);
    }

    public String getName() {
        if (this.mBagItem == null) {
            return null;
        }
        return this.mBagItem.decodeName(true);
    }

    public Entry getAttributeEntry() {
        if (this.mBagItem == null) {
            return null;
        }
        return this.mBagItem.resolveName().get();
    }

    public int getNameId() {
        if (this.mBagItem == null) {
            return 0;
        }
        return this.mBagItem.getNameId();
    }

    public boolean hasAttributeValue() {
        return getValueType() == ValueType.ATTRIBUTE;
    }

    public boolean hasIntValue() {
        ValueType valueType = getValueType();
        return valueType == ValueType.DEC || valueType == ValueType.HEX;
    }

    public String getValueAsReference() {
        ValueType valueType = getValueType();
        if (valueType != ValueType.REFERENCE && valueType != ValueType.ATTRIBUTE) {
            throw new IllegalArgumentException("Not REF ValueType=" + valueType);
        }
        return getBagItem().decodeValue();
    }

    public String decodeAttributeValue(AttributeBag attributeBag) {
        if (hasIntValue()) {
            return attributeBag.decodeAttributeValue(getValue());
        }
        return null;
    }

    public AttributeBagItem[] getFlagsOrEnum(AttributeBag attributeBag) {
        if (hasIntValue()) {
            return attributeBag.searchValue(getValue());
        }
        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<item name=\"");
        String name2 = getName();
        if (name2 == null) {
            name2 = HexUtil.toHex8("@0x", getNameId());
        }
        sb.append(name2);
        sb.append("\">");
        if (hasStringValue()) {
            sb.append(getStringValue());
        }
        String valueAsReference = (hasReferenceValue() || hasAttributeValue()) ? getValueAsReference() : null;
        if (valueAsReference == null) {
            valueAsReference = HexUtil.toHex8(getValue());
        }
        sb.append(valueAsReference);
        sb.append("</item>");
        return sb.toString();
    }

    protected static StyleBagItem create(ResValueMap resValueMap) {
        if (resValueMap == null) {
            return null;
        }
        return new StyleBagItem(resValueMap);
    }

    public static StyleBagItem create(ValueType valueType, int i) {
        if (valueType == null || valueType == ValueType.STRING) {
            return null;
        }
        return new StyleBagItem(valueType, i);
    }

    protected static StyleBagItem copyOf(ResValueMap resValueMap) {
        ValueType valueType = resValueMap.getValueType();
        if (valueType == ValueType.STRING) {
            return new StyleBagItem(resValueMap.getDataAsPoolString());
        }
        return new StyleBagItem(valueType, resValueMap.getData());
    }

    public static StyleBagItem integer(int i) {
        return new StyleBagItem(ValueType.DEC, i);
    }

    public static StyleBagItem string(TableString tableString) {
        if (tableString == null) {
            return null;
        }
        return new StyleBagItem(tableString);
    }

    public static StyleBagItem reference(int i) {
        return new StyleBagItem(ValueType.REFERENCE, i);
    }

    public static StyleBagItem attribute(int i) {
        return new StyleBagItem(ValueType.ATTRIBUTE, i);
    }

    public static StyleBagItem encoded(EncodeResult encodeResult) {
        if (encodeResult == null) {
            return null;
        }
        return create(encodeResult.valueType, encodeResult.value);
    }

    public static StyleBagItem color(String str) {
        return encoded(ValueCoder.encode(str, CommonType.COLOR.valueTypes()));
    }

    public static StyleBagItem dimensionOrFraction(String str) {
        EncodeResult encodeResultEncode = ValueCoder.encode(str, ValueType.DIMENSION);
        if (encodeResultEncode == null) {
            encodeResultEncode = ValueCoder.encode(str, ValueType.FRACTION);
        }
        return encoded(encodeResultEncode);
    }

    public static StyleBagItem createFloat(float f) {
        return new StyleBagItem(ValueType.FLOAT, Float.floatToIntBits(f));
    }

    public static StyleBagItem enumOrFlag(AttributeBag attributeBag, String str) {
        return encoded(attributeBag.encodeEnumOrFlagValue(str));
    }
}
