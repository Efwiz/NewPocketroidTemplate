package com.reandroid.arsc.value.bag;

import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueType;

/* JADX INFO: loaded from: classes10.dex */
public abstract class BagItem {
    private final int data;
    protected final ResValueMap mBagItem;
    private final StringItem string;
    private final ValueType valueType;

    protected BagItem(ResValueMap resValueMap) {
        this.mBagItem = resValueMap;
        this.valueType = null;
        this.string = null;
        this.data = 0;
    }

    protected BagItem(ValueType valueType, int i) {
        if (valueType == ValueType.STRING) {
            throw new IllegalArgumentException("Use the string constructor instead");
        }
        this.mBagItem = null;
        this.string = null;
        this.valueType = valueType;
        this.data = i;
    }

    protected BagItem(StringItem stringItem) {
        this.string = stringItem;
        this.mBagItem = null;
        this.valueType = ValueType.STRING;
        this.data = 0;
    }

    public ValueType getValueType() {
        ResValueMap resValueMap = this.mBagItem;
        if (resValueMap != null) {
            return resValueMap.getValueType();
        }
        return this.valueType;
    }

    public int getValue() {
        ResValueMap resValueMap = this.mBagItem;
        if (resValueMap != null) {
            return resValueMap.getData();
        }
        if (this.valueType == ValueType.STRING) {
            return this.string.getIndex();
        }
        return this.data;
    }

    public void copyTo(ResValueMap resValueMap) {
        ResValueMap resValueMap2 = this.mBagItem;
        if (resValueMap2 != null) {
            resValueMap.setTypeAndData(resValueMap2.getValueType(), this.mBagItem.getData());
            return;
        }
        if (this.valueType == ValueType.STRING) {
            TableStringPool tableStringPool = (TableStringPool) resValueMap.getStringPool();
            if (tableStringPool == this.string.getParent(TableStringPool.class)) {
                resValueMap.setTypeAndData(ValueType.STRING, this.string.getIndex());
                return;
            } else {
                resValueMap.setTypeAndData(ValueType.STRING, tableStringPool.getOrCreate(this.string.get()).getIndex());
                return;
            }
        }
        resValueMap.setTypeAndData(this.valueType, this.data);
    }

    public ResValueMap getBagItem() {
        return this.mBagItem;
    }

    public boolean hasStringValue() {
        return getValueType() == ValueType.STRING;
    }

    public boolean hasReferenceValue() {
        return getValueType() == ValueType.REFERENCE;
    }

    public String getStringValue() {
        ResValueMap resValueMap = this.mBagItem;
        if (resValueMap != null) {
            return resValueMap.getValueAsString();
        }
        if (this.valueType == ValueType.STRING) {
            return this.string.getHtml();
        }
        throw new IllegalArgumentException("Not a string");
    }
}
