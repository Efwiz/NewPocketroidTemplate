package com.reandroid.arsc.value.plurals;

import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.item.TableString;
import com.reandroid.arsc.value.AttributeType;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ResValue;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.bag.BagItem;
import com.reandroid.utils.HexUtil;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class PluralsBagItem extends BagItem {
    private PluralsBagItem(ResValueMap resValueMap) {
        super(resValueMap);
    }

    private PluralsBagItem(StringItem stringItem) {
        super(stringItem);
    }

    private PluralsBagItem(ValueType valueType, int i) {
        super(valueType, i);
    }

    public AttributeType getQuantity() {
        AttributeType attributeType;
        if (this.mBagItem == null || (attributeType = this.mBagItem.getAttributeType()) == null || !attributeType.isPlural()) {
            return null;
        }
        return attributeType;
    }

    /* JADX INFO: renamed from: com.reandroid.arsc.value.plurals.PluralsBagItem$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$reandroid$arsc$value$ValueType;

        static {
            int[] iArr = new int[ValueType.values().length];
            $SwitchMap$com$reandroid$arsc$value$ValueType = iArr;
            try {
                iArr[ValueType.STRING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$reandroid$arsc$value$ValueType[ValueType.REFERENCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0048  */
    public String getQualityString(ResConfig resConfig) {
        Entry entry;
        int i = AnonymousClass1.$SwitchMap$com$reandroid$arsc$value$ValueType[getValueType().ordinal()];
        if (i == 1) {
            return getStringValue();
        }
        if (i == 2) {
            Entry entry2 = this.mBagItem != null ? this.mBagItem.getEntry() : null;
            if (entry2 == null) {
                return null;
            }
            if (resConfig == null) {
                resConfig = entry2.getResConfig();
            }
            if (resConfig != null) {
                List<Entry> listResolveReferenceWithConfig = entry2.getPackageBlock().getTableBlock().resolveReferenceWithConfig(getValue(), resConfig);
                if (listResolveReferenceWithConfig.size() > 0) {
                    entry = listResolveReferenceWithConfig.get(0);
                } else {
                    entry = null;
                }
            } else {
                entry = null;
            }
            if (entry == null) {
                return null;
            }
            ResValue resValue = entry.getResValue();
            if (resValue == null || resValue.getValueType() != ValueType.STRING) {
                throw new IllegalArgumentException("Not a STR reference: " + formattedRefValue());
            }
            return resValue.getValueAsString();
        }
        throw new IllegalArgumentException("Not STR/REFERENCE ValueType=" + getValueType());
    }

    private String formattedRefValue() {
        return HexUtil.toHex8("@0x", getValue());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("<item quantity=\"");
        sb.append(getQuantity());
        sb.append("\">");
        if (hasStringValue()) {
            sb.append(getStringValue());
        } else {
            sb.append(formattedRefValue());
        }
        sb.append("</item>");
        return sb.toString();
    }

    protected static PluralsBagItem create(ResValueMap resValueMap) {
        if (resValueMap == null) {
            return null;
        }
        return new PluralsBagItem(resValueMap);
    }

    protected static PluralsBagItem copyOf(ResValueMap resValueMap) {
        ValueType valueType = resValueMap.getValueType();
        if (valueType == ValueType.STRING) {
            return new PluralsBagItem(resValueMap.getDataAsPoolString());
        }
        return new PluralsBagItem(valueType, resValueMap.getData());
    }

    public static PluralsBagItem string(TableString tableString) {
        if (tableString == null) {
            return null;
        }
        return new PluralsBagItem(tableString);
    }

    public static PluralsBagItem reference(int i) {
        return new PluralsBagItem(ValueType.REFERENCE, i);
    }
}
