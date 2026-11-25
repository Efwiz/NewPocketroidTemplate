package com.reandroid.arsc.value;

import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.collection.SingleIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class ResTableEntry extends TableEntry<EntryHeader, ResValue> {
    public static final String NAME_value = "value";

    public ResTableEntry() {
        super(new EntryHeader(), new ResValue());
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public Iterator<ValueItem> allValues() {
        return new SingleIterator(getValue());
    }

    @Override // com.reandroid.arsc.value.TableEntry
    void linkTableStringsInternal(TableStringPool tableStringPool) {
        getValue().linkTableStrings(tableStringPool);
    }

    @Override // com.reandroid.arsc.value.TableEntry
    void onRemoved() {
        getHeader().onRemoved();
        getValue().onRemoved();
    }

    @Override // com.reandroid.arsc.value.TableEntry
    boolean canMerge(TableEntry<?, ?> tableEntry) {
        ValueType valueType;
        if (tableEntry == this || !(tableEntry instanceof ResTableEntry) || (valueType = ((ResTableEntry) tableEntry).getValue().getValueType()) == null || valueType == ValueType.NULL) {
            return false;
        }
        ValueType valueType2 = getValue().getValueType();
        return valueType2 == null || valueType2 == ValueType.NULL;
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public void merge(TableEntry<?, ?> tableEntry) {
        if (tableEntry == this || !(tableEntry instanceof ResTableEntry)) {
            return;
        }
        getHeader().merge(tableEntry.getHeader());
        getValue().merge((ValueItem) tableEntry.getValue());
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public void mergeWithName(ResourceMergeOption resourceMergeOption, TableEntry<?, ?> tableEntry) {
        if (tableEntry == this || !(tableEntry instanceof ResTableEntry)) {
            return;
        }
        getHeader().mergeWithName(resourceMergeOption, tableEntry.getHeader());
        getValue().mergeWithName(resourceMergeOption, (ValueItem) tableEntry.getValue());
    }

    @Override // com.reandroid.arsc.value.TableEntry, com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        getHeader().toJson(jSONObject);
        jSONObject.put("value", getValue().toJson());
        return jSONObject;
    }

    @Override // com.reandroid.arsc.value.TableEntry, com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        getHeader().fromJson(jSONObject);
        getValue().fromJson(jSONObject.getJSONObject("value"));
    }
}
