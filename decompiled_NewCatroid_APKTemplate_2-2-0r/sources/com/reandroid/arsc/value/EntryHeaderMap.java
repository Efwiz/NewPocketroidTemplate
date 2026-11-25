package com.reandroid.arsc.value;

import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes10.dex */
public class EntryHeaderMap extends ValueHeader {
    private static final short HEADER_SIZE_COMPLEX = 16;
    public static final String NAME_parent_id = "parent_id";
    private static final int OFFSET_PARENT_ID = 8;
    private static final int OFFSET_VALUE_COUNT = 12;

    public EntryHeaderMap() {
        super(16);
        setComplex(true);
    }

    public ResourceEntry resolveParentId() {
        Entry entry = (Entry) getParentInstance(Entry.class);
        if (entry != null) {
            return entry.resolve(getParentId());
        }
        return null;
    }

    public int getParentId() {
        return getInteger(getBytesInternal(), 8);
    }

    public void setParentId(int i) {
        putInteger(getBytesInternal(), 8, i);
    }

    public int getValuesCount() {
        return getInteger(getBytesInternal(), 12);
    }

    public void setValuesCount(int i) {
        putInteger(getBytesInternal(), 12, i);
    }

    @Override // com.reandroid.arsc.value.ValueHeader
    public void merge(ValueHeader valueHeader) {
        if (valueHeader == this || !(valueHeader instanceof EntryHeaderMap)) {
            return;
        }
        super.merge(valueHeader);
        EntryHeaderMap entryHeaderMap = (EntryHeaderMap) valueHeader;
        setParentId(entryHeaderMap.getParentId());
        setValuesCount(entryHeaderMap.getValuesCount());
    }

    @Override // com.reandroid.arsc.value.ValueHeader
    public void mergeWithName(ResourceMergeOption resourceMergeOption, ValueHeader valueHeader) {
        int resourceId;
        if (valueHeader == this || !(valueHeader instanceof EntryHeaderMap)) {
            return;
        }
        super.merge(valueHeader);
        EntryHeaderMap entryHeaderMap = (EntryHeaderMap) valueHeader;
        setValuesCount(entryHeaderMap.getValuesCount());
        ResourceEntry resourceEntryResolveParentId = entryHeaderMap.resolveParentId();
        if (resourceEntryResolveParentId == null) {
            setParentId(entryHeaderMap.getParentId());
            return;
        }
        if (resourceEntryResolveParentId.isContext(entryHeaderMap)) {
            ResourceEntry resourceEntryMergeWithName = ((PackageBlock) getParentInstance(PackageBlock.class)).mergeWithName(resourceMergeOption, resourceEntryResolveParentId);
            resourceId = resourceEntryMergeWithName != null ? resourceEntryMergeWithName.getResourceId() : 0;
        } else {
            resourceId = resourceEntryResolveParentId.getResourceId();
        }
        setParentId(resourceId);
    }

    @Override // com.reandroid.arsc.value.ValueHeader
    public void toJson(JSONObject jSONObject) {
        super.toJson(jSONObject);
        jSONObject.put(ValueHeader.NAME_is_complex, true);
        int parentId = getParentId();
        if (parentId != 0) {
            jSONObject.put(NAME_parent_id, parentId);
        }
    }

    @Override // com.reandroid.arsc.value.ValueHeader, com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        super.fromJson(jSONObject);
        setComplex(jSONObject.optBoolean(ValueHeader.NAME_is_complex, true));
        setParentId(jSONObject.optInt(NAME_parent_id));
    }

    @Override // com.reandroid.arsc.value.ValueHeader
    public String toString() {
        if (isNull()) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int size = getSize();
        int size2 = readSize();
        if (size != 16) {
            sb.append("size=").append(size);
        }
        if (size != size2) {
            sb.append(", readSize=").append(size2);
        }
        if (isComplex()) {
            sb.append(" complex");
        }
        if (isPublic()) {
            sb.append(", public");
        }
        if (isWeak()) {
            sb.append(", weak");
        }
        String name2 = getName();
        if (name2 != null) {
            sb.append(", name=").append(name2);
        } else {
            sb.append(", key=").append(getKey());
        }
        if (getParentId() != 0) {
            sb.append(", parentId=");
            sb.append(HexUtil.toHex8(getParentId()));
        }
        sb.append(", count=").append(getValuesCount());
        return sb.toString();
    }
}
