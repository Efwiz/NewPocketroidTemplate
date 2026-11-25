package com.reandroid.arsc.value.attribute;

import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.coder.EncodeResult;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.AttributeType;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public class AttributeBag {
    public static final short TYPE_ENUM = 1;
    public static final short TYPE_FLAG = 2;
    private final AttributeBagItem[] mBagItems;

    public AttributeBag(AttributeBagItem[] attributeBagItemArr) {
        this.mBagItems = attributeBagItemArr;
    }

    public boolean contains(AttributeDataFormat attributeDataFormat) {
        return getFormat().contains(attributeDataFormat);
    }

    public boolean isEqualType(AttributeDataFormat attributeDataFormat) {
        return getFormat().isEqualType(attributeDataFormat);
    }

    public boolean isCompatible(ValueType valueType) {
        return AttributeDataFormat.contains(getFormats(), valueType);
    }

    public EncodeResult encode(String str) {
        EncodeResult encodeResultEncode;
        EncodeResult encodeResultEncodeEnumOrFlagValue = encodeEnumOrFlagValue(str);
        if (encodeResultEncodeEnumOrFlagValue != null) {
            return encodeResultEncodeEnumOrFlagValue;
        }
        AttributeDataFormat[] formats = getFormats();
        if (formats != null && (encodeResultEncode = ValueCoder.encode(str, formats)) != null) {
            return encodeResultEncode;
        }
        if (isCompatible(ValueType.STRING)) {
            return new EncodeResult(ValueType.STRING, -1);
        }
        if (isEnumOrFlag()) {
            return new EncodeResult("Invalid attribute enum/flag/value");
        }
        return new EncodeResult("Incompatible attribute value, expected formats " + AttributeDataFormat.toString(formats));
    }

    public EncodeResult encodeEnumOrFlagValue(String str) {
        if (str == null || !isEnumOrFlag()) {
            return null;
        }
        EncodeResult encodeResultEncode = ValueCoder.encode(str, AttributeDataFormat.INTEGER);
        if (encodeResultEncode != null) {
            return encodeResultEncode;
        }
        boolean z = false;
        int data = 0;
        for (String str2 : str.split("[\\s|]+")) {
            String strTrim = str2.trim();
            AttributeBagItem attributeBagItemSearchByName = searchByName(strTrim);
            if (attributeBagItemSearchByName == null) {
                if (strTrim.length() != 0) {
                    return null;
                }
            } else {
                data = attributeBagItemSearchByName.getBagItem().getData() | data;
                z = true;
            }
        }
        if (z) {
            return new EncodeResult(isFlag() ? ValueType.HEX : ValueType.DEC, data);
        }
        return null;
    }

    public String decodeAttributeValue(int i) {
        return AttributeBagItem.toString(searchValue(i), false);
    }

    public AttributeBagItem searchByName(String str) {
        for (AttributeBagItem attributeBagItem : getBagItems()) {
            if (!attributeBagItem.isType() && str.equals(attributeBagItem.getNameOrHex())) {
                return attributeBagItem;
            }
        }
        return null;
    }

    public AttributeBagItem[] searchValue(int i) {
        if (isFlag()) {
            return searchFlagValue(i);
        }
        AttributeBagItem attributeBagItemSearchEnumValue = searchEnumValue(i);
        if (attributeBagItemSearchEnumValue != null) {
            return new AttributeBagItem[]{attributeBagItemSearchEnumValue};
        }
        return null;
    }

    private AttributeBagItem searchEnumValue(int i) {
        for (AttributeBagItem attributeBagItem : getBagItems()) {
            if (!attributeBagItem.isType() && i == attributeBagItem.getData()) {
                return attributeBagItem;
            }
        }
        return null;
    }

    private AttributeBagItem[] searchFlagValue(int i) {
        AttributeBagItem[] bagItems = getBagItems();
        AttributeBagItem[] attributeBagItemArr = new AttributeBagItem[bagItems.length];
        for (AttributeBagItem attributeBagItem : bagItems) {
            if (!attributeBagItem.isType()) {
                int data = attributeBagItem.getData();
                if ((i & data) != data) {
                    continue;
                } else {
                    if (i == data) {
                        return new AttributeBagItem[]{attributeBagItem};
                    }
                    int iIndexOf = indexOf(attributeBagItemArr, data);
                    if (iIndexOf >= 0) {
                        attributeBagItemArr[iIndexOf] = attributeBagItem;
                    }
                }
            }
        }
        return removeNull(attributeBagItemArr);
    }

    private int indexOf(AttributeBagItem[] attributeBagItemArr, int i) {
        int data;
        int i2 = 0;
        while (i2 < attributeBagItemArr.length) {
            AttributeBagItem attributeBagItem = attributeBagItemArr[i2];
            if (attributeBagItem == null || (data = attributeBagItem.getData()) == 0) {
                return i2;
            }
            int i3 = data & i;
            if (i3 == i) {
                return -1;
            }
            if (i3 == data) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    private AttributeBagItem[] removeNull(AttributeBagItem[] attributeBagItemArr) {
        int iCountNonNull = countNonNull(attributeBagItemArr);
        if (iCountNonNull == 0) {
            return null;
        }
        AttributeBagItem[] attributeBagItemArr2 = new AttributeBagItem[iCountNonNull];
        int i = 0;
        for (AttributeBagItem attributeBagItem : attributeBagItemArr) {
            if (attributeBagItem != null) {
                attributeBagItemArr2[i] = attributeBagItem;
                i++;
            }
        }
        return attributeBagItemArr2;
    }

    private int countNonNull(AttributeBagItem[] attributeBagItemArr) {
        if (attributeBagItemArr == null) {
            return 0;
        }
        int i = 0;
        for (AttributeBagItem attributeBagItem : attributeBagItemArr) {
            if (attributeBagItem != null) {
                i++;
            }
        }
        return i;
    }

    public AttributeBagItem[] getBagItems() {
        return this.mBagItems;
    }

    public AttributeDataFormat[] getFormats() {
        return getFormat().getDataFormats();
    }

    public AttributeBagItem getFormat() {
        AttributeBagItem attributeBagItemFind = find(AttributeType.FORMATS);
        return attributeBagItemFind == null ? getBagItems()[0] : attributeBagItemFind;
    }

    public AttributeBagItem find(AttributeType attributeType) {
        for (AttributeBagItem attributeBagItem : getBagItems()) {
            if (attributeBagItem.getType() == attributeType) {
                return attributeBagItem;
            }
        }
        return null;
    }

    public boolean isEnumOrFlag() {
        return isFlag() || isEnum();
    }

    public boolean isFlag() {
        return getFormat().isFlag();
    }

    public boolean isEnum() {
        return getFormat().isEnum();
    }

    public String toString() {
        AttributeBagItem[] bagItems = getBagItems();
        StringBuilder sb = new StringBuilder();
        Entry entry = getFormat().getBagItem().getEntry();
        if (entry != null) {
            sb.append(entry.getSpecString());
        }
        int length = bagItems.length;
        sb.append(", childes=").append(length);
        int i = 0;
        while (i < length) {
            AttributeBagItem attributeBagItem = bagItems[i];
            i++;
            sb.append("\n    [").append(i).append("]  ");
            sb.append(attributeBagItem.toString());
        }
        return sb.toString();
    }

    public static AttributeBag create(Entry entry) {
        if (entry != null) {
            return create(entry.getResValueMapArray());
        }
        return null;
    }

    public static AttributeBag create(ResValueMapArray resValueMapArray) {
        AttributeBagItem[] attributeBagItemArrCreate;
        if (resValueMapArray == null || resValueMapArray.size() == 0 || (attributeBagItemArrCreate = AttributeBagItem.create(resValueMapArray.getChildes())) == null) {
            return null;
        }
        return new AttributeBag(attributeBagItemArrCreate);
    }

    public static boolean isAttribute(ResTableMapEntry resTableMapEntry) {
        return (resTableMapEntry == null || AttributeBagItem.create(resTableMapEntry.listResValueMap()) == null) ? false : true;
    }
}
