package com.reandroid.arsc.array;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.pool.SpecStringPool;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ValueType;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class EntryArray extends OffsetBlockArray<Entry> implements JSONConvert<JSONArray> {
    public EntryArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2) {
        super(offsetArray, integerItem, integerItem2);
    }

    public void linkTableStringsInternal(TableStringPool tableStringPool) {
        Iterator<T> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            ((Entry) it.next()).linkTableStringsInternal(tableStringPool);
        }
    }

    public void linkSpecStringsInternal(SpecStringPool specStringPool) {
        Iterator<T> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            ((Entry) it.next()).linkSpecStringsInternal(specStringPool);
        }
    }

    public int getHighestEntryId() {
        if (isSparse()) {
            return ((SparseOffsetsArray) getOffsetArray()).getHighestId();
        }
        return size() - 1;
    }

    public int getEntryId(int i) {
        OffsetArray offsetArray = getOffsetArray();
        return offsetArray instanceof SparseOffsetsArray ? ((SparseOffsetsArray) offsetArray).getIdx(i) : i;
    }

    public int getEntryIndex(int i) {
        OffsetArray offsetArray = getOffsetArray();
        return offsetArray instanceof SparseOffsetsArray ? ((SparseOffsetsArray) offsetArray).indexOf(i) : i;
    }

    public boolean isSparse() {
        return super.getOffsetArray() instanceof SparseOffsetsArray;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void destroy() {
        for (T t : listItems()) {
            if (t != null) {
                t.setNull(true);
            }
        }
        clear();
    }

    public Boolean hasComplexEntry() {
        Iterator<T> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            if (entry.isComplex()) {
                return true;
            }
            ValueType valueType = entry.getResValue().getValueType();
            if (valueType != null && valueType != ValueType.REFERENCE && valueType != ValueType.NULL) {
                return false;
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.array.OffsetBlockArray
    public boolean isEmpty() {
        return true ^ m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true).hasNext();
    }

    public Entry getOrCreate(short s) {
        int i = s & 65535;
        Entry entry = getEntry(i);
        if (entry != null) {
            return entry;
        }
        boolean zIsSparse = isSparse();
        int size = zIsSparse ? size() + 1 : i + 1;
        updateHighestCount(size);
        if (!zIsSparse) {
            refreshCount();
            return (Entry) super.get(i);
        }
        SparseOffsetsArray sparseOffsetsArray = (SparseOffsetsArray) getOffsetArray();
        sparseOffsetsArray.ensureArraySize(size());
        int i2 = size - 1;
        sparseOffsetsArray.setIdx(i2, i);
        refreshCount();
        return (Entry) super.get(i2);
    }

    public Entry get(short s) {
        return getEntry(s);
    }

    public Entry getEntry(short s) {
        return getEntry(s & 65535);
    }

    public Entry getEntry(int i) {
        return (Entry) super.get(getEntryIndex(i));
    }

    public Entry getEntry(String str) {
        TypeBlock typeBlock;
        PackageBlock packageBlock;
        if (str == null || (typeBlock = (TypeBlock) getParentInstance(TypeBlock.class)) == null || (packageBlock = typeBlock.getPackageBlock()) == null) {
            return null;
        }
        Iterator<Entry> entries = packageBlock.getEntries(typeBlock.getTypeName(), str);
        while (entries.hasNext()) {
            Entry next = entries.next();
            if (next.getParentInstance(EntryArray.class) == this) {
                return next;
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public Entry newInstance() {
        return new Entry();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public Entry[] newArrayInstance(int i) {
        return new Entry[i];
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        JSONArray jSONArray = new JSONArray();
        Iterator<T> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            JSONObject json = entry.toJson();
            if (json != null) {
                json.put("id", entry.getId());
                jSONArray.put(json);
            }
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        clear();
        if (isSparse()) {
            fromJsonSparse(jSONArray);
        } else {
            fromJsonNonSparse(jSONArray);
        }
        refreshCountAndStart();
    }

    private void fromJsonNonSparse(JSONArray jSONArray) {
        int length = jSONArray.length();
        ensureSize(length);
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                int i2 = jSONObjectOptJSONObject.getInt("id");
                ensureSize(i2 + 1);
                ((Entry) super.get(i2)).fromJson(jSONObjectOptJSONObject);
            }
        }
    }

    private void fromJsonSparse(JSONArray jSONArray) {
        SparseOffsetsArray sparseOffsetsArray = (SparseOffsetsArray) getOffsetArray();
        sparseOffsetsArray.setSize(0);
        int length = jSONArray.length();
        ensureSize(length);
        sparseOffsetsArray.setSize(length);
        for (int i = 0; i < length; i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(i);
            if (jSONObjectOptJSONObject == null) {
                sparseOffsetsArray.setIdx(i, -1);
            } else {
                int i2 = jSONObjectOptJSONObject.getInt("id");
                Entry entry = (Entry) super.get(i);
                sparseOffsetsArray.setIdx(i, i2);
                entry.fromJson(jSONObjectOptJSONObject);
            }
        }
    }

    public void merge(EntryArray entryArray) {
        if (entryArray == null || entryArray == this || entryArray.isEmpty()) {
            return;
        }
        if (isSparse()) {
            mergeSparse(entryArray);
        } else {
            mergeNonSparse(entryArray);
        }
        refreshCountAndStart();
    }

    private void mergeSparse(EntryArray entryArray) {
        Iterator<T> it = entryArray.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            getOrCreate((short) entry.getId()).merge(entry);
        }
    }

    private void mergeNonSparse(EntryArray entryArray) {
        ensureSize(entryArray.size());
        Iterator<T> it = entryArray.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            ((Entry) super.get(entry.getIndex())).merge(entry);
        }
    }

    private void updateHighestCount(int i) {
        SpecTypePair specTypePair = (SpecTypePair) getParentInstance(SpecTypePair.class);
        if (specTypePair == null) {
            ensureSize(i);
            return;
        }
        int highestEntryCount = specTypePair.getHighestEntryCount();
        if (i <= highestEntryCount) {
            i = highestEntryCount;
        }
        ensureSize(i);
    }

    @Override // com.reandroid.arsc.array.OffsetBlockArray, com.reandroid.arsc.container.BlockList
    public String toString() {
        return getClass().getSimpleName() + ": size=" + size();
    }
}
