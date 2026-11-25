package com.reandroid.arsc.value.attribute;

import com.huawei.hms.framework.common.ContainerUtils;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.AttributeType;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.utils.HexUtil;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class AttributeBagItem {
    private final ResValueMap mBagItem;

    public AttributeBagItem(ResValueMap resValueMap) {
        this.mBagItem = resValueMap;
    }

    public int getData() {
        return getBagItem().getData();
    }

    public String getNameOrHex() {
        String name2 = getName();
        return name2 == null ? ValueCoder.decodeUnknownNameId(getBagItem().getNameId()) : name2;
    }

    public String getName() {
        ResourceEntry resourceEntryResolveName;
        if (isType() || (resourceEntryResolveName = getBagItem().resolveName()) == null) {
            return null;
        }
        return resourceEntryResolveName.getName();
    }

    public ResValueMap getBagItem() {
        return this.mBagItem;
    }

    public AttributeType getType() {
        return getBagItem().getAttributeType();
    }

    public boolean isFormats() {
        return getType() == AttributeType.FORMATS;
    }

    public boolean isType() {
        return getType() != null;
    }

    public boolean contains(AttributeDataFormat attributeDataFormat) {
        if (attributeDataFormat == null || !isFormats()) {
            return false;
        }
        return attributeDataFormat.matches(getBagItem().getData());
    }

    public boolean isEqualType(AttributeDataFormat attributeDataFormat) {
        return attributeDataFormat != null && isFormats() && attributeDataFormat.getMask() == getBagItem().getData();
    }

    public AttributeDataFormat[] getDataFormats() {
        if (isFormats()) {
            return AttributeDataFormat.decodeValueTypes(getBagItem().getData());
        }
        return null;
    }

    public boolean isEnum() {
        if (isFormats()) {
            return AttributeDataFormat.ENUM.matches(getBagItem().getData());
        }
        return false;
    }

    public boolean isFlag() {
        if (isFormats()) {
            return AttributeDataFormat.FLAG.matches(getBagItem().getData());
        }
        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        ResValueMap bagItem = getBagItem();
        sb.append(getNameOrHex());
        sb.append(ContainerUtils.KEY_VALUE_DELIMITER).append(HexUtil.toHex8(bagItem.getData()));
        return sb.toString();
    }

    public static String toString(AttributeBagItem[] attributeBagItemArr) {
        return toString(attributeBagItemArr, false);
    }

    public static String toString(AttributeBagItem[] attributeBagItemArr, boolean z) {
        String name2;
        if (attributeBagItemArr == null || (attributeBagItemArr.length) == 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean z2 = false;
        for (AttributeBagItem attributeBagItem : attributeBagItemArr) {
            if (attributeBagItem != null) {
                if (z2) {
                    sb.append("|");
                }
                if (z) {
                    name2 = attributeBagItem.getNameOrHex();
                } else {
                    name2 = attributeBagItem.getName();
                }
                if (name2 == null) {
                    return null;
                }
                sb.append(name2);
                z2 = true;
            }
        }
        if (z2) {
            return sb.toString();
        }
        return null;
    }

    public static AttributeBagItem[] create(List<ResValueMap> list) {
        if (list == null) {
            return null;
        }
        int size = list.size();
        AttributeBagItem[] attributeBagItemArr = new AttributeBagItem[size];
        AttributeBagItem attributeBagItem = null;
        for (int i = 0; i < size; i++) {
            AttributeBagItem attributeBagItem2 = new AttributeBagItem(list.get(i));
            attributeBagItemArr[i] = attributeBagItem2;
            if (attributeBagItem == null && AttributeType.FORMATS == attributeBagItem2.getType()) {
                attributeBagItem = attributeBagItem2;
            }
        }
        if (attributeBagItem != null) {
            return attributeBagItemArr;
        }
        return null;
    }
}
