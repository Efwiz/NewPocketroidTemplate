package com.reandroid.arsc.value;

import com.reandroid.arsc.array.EntryArray;
import com.reandroid.arsc.array.ResValueMapArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.SpecBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.SpecFlag;
import com.reandroid.arsc.item.SpecString;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.model.ResourceName;
import com.reandroid.arsc.pool.SpecStringPool;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.refactor.ResourceMergeOption;
import com.reandroid.graphics.AndroidColor;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.xml.StyleDocument;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class Entry extends Block implements JSONConvert<JSONObject> {
    public static final String NAME_id = "id";
    private IntegerItem mNullSpecReference;
    private TableEntry<?, ?> mTableEntry;

    public Iterator<ValueItem> allValues() {
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry != null) {
            return tableEntry.allValues();
        }
        return EmptyIterator.of();
    }

    public void linkTableStringsInternal(TableStringPool tableStringPool) {
        getTableEntry().linkTableStringsInternal(tableStringPool);
    }

    public void linkSpecStringsInternal(SpecStringPool specStringPool) {
        getTableEntry().getHeader().linkSpecStringsInternal(specStringPool);
    }

    public ResValue getResValue() {
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry instanceof ResTableEntry) {
            return ((ResTableEntry) tableEntry).getValue();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ResValueMapArray getResValueMapArray() {
        ResTableMapEntry resTableMapEntry = getResTableMapEntry();
        if (resTableMapEntry != null) {
            return (ResValueMapArray) resTableMapEntry.getValue();
        }
        return null;
    }

    public ResTableMapEntry getResTableMapEntry() {
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry instanceof ResTableMapEntry) {
            return (ResTableMapEntry) tableEntry;
        }
        return null;
    }

    public String getXmlTag() {
        ResTableMapEntry resTableMapEntry;
        ValueType valueTypeIsAllSameValueType;
        String xmlTagName = TypeString.toXmlTagName(getTypeName());
        if (xmlTagName == null || !xmlTagName.contains("array") || (resTableMapEntry = getResTableMapEntry()) == null || (valueTypeIsAllSameValueType = resTableMapEntry.isAllSameValueType()) == null) {
            return xmlTagName;
        }
        if (valueTypeIsAllSameValueType == ValueType.STRING) {
            return "string-" + xmlTagName;
        }
        return valueTypeIsAllSameValueType == ValueType.DEC ? "integer-" + xmlTagName : xmlTagName;
    }

    public SpecFlag getSpecFlag() {
        SpecBlock specBlock = getSpecBlock();
        if (specBlock == null) {
            return null;
        }
        return specBlock.getSpecFlag(getId());
    }

    public void ensureComplex(boolean z) {
        ensureTableEntry(z);
    }

    public int getId() {
        int index = getIndex();
        EntryArray entryArray = (EntryArray) getParentInstance(EntryArray.class);
        return entryArray != null ? entryArray.getEntryId(index) : index;
    }

    public SpecString reName(String str) {
        SpecTypePair specTypePair = getSpecTypePair();
        SpecString name2 = null;
        if (specTypePair == null) {
            return null;
        }
        Iterator<Entry> entries = specTypePair.getEntries(getId(), false);
        while (entries.hasNext()) {
            Entry next = entries.next();
            if (name2 == null) {
                name2 = next.setName(str);
            } else {
                next.updateSpecReference(name2);
            }
        }
        return name2;
    }

    public SpecString setName(String str) {
        return setName(str, false);
    }

    public SpecString setName(String str, boolean z) {
        PackageBlock packageBlock;
        if (str == null) {
            unlinkNullSpecString();
            TableEntry<?, ?> tableEntry = getTableEntry();
            if (tableEntry != null) {
                tableEntry.getHeader().setKey((StringItem) null);
            }
            return null;
        }
        if ((!z && isNull()) || (packageBlock = getPackageBlock()) == null) {
            return null;
        }
        SpecStringPool specStringPool = packageBlock.getSpecStringPool();
        SpecString specString = specStringPool.get(getSpecReference());
        if (specString != null && str.equals(specString.get())) {
            return null;
        }
        SpecString orCreate = specStringPool.getOrCreate(str);
        setSpecReference(orCreate);
        return orCreate;
    }

    public String getName() {
        SpecString specString = getSpecString();
        if (specString != null) {
            return specString.get();
        }
        return null;
    }

    public String getTypeName() {
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock != null) {
            return typeBlock.getTypeName();
        }
        return null;
    }

    public int getTypeId() {
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock != null) {
            return typeBlock.getId();
        }
        return 0;
    }

    public int getResourceId() {
        PackageBlock packageBlock;
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock == null || (packageBlock = typeBlock.getPackageBlock()) == null) {
            return 0;
        }
        return (typeBlock.getId() << 16) | (packageBlock.getId() << 24) | getId();
    }

    public int getSpecReference() {
        IntegerItem integerItem = this.mNullSpecReference;
        if (integerItem != null) {
            return integerItem.get();
        }
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry != null) {
            return tableEntry.getHeader().getKey();
        }
        return -1;
    }

    public TypeString getTypeString() {
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock != null) {
            return typeBlock.getTypeString();
        }
        return null;
    }

    public boolean isDefined() {
        return getSpecReference() != -1;
    }

    public boolean isDefault() {
        ResConfig resConfig = getResConfig();
        if (resConfig != null) {
            return resConfig.isDefault();
        }
        return false;
    }

    public void setSpecReference(SpecString specString) {
        if (isSameSpecString(specString)) {
            return;
        }
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry != null) {
            tableEntry.getHeader().setKey(specString);
            unlinkNullSpecString();
        } else {
            linkNullSpecString(specString);
        }
    }

    public void updateSpecReference(SpecString specString) {
        if (isSameSpecString(specString)) {
            return;
        }
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry != null) {
            tableEntry.getHeader().setKey(specString);
            unlinkNullSpecString();
        } else if (this.mNullSpecReference != null) {
            linkNullSpecString(specString);
        } else if (specString == null) {
            unlinkNullSpecString();
        }
    }

    public void setSpecReference(int i) {
        if (i == getSpecReference()) {
            return;
        }
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry == null) {
            linkNullSpecString(i);
        } else {
            unlinkNullSpecString();
            tableEntry.getHeader().setKey(i);
        }
    }

    private boolean isSameSpecString(SpecString specString) {
        int specReference = getSpecReference();
        if (specString == null) {
            return specReference < 0;
        }
        if (specReference != specString.getIndex()) {
            return false;
        }
        return this.mNullSpecReference == null || getTableEntry() == null;
    }

    private void linkNullSpecString(int i) {
        if (i < 0) {
            unlinkNullSpecString();
            return;
        }
        SpecStringPool specStringPool = getSpecStringPool();
        if (specStringPool == null) {
            unlinkNullSpecString();
        } else {
            linkNullSpecString(specStringPool.get(i));
        }
    }

    private void linkNullSpecString(SpecString specString) {
        if (specString == null) {
            unlinkNullSpecString();
            return;
        }
        IntegerItem integerItem = this.mNullSpecReference;
        if (integerItem == null || integerItem.get() != specString.getIndex()) {
            unlinkNullSpecString();
            IntegerItem integerItem2 = new IntegerItem();
            integerItem2.setParent(this);
            integerItem2.setIndex(1);
            integerItem2.set(specString.getIndex());
            specString.addReference(integerItem2);
            this.mNullSpecReference = integerItem2;
        }
    }

    private void unlinkNullSpecString() {
        IntegerItem integerItem = this.mNullSpecReference;
        if (integerItem == null) {
            return;
        }
        SpecStringPool specStringPool = getSpecStringPool();
        if (specStringPool != null) {
            specStringPool.removeReference(integerItem);
        }
        integerItem.setParent(null);
        integerItem.setIndex(-1);
        this.mNullSpecReference = null;
    }

    private SpecStringPool getSpecStringPool() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null) {
            return packageBlock.getSpecStringPool();
        }
        return null;
    }

    public ValueType getValueType() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueType();
        }
        return null;
    }

    public String getValueAsString() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsString();
        }
        return null;
    }

    public StyleDocument getValueAsStyleDocument() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsStyleDocument();
        }
        return null;
    }

    public Boolean getValueAsBoolean() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return Boolean.valueOf(resValue.getValueAsBoolean());
        }
        return null;
    }

    public AndroidColor getValueAsColor() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsColor();
        }
        return null;
    }

    public Float getValueAsFloat() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsFloat();
        }
        return null;
    }

    public Integer getValueAsInteger() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsInteger();
        }
        return null;
    }

    public ResourceEntry getValueAsReference() {
        ResValue resValue = getResValue();
        if (resValue != null) {
            return resValue.getValueAsReference();
        }
        return null;
    }

    public ResValue setValueAsRaw(ValueType valueType, int i) {
        ResValue resValueEnsureScalar = ensureScalar();
        resValueEnsureScalar.setTypeAndData(valueType, i);
        return resValueEnsureScalar;
    }

    public ResValue setValueAsBoolean(boolean z) {
        ResValue resValueEnsureScalar = ensureScalar();
        resValueEnsureScalar.setValueAsBoolean(z);
        return resValueEnsureScalar;
    }

    public ResValue setValueAsReference(int i) {
        return setValueAsRaw(ValueType.REFERENCE, i);
    }

    public ResValue setValueAsString(StyleDocument styleDocument) {
        ResValue resValue = (ResValue) ensureTableEntry(false).getValue();
        resValue.setValueAsString(styleDocument);
        return resValue;
    }

    public ResValue setValueAsString(String str) {
        ResValue resValueEnsureScalar = ensureScalar();
        resValueEnsureScalar.setValueAsString(str);
        return resValueEnsureScalar;
    }

    public ResValue setValueAsColor(AndroidColor androidColor) {
        ResValue resValueEnsureScalar = ensureScalar();
        resValueEnsureScalar.setValue(androidColor);
        return resValueEnsureScalar;
    }

    public SpecString getSpecString() {
        PackageBlock packageBlock;
        int specReference = getSpecReference();
        if (specReference >= 0 && (packageBlock = getPackageBlock()) != null) {
            return packageBlock.getSpecStringPool().get(specReference);
        }
        return null;
    }

    public ResConfig getResConfig() {
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock != null) {
            return typeBlock.getResConfig();
        }
        return null;
    }

    public SpecBlock getSpecBlock() {
        SpecTypePair specTypePair = getSpecTypePair();
        if (specTypePair != null) {
            return specTypePair.getSpecBlock();
        }
        return null;
    }

    private SpecTypePair getSpecTypePair() {
        TypeBlock typeBlock = getTypeBlock();
        if (typeBlock != null) {
            return typeBlock.getParentSpecTypePair();
        }
        return null;
    }

    public TypeBlock getTypeBlock() {
        return (TypeBlock) getParent(TypeBlock.class);
    }

    private String getPackageName() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null) {
            return packageBlock.getName();
        }
        return null;
    }

    public PackageBlock getPackageBlock() {
        return (PackageBlock) getParent(PackageBlock.class);
    }

    public ResourceEntry getResourceEntry() {
        return new ResourceEntry(getPackageBlock(), getResourceId());
    }

    public ResourceName getResourceName() {
        String name2;
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null || (name2 = getName()) == null) {
            return null;
        }
        return new ResourceName(packageBlock.getName(), getTypeName(), name2);
    }

    private ResValue ensureScalar() {
        return (ResValue) ensureTableEntry(false).getValue();
    }

    private TableEntry<?, ?> ensureTableEntry(boolean z) {
        TableEntry<?, ?> tableEntry = getTableEntry();
        boolean z2 = (z && (tableEntry instanceof ResTableMapEntry)) || (!z && (tableEntry instanceof ResTableEntry));
        if (tableEntry != null && z2) {
            return tableEntry;
        }
        TableEntry<?, ?> tableEntryCreateTableEntry = createTableEntry(z);
        setTableEntry(tableEntryCreateTableEntry);
        return tableEntryCreateTableEntry;
    }

    public TableEntry<?, ?> getTableEntry() {
        return this.mTableEntry;
    }

    public ValueHeader getHeader() {
        TableEntry<?, ?> tableEntry = getTableEntry();
        if (tableEntry != null) {
            return tableEntry.getHeader();
        }
        return null;
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return getTableEntry() == null;
    }

    @Override // com.reandroid.arsc.base.Block
    public void setNull(boolean z) {
        if (z) {
            setTableEntry(null);
        }
    }

    @Override // com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        if (isNull()) {
            return null;
        }
        return getTableEntry().getBytes();
    }

    @Override // com.reandroid.arsc.base.Block
    public int countBytes() {
        if (isNull()) {
            return 0;
        }
        return getTableEntry().countBytes();
    }

    @Override // com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        blockCounter.setCurrent(this);
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
        } else {
            if (isNull()) {
                return;
            }
            getTableEntry().onCountUpTo(blockCounter);
        }
    }

    @Override // com.reandroid.arsc.base.Block
    protected int onWriteBytes(OutputStream outputStream) throws IOException {
        if (isNull()) {
            return 0;
        }
        return getTableEntry().writeBytes(outputStream);
    }

    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        TableEntry<?, ?> tableEntryCreateTableEntry = createTableEntry(blockReader);
        setTableEntry(tableEntryCreateTableEntry);
        tableEntryCreateTableEntry.readBytes(blockReader);
    }

    public boolean isComplex() {
        return getTableEntry() instanceof CompoundEntry;
    }

    public boolean isScalar() {
        return getTableEntry() instanceof ResTableEntry;
    }

    public void setTableEntry(TableEntry<?, ?> tableEntry) {
        if (tableEntry == this.mTableEntry) {
            return;
        }
        onTableEntryRemoved();
        if (tableEntry == null) {
            return;
        }
        tableEntry.setIndex(0);
        tableEntry.setParent(this);
        this.mTableEntry = tableEntry;
        transferSpecReference(tableEntry);
    }

    private void transferSpecReference(TableEntry<?, ?> tableEntry) {
        IntegerItem integerItem = this.mNullSpecReference;
        if (integerItem == null) {
            return;
        }
        int i = integerItem.get();
        unlinkNullSpecString();
        ValueHeader header = tableEntry.getHeader();
        if (header.getKey() < 0) {
            header.setKey(i);
        }
    }

    private void onTableEntryRemoved() {
        TableEntry<?, ?> tableEntry = this.mTableEntry;
        if (tableEntry == null) {
            return;
        }
        tableEntry.onRemoved();
        tableEntry.setIndex(-1);
        tableEntry.setParent(null);
        this.mTableEntry = null;
    }

    private TableEntry<?, ?> createTableEntry(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        blockReader.offset(2);
        boolean z = (blockReader.readShort() & 1) == 1;
        blockReader.seek(position);
        return createTableEntry(z);
    }

    private TableEntry<?, ?> createTableEntry(boolean z) {
        if (z) {
            return new ResTableMapEntry();
        }
        return new ResTableEntry();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        if (isNull()) {
            return null;
        }
        return getTableEntry().toJson();
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        if (jSONObject == null) {
            setNull(true);
            return;
        }
        TableEntry<?, ?> tableEntryCreateTableEntry = createTableEntry(jSONObject.optBoolean(ValueHeader.NAME_is_complex, false));
        setTableEntry(tableEntryCreateTableEntry);
        tableEntryCreateTableEntry.fromJson(jSONObject);
    }

    public ResourceEntry resolve(int i) {
        PackageBlock packageBlock = getPackageBlock();
        return packageBlock.getTableBlock().getResource(packageBlock, i);
    }

    public void merge(Entry entry) {
        if (canMerge(entry)) {
            TableEntry<?, ?> tableEntry = entry.getTableEntry();
            ensureTableEntry(tableEntry instanceof ResTableMapEntry).merge(tableEntry);
        }
    }

    public void mergeWithName(ResourceMergeOption resourceMergeOption, Entry entry) {
        if (canMerge(entry)) {
            TableEntry<?, ?> tableEntry = entry.getTableEntry();
            ensureTableEntry(tableEntry instanceof ResTableMapEntry).mergeWithName(resourceMergeOption, tableEntry);
        }
    }

    private boolean canMerge(Entry entry) {
        if (entry == null || entry == this || entry.isNull()) {
            return false;
        }
        if (isNull()) {
            return true;
        }
        return getTableEntry().canMerge(entry.getTableEntry());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HexUtil.toHex8(getResourceId()));
        sb.append(StringUtil.SPACE);
        ResConfig resConfig = getResConfig();
        if (resConfig != null) {
            sb.append(resConfig);
            sb.append(StringUtil.SPACE);
        }
        SpecFlag specFlag = getSpecFlag();
        if (specFlag != null) {
            sb.append(specFlag);
            sb.append(StringUtil.SPACE);
        }
        if (isNull()) {
            sb.append("NULL ");
        }
        sb.append('@');
        sb.append(getTypeName());
        sb.append('/');
        sb.append(getName());
        return sb.toString();
    }
}
