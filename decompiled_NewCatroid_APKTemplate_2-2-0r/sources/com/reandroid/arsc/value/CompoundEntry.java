package com.reandroid.arsc.value;

import com.reandroid.arsc.array.CompoundItemArray;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.coder.ValueCoder;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.value.ResValueMap;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.collection.ComputeIterator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class CompoundEntry<ITEM extends ResValueMap, ARRAY extends CompoundItemArray<ITEM>> extends TableEntry<EntryHeaderMap, ARRAY> implements Iterable<ITEM> {
    public static final String NAME_values = "values";

    static /* synthetic */ ValueItem lambda$allValues$0(ResValueMap resValueMap) {
        return resValueMap;
    }

    public CompoundEntry(ARRAY array) {
        super(new EntryHeaderMap(), array);
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public Iterator<ValueItem> allValues() {
        return new ComputeIterator(iterator(), new Function() { // from class: com.reandroid.arsc.value.CompoundEntry$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return CompoundEntry.lambda$allValues$0((ResValueMap) obj);
            }
        });
    }

    @Override // java.lang.Iterable
    public Iterator<ITEM> iterator() {
        return getValue().iterator();
    }

    public AttributeDataFormat[] getAttributeTypeFormats() {
        ResValueMap byType = getByType(AttributeType.FORMATS);
        if (byType != null) {
            return byType.getAttributeTypeFormats();
        }
        return null;
    }

    public boolean containsType(AttributeType attributeType) {
        return getValue().containsType(attributeType);
    }

    public ITEM getByType(AttributeType attributeType) {
        return (ITEM) getValue().getByType(attributeType);
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public void refresh() {
        getHeader().setValuesCount(getValue().size());
    }

    public List<ITEM> listResValueMap() {
        return getValue().getChildes();
    }

    public String decodeParentId() {
        int parentId = getParentId();
        if (parentId == 0) {
            return null;
        }
        return ValueCoder.decodeReference(getPackageBlock(), ValueType.REFERENCE, parentId);
    }

    public int getParentId() {
        return getHeader().getParentId();
    }

    public void setParentId(int i) {
        getHeader().setParentId(i);
    }

    public int childesCount() {
        return getValue().size();
    }

    public void setValuesCount(int i) {
        getHeader().setValuesCount(i);
        getValue().setSize(i);
    }

    public ResourceEntry resolveParentId() {
        PackageBlock packageBlock;
        TableBlock tableBlock;
        int parentId = getParentId();
        if (parentId == 0 || (packageBlock = getPackageBlock()) == null || (tableBlock = packageBlock.getTableBlock()) == null) {
            return null;
        }
        return tableBlock.getResource(packageBlock, parentId);
    }

    public PackageBlock getPackageBlock() {
        Entry parentEntry = getParentEntry();
        if (parentEntry != null) {
            return parentEntry.getPackageBlock();
        }
        return null;
    }

    @Override // com.reandroid.arsc.value.TableEntry
    void linkTableStringsInternal(TableStringPool tableStringPool) {
        Iterator<ITEM> it = listResValueMap().iterator();
        while (it.hasNext()) {
            it.next().linkTableStrings(tableStringPool);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.arsc.value.TableEntry
    public void onHeaderLoaded(ARRAY array, EntryHeaderMap entryHeaderMap) {
        array.setSize(entryHeaderMap.getValuesCount());
    }

    @Override // com.reandroid.arsc.value.TableEntry
    void onRemoved() {
        getHeader().onRemoved();
        getValue().onRemoved();
    }

    @Override // com.reandroid.arsc.value.TableEntry, com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        getHeader().toJson(jSONObject);
        jSONObject.put(NAME_values, getValue().toJson());
        return jSONObject;
    }

    @Override // com.reandroid.arsc.value.TableEntry, com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        getHeader().fromJson(jSONObject);
        getValue().fromJson(jSONObject.optJSONArray(NAME_values));
        refresh();
    }

    @Override // com.reandroid.arsc.value.TableEntry
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getHeader());
        List<ITEM> listListResValueMap = listResValueMap();
        int size = listListResValueMap.size();
        int i = size <= 4 ? size : 4;
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("\n    ");
            sb.append(listListResValueMap.get(i2));
        }
        if (size > 0) {
            if (i != size) {
                sb.append("\n    ...");
            }
            sb.append("\n   ");
        }
        return sb.toString();
    }
}
