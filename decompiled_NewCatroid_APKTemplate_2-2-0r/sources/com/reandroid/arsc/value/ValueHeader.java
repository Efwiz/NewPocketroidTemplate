package com.reandroid.arsc.value;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.ParentChunk;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.arsc.item.ReferenceItem;
import com.reandroid.arsc.item.SpecString;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.pool.SpecStringPool;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class ValueHeader extends BlockItem implements JSONConvert<JSONObject> {
    public static final String NAME_entry_name = "entry_name";
    public static final String NAME_is_complex = "is_complex";
    public static final String NAME_is_public = "is_public";
    public static final String NAME_is_weak = "is_weak";
    private static final int OFFSET_DATA_TYPE = 3;
    private static final int OFFSET_FLAGS = 2;
    private static final int OFFSET_SIZE = 0;
    private static final int OFFSET_SPEC_REFERENCE = 4;
    private ReferenceItem mStringReference;

    public ValueHeader(int i) {
        super(i);
        writeSize();
        putInteger(getBytesInternal(), 4, -1);
    }

    void linkSpecStringsInternal(SpecStringPool specStringPool) {
        SpecString specString = specStringPool.get(getKey());
        if (specString == null) {
            this.mStringReference = null;
            return;
        }
        ReferenceItem referenceItem = this.mStringReference;
        if (referenceItem != null) {
            specString.removeReference(referenceItem);
        }
        ValueHeaderReference valueHeaderReference = new ValueHeaderReference(this);
        this.mStringReference = valueHeaderReference;
        specString.addReference(valueHeaderReference);
    }

    public void onRemoved() {
        unLinkStringReference();
    }

    public String getName() {
        StringItem nameString = getNameString();
        if (nameString != null) {
            return nameString.get();
        }
        return null;
    }

    public boolean isComplex() {
        return getBit(getBytesInternal(), 2, 0);
    }

    public void setComplex(boolean z) {
        putBit(getBytesInternal(), 2, 0, z);
    }

    public void setPublic(boolean z) {
        putBit(getBytesInternal(), 2, 1, z);
    }

    public boolean isPublic() {
        return getBit(getBytesInternal(), 2, 1);
    }

    public void setWeak(boolean z) {
        putBit(getBytesInternal(), 2, 2, z);
    }

    public boolean isWeak() {
        return getBit(getBytesInternal(), 2, 2);
    }

    void setCompact(boolean z) {
        if (z == isCompact()) {
            return;
        }
        int key = getKey();
        putBit(getBytesInternal(), 2, 3, z);
        writeKey(key, z);
    }

    public boolean isCompact() {
        return getBit(getBytesInternal(), 2, 3);
    }

    public int getKey() {
        if (isCompact()) {
            return getShortUnsigned(getBytesInternal(), 0);
        }
        return getData();
    }

    public void setKey(int i) {
        if (i == getKey()) {
            return;
        }
        unLinkStringReference();
        writeKey(i);
        linkStringReference();
    }

    void writeKey(int i) {
        writeKey(i, isCompact());
    }

    private void writeKey(int i, boolean z) {
        if (z) {
            putShort(getBytesInternal(), 0, i);
        } else {
            setData(i);
        }
    }

    int getData() {
        return getInteger(getBytesInternal(), 4);
    }

    void setData(int i) {
        putInteger(getBytesInternal(), 4, i);
    }

    byte getType() {
        return getBytesInternal()[3];
    }

    void setType(byte b) {
        getBytesInternal()[3] = b;
    }

    public void setKey(StringItem stringItem) {
        if (ignoreUpdateKey(stringItem)) {
            return;
        }
        unLinkStringReference();
        writeKey(stringItem != null ? stringItem.getIndex() : -1);
        linkStringReference(stringItem);
    }

    private boolean ignoreUpdateKey(StringItem stringItem) {
        int key = getKey();
        ReferenceItem referenceItem = this.mStringReference;
        if (stringItem == null) {
            return referenceItem == null && key == -1;
        }
        return referenceItem != null && key == stringItem.getIndex() && getSpecString(key) == stringItem;
    }

    public void setSize(int i) {
        if (isCompact()) {
            return;
        }
        super.setBytesLength(i, false);
        writeSize();
    }

    public int getSize() {
        return getBytesInternal().length;
    }

    int readSize() {
        if (getSize() < 2) {
            return 0;
        }
        return getShort(getBytesInternal(), 0) & 65535;
    }

    private void writeSize() {
        int size = getSize();
        if (size > 1) {
            putShort(getBytesInternal(), 0, (short) size);
        }
    }

    private void linkStringReference() {
        StringPool<?> specStringPool = getSpecStringPool();
        if (specStringPool == null || specStringPool.isStringLinkLocked()) {
            return;
        }
        linkStringReference(specStringPool.get(getKey()));
    }

    private void linkStringReference(StringItem stringItem) {
        unLinkStringReference();
        if (stringItem == null) {
            return;
        }
        ValueHeaderReference valueHeaderReference = new ValueHeaderReference(this);
        this.mStringReference = valueHeaderReference;
        stringItem.addReference(valueHeaderReference);
    }

    private void unLinkStringReference() {
        ReferenceItem referenceItem = this.mStringReference;
        if (referenceItem == null) {
            return;
        }
        this.mStringReference = null;
        StringItem nameString = getNameString();
        if (nameString == null) {
            return;
        }
        nameString.removeReference(referenceItem);
    }

    public StringItem getNameString() {
        return getSpecString(getKey());
    }

    private StringItem getSpecString(int i) {
        StringPool<?> specStringPool;
        if (i >= 0 && (specStringPool = getSpecStringPool()) != null) {
            return specStringPool.get(i);
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private StringPool<?> getSpecStringPool() {
        for (Block parent = getParent(); parent != 0; parent = parent.getParent()) {
            if (parent instanceof ParentChunk) {
                return ((ParentChunk) parent).getSpecStringPool();
            }
        }
        return null;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        blockReader.readFully(getBytesInternal());
        if (isCompact()) {
            return;
        }
        blockReader.seek(position);
        setBytesLength(blockReader.readUnsignedShort(), false);
        blockReader.readFully(getBytesInternal());
    }

    private void setName(String str) {
        if (str == null) {
            str = "";
        }
        StringPool<?> specStringPool = getSpecStringPool();
        if (specStringPool == null) {
            return;
        }
        setKey(specStringPool.getOrCreate(str));
    }

    public void merge(ValueHeader valueHeader) {
        if (valueHeader == null || valueHeader == this) {
            return;
        }
        setComplex(valueHeader.isComplex());
        setWeak(valueHeader.isWeak());
        setPublic(valueHeader.isPublic());
        setName(valueHeader.getName());
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, ValueHeader valueHeader) {
        merge(valueHeader);
    }

    public void toJson(JSONObject jSONObject) {
        jSONObject.put(NAME_entry_name, getName());
        if (isWeak()) {
            jSONObject.put(NAME_is_weak, true);
        }
        if (isPublic()) {
            jSONObject.put(NAME_is_public, true);
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        toJson(jSONObject);
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setWeak(jSONObject.optBoolean(NAME_is_weak, false));
        setPublic(jSONObject.optBoolean(NAME_is_public, false));
        setName(jSONObject.optString(NAME_entry_name));
    }

    public String toString() {
        if (isNull()) {
            return "null";
        }
        StringBuilder sb = new StringBuilder();
        int size = getSize();
        int size2 = readSize();
        if (size != 8) {
            sb.append("size=").append(size);
        }
        if (size != size2) {
            sb.append(", readSize=").append(size2);
        }
        if (isComplex()) {
            sb.append(", complex");
        }
        if (isPublic()) {
            sb.append(", public");
        }
        if (isWeak()) {
            sb.append(", weak");
        }
        if (isCompact()) {
            sb.append(", compact");
        }
        String name2 = getName();
        if (name2 != null) {
            sb.append(", name=").append(name2);
        } else {
            sb.append(", key=").append(getKey());
        }
        return sb.toString();
    }

    static class ValueHeaderReference implements ReferenceItem {
        private final ValueHeader valueHeader;

        ValueHeaderReference(ValueHeader valueHeader) {
            this.valueHeader = valueHeader;
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public int get() {
            return this.valueHeader.getKey();
        }

        @Override // com.reandroid.arsc.item.IntegerReference
        public void set(int i) {
            this.valueHeader.writeKey(i);
        }

        @Override // com.reandroid.arsc.item.ReferenceItem
        public <T1 extends Block> T1 getReferredParent(Class<T1> cls) {
            ValueHeader valueHeader = this.valueHeader;
            return cls.isInstance(valueHeader) ? valueHeader : (T1) valueHeader.getParentInstance(cls);
        }
    }
}
