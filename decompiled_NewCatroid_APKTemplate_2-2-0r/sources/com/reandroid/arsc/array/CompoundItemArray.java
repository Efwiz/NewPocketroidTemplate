package com.reandroid.arsc.array;

import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.arsc.value.AttributeDataFormat;
import com.reandroid.arsc.value.AttributeType;
import com.reandroid.arsc.value.ResTableMapEntry;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.arsc.value.ValueItem;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public abstract class CompoundItemArray<T extends ResValueMap> extends BlockArray<T> implements JSONConvert<JSONArray>, Comparator<ResValueMap> {
    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    public void sort() {
        super.sort(this);
        updateCountToHeader();
    }

    @Override // com.reandroid.arsc.container.BlockList
    public T createNext() {
        T t = (T) super.createNext();
        updateCountToHeader();
        return t;
    }

    private void updateCountToHeader() {
        ((ResTableMapEntry) getParent(ResTableMapEntry.class)).getHeader().setValuesCount(size());
    }

    public AttributeDataFormat[] getFormats() {
        ResValueMap byType = getByType(AttributeType.FORMATS);
        if (byType != null) {
            return AttributeDataFormat.decodeValueTypes(byType.getData() & 255);
        }
        return null;
    }

    public boolean containsType(AttributeType attributeType) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            if (attributeType == it.next().getAttributeType()) {
                return true;
            }
        }
        return false;
    }

    public T getOrCreateType(AttributeType attributeType) {
        if (attributeType == null) {
            return null;
        }
        T t = (T) getByType(attributeType);
        if (t != null) {
            return t;
        }
        T t2 = (T) createNext();
        t2.setAttributeType(attributeType);
        return t2;
    }

    public T getByType(AttributeType attributeType) {
        if (attributeType == null) {
            return null;
        }
        for (T t : this) {
            if (attributeType == t.getAttributeType()) {
                return t;
            }
        }
        return null;
    }

    public T getByName(int i) {
        for (T t : this) {
            if (t != null && i == t.getNameId()) {
                return t;
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        updateCountToHeader();
    }

    public void onRemoved() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            it.next().onRemoved();
        }
    }

    @Override // com.reandroid.arsc.base.BlockArray
    public void clear() {
        onRemoved();
        super.clear();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        JSONArray jSONArray = new JSONArray();
        if (isNull()) {
            return jSONArray;
        }
        Iterator<T> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            jSONArray.put(i, it.next().toJson());
            i++;
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        clear();
        if (jSONArray == null) {
            return;
        }
        int length = jSONArray.length();
        ensureSize(length);
        for (int i = 0; i < length; i++) {
            ((ResValueMap) get(i)).fromJson(jSONArray.getJSONObject(i));
        }
        updateCountToHeader();
    }

    public void merge(CompoundItemArray<?> compoundItemArray) {
        if (compoundItemArray == null || compoundItemArray == this) {
            return;
        }
        clear();
        int size = compoundItemArray.size();
        ensureSize(size);
        for (int i = 0; i < size; i++) {
            ((ResValueMap) get(i)).merge((ValueItem) compoundItemArray.get(i));
        }
        updateCountToHeader();
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, CompoundItemArray<?> compoundItemArray) {
        if (compoundItemArray == null || compoundItemArray == this) {
            return;
        }
        clear();
        int size = compoundItemArray.size();
        ensureSize(size);
        for (int i = 0; i < size; i++) {
            ((ResValueMap) get(i)).mergeWithName(resourceMergeOption, (ValueItem) compoundItemArray.get(i));
        }
        updateCountToHeader();
    }

    @Override // java.util.Comparator
    public int compare(ResValueMap resValueMap, ResValueMap resValueMap2) {
        if (resValueMap == resValueMap2) {
            return 0;
        }
        if (resValueMap == null) {
            return 1;
        }
        return resValueMap.compareTo(resValueMap2);
    }
}
