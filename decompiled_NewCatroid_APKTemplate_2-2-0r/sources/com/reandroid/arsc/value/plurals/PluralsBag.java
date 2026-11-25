package com.reandroid.arsc.value.plurals;

import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.value.AttributeType;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.arsc.value.bag.MapBag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/* JADX INFO: loaded from: classes10.dex */
public class PluralsBag extends MapBag<AttributeType, PluralsBagItem> {
    private static final Set<ValueType> validTypes = new HashSet(Arrays.asList(ValueType.NULL, ValueType.STRING, ValueType.REFERENCE));

    private PluralsBag(Entry entry) {
        super(entry);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public PluralsBagItem createBagItem(ResValueMap resValueMap, boolean z) {
        if (z) {
            return PluralsBagItem.copyOf(resValueMap);
        }
        return PluralsBagItem.create(resValueMap);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public ResValueMap newKey(AttributeType attributeType) {
        ResValueMap resValueMap;
        ResValueMapArray mapArray = getMapArray();
        if (mapArray != null) {
            resValueMap = mapArray.createNext();
        } else {
            resValueMap = new ResValueMap();
        }
        resValueMap.setAttributeType(attributeType);
        return resValueMap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.reandroid.arsc.value.bag.MapBag
    public AttributeType getKeyFor(ResValueMap resValueMap) {
        AttributeType attributeType;
        if (resValueMap == null || (attributeType = resValueMap.getAttributeType()) == null || !attributeType.isPlural()) {
            return null;
        }
        return attributeType;
    }

    public String getQuantityString(AttributeType attributeType, ResConfig resConfig) {
        PluralsBagItem pluralsBagItem = (PluralsBagItem) get(attributeType);
        if (pluralsBagItem == null) {
            return null;
        }
        return pluralsBagItem.getQualityString(resConfig);
    }

    public String getQuantityString(AttributeType attributeType) {
        return getQuantityString(attributeType, null);
    }

    public void setQuantityString(AttributeType attributeType, String str) {
        if (attributeType == null || str == null) {
            return;
        }
        put(attributeType, PluralsBagItem.string(getStringPool().getOrCreate(str)));
    }

    @Override // java.util.AbstractMap
    public String toString() {
        StringBuilder sb = new StringBuilder("<");
        String typeName = getTypeName();
        sb.append(typeName);
        sb.append(" name=\"");
        sb.append(getName());
        sb.append("\">");
        for (PluralsBagItem pluralsBagItem : values()) {
            sb.append("\n    ");
            sb.append(pluralsBagItem.toString());
        }
        sb.append("\n</");
        sb.append(typeName);
        sb.append(">");
        return sb.toString();
    }

    public static boolean isPlurals(Entry entry) {
        AttributeType attributeType;
        PluralsBag pluralsBagCreate = create(entry);
        if (pluralsBagCreate == null) {
            return false;
        }
        ResValueMapArray<ResValueMap> mapArray = pluralsBagCreate.getMapArray();
        if (mapArray.size() == 0) {
            return false;
        }
        for (ResValueMap resValueMap : mapArray) {
            if (resValueMap == null || !validTypes.contains(resValueMap.getValueType()) || (attributeType = resValueMap.getAttributeType()) == null || !attributeType.isPlural()) {
                return false;
            }
        }
        return true;
    }

    public static PluralsBag create(Entry entry) {
        if (entry == null || !entry.isComplex()) {
            return null;
        }
        return new PluralsBag(entry);
    }
}
