package com.reandroid.arsc.chunk;

import com.reandroid.arsc.array.EntryArray;
import com.reandroid.arsc.array.IntegerOffsetArray;
import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.array.ShortOffsetArray;
import com.reandroid.arsc.array.SparseOffsetsArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.header.TypeHeader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.SpecString;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.pool.SpecStringPool;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.pool.TypeStringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ValueItem;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.IterableIterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.xerces.impl.xs.SchemaSymbols;

/* JADX INFO: loaded from: classes10.dex */
public class TypeBlock extends Chunk<TypeHeader> implements Iterable<Entry>, JSONConvert<JSONObject>, Comparable<TypeBlock> {
    public static final String NAME_config = "config";
    public static final String NAME_entries = "entries";
    public static final String NAME_id = "id";
    public static final String NAME_is_offset16 = "is_offset16";
    public static final String NAME_is_sparse = "is_sparse";
    public static final String NAME_name = "name";
    private final EntryArray mEntryArray;
    private TypeString mTypeString;

    private static boolean isWildTypeNamePrefix(char c) {
        return c == '*' || c == '+' || c == '^';
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TypeBlock(boolean z, boolean z2) {
        OffsetArray integerOffsetArray;
        super(new TypeHeader(z, z2), 2);
        TypeHeader typeHeader = (TypeHeader) getHeaderBlock();
        if (z) {
            integerOffsetArray = new SparseOffsetsArray();
        } else if (z2) {
            integerOffsetArray = new ShortOffsetArray();
        } else {
            integerOffsetArray = new IntegerOffsetArray();
        }
        EntryArray entryArray = new EntryArray(integerOffsetArray, typeHeader.getCountItem(), typeHeader.getEntriesStart());
        this.mEntryArray = entryArray;
        addChild((Block) integerOffsetArray);
        addChild(entryArray);
    }

    public Iterator<ValueItem> allValues() {
        return new IterableIterator<Entry, ValueItem>(iterator()) { // from class: com.reandroid.arsc.chunk.TypeBlock.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<ValueItem> iterator(Entry entry) {
                return entry.allValues();
            }
        };
    }

    public boolean isTypeAttr() {
        TypeString typeString = getTypeString();
        if (typeString != null) {
            return typeString.isTypeAttr();
        }
        return false;
    }

    public boolean isTypeId() {
        TypeString typeString = getTypeString();
        if (typeString != null) {
            return typeString.isTypeId();
        }
        return false;
    }

    public String buildUniqueDirectoryName() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null && packageBlock.hasValidTypeNames()) {
            return getTypeName() + getResConfig().getQualifiers();
        }
        return "type_" + HexUtil.toHex2(getTypeId()) + getResConfig().getQualifiers();
    }

    public void linkTableStringsInternal(TableStringPool tableStringPool) {
        getEntryArray().linkTableStringsInternal(tableStringPool);
    }

    public void linkSpecStringsInternal(SpecStringPool specStringPool) {
        getEntryArray().linkSpecStringsInternal(specStringPool);
    }

    public boolean isSparse() {
        return getHeaderBlock().isSparse();
    }

    public boolean isOffset16() {
        return getHeaderBlock().isOffset16();
    }

    public void destroy() {
        getEntryArray().destroy();
        setId(0);
        setParent(null);
    }

    public boolean removeNullEntries(int i) {
        int i2 = i & 65535;
        EntryArray entryArray = getEntryArray();
        entryArray.removeAllNull(i2);
        return entryArray.size() == i2;
    }

    public PackageBlock getPackageBlock() {
        SpecTypePair specTypePair = (SpecTypePair) getParent(SpecTypePair.class);
        if (specTypePair != null) {
            return specTypePair.getPackageBlock();
        }
        return null;
    }

    public String getTypeName() {
        TypeString typeString = getTypeString();
        if (typeString == null) {
            return null;
        }
        return typeString.get();
    }

    public TypeString getTypeString() {
        TypeString typeString = this.mTypeString;
        if (typeString != null) {
            if (typeString.getId() == getTypeId()) {
                return this.mTypeString;
            }
            this.mTypeString = null;
        }
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null) {
            return null;
        }
        TypeString byId = packageBlock.getTypeStringPool().getById(getId());
        this.mTypeString = byId;
        return byId;
    }

    public byte getTypeId() {
        return getHeaderBlock().getId().get();
    }

    public int getId() {
        return getHeaderBlock().getId().unsignedInt();
    }

    public void setId(int i) {
        setTypeId((byte) (i & 255));
    }

    public void setTypeId(byte b) {
        getHeaderBlock().getId().set(b);
    }

    public void setTypeName(String str) {
        TypeStringPool typeStringPool = getTypeStringPool();
        int id2 = getId();
        TypeString byId = typeStringPool.getById(id2);
        if (byId == null) {
            byId = typeStringPool.getOrCreate(id2, str);
        }
        byId.set(str);
    }

    private TypeStringPool getTypeStringPool() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null) {
            return packageBlock.getTypeStringPool();
        }
        return (TypeStringPool) ObjectsUtil.cast(null);
    }

    public void setEntryCount(int i) {
        IntegerItem countItem = getHeaderBlock().getCountItem();
        if (i == countItem.get()) {
            return;
        }
        countItem.set(i);
        onSetEntryCount(i);
    }

    public boolean isEmpty() {
        return getEntryArray().isEmpty();
    }

    public boolean isDefault() {
        return getResConfig().isDefault();
    }

    public String getQualifiers() {
        return getResConfig().getQualifiers();
    }

    public void setQualifiers(String str) {
        getResConfig().parseQualifiers(str);
    }

    public SpecTypePair getParentSpecTypePair() {
        return (SpecTypePair) getParent(SpecTypePair.class);
    }

    public Entry getOrCreateDefinedEntry(String str) {
        int iResolveResourceId;
        Entry entry = getEntry(str);
        if (entry != null) {
            return entry;
        }
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null || (iResolveResourceId = packageBlock.resolveResourceId(getId(), str)) == 0) {
            return null;
        }
        SpecString orCreate = packageBlock.getSpecStringPool().getOrCreate(str);
        Entry orCreateEntry = getOrCreateEntry((short) (65535 & iResolveResourceId));
        orCreateEntry.setSpecReference(orCreate);
        return orCreateEntry;
    }

    public Entry getOrCreateEntry(String str) {
        Entry entry = getEntry(str);
        if (entry != null) {
            return entry;
        }
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null) {
            return null;
        }
        int iResolveResourceId = packageBlock.resolveResourceId(getId(), str);
        int highestEntryId = iResolveResourceId != 0 ? iResolveResourceId & 65535 : getParentSpecTypePair().getHighestEntryId() + 1;
        SpecString orCreate = packageBlock.getSpecStringPool().getOrCreate(str);
        Entry orCreateEntry = getOrCreateEntry((short) highestEntryId);
        orCreateEntry.setSpecReference(orCreate);
        return orCreateEntry;
    }

    public Entry getOrCreateEntry(short s) {
        return getEntryArray().getOrCreate(s);
    }

    public Entry getEntry(short s) {
        return getEntryArray().getEntry(s);
    }

    public int realSize() {
        return getEntryArray().countNonNull();
    }

    public int size() {
        return getEntryArray().size();
    }

    @Override // java.lang.Iterable
    public Iterator<Entry> iterator() {
        return getEntryArray().m2755lambda$listItems$0$comreandroidarscbaseBlockArray(false);
    }

    public void clear() {
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            it.next().setNull(true);
        }
        getEntryArray().clear();
    }

    public Entry getEntry(String str) {
        return getEntryArray().getEntry(str);
    }

    public Boolean hasComplexEntry() {
        SpecTypePair parentSpecTypePair = getParentSpecTypePair();
        if (parentSpecTypePair != null) {
            return parentSpecTypePair.hasComplexEntry();
        }
        return null;
    }

    public ResConfig getResConfig() {
        return getHeaderBlock().getConfig();
    }

    public EntryArray getEntryArray() {
        return this.mEntryArray;
    }

    public void ensureEntriesCount(int i) {
        EntryArray entryArray = getEntryArray();
        entryArray.ensureSize(i);
        entryArray.refreshCount();
    }

    public List<Entry> listEntries(boolean z) {
        return CollectionUtil.toList(getEntryArray().m2755lambda$listItems$0$comreandroidarscbaseBlockArray(z));
    }

    public Entry getEntry(int i) {
        return getEntryArray().getEntry(i);
    }

    private void onSetEntryCount(int i) {
        getEntryArray().setSize(i);
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
        getEntryArray().refreshCountAndStart();
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        getHeaderBlock().getConfig().refresh();
        super.onPreRefresh();
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            writeBytes(byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException unused) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        JSONObject jSONObject = new JSONObject();
        if (isSparse()) {
            jSONObject.put(NAME_is_sparse, true);
        }
        if (isOffset16()) {
            jSONObject.put(NAME_is_offset16, true);
        }
        jSONObject.put("id", getId());
        jSONObject.put("name", getTypeName());
        jSONObject.put("config", getResConfig().toJson());
        jSONObject.put(NAME_entries, getEntryArray().toJson());
        return jSONObject;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        setId(jSONObject.getInt("id"));
        String strOptString = jSONObject.optString("name");
        if (strOptString != null) {
            setTypeName(strOptString);
        }
        getEntryArray().fromJson(jSONObject.getJSONArray(NAME_entries));
        getResConfig().fromJson(jSONObject.getJSONObject("config"));
    }

    public void merge(TypeBlock typeBlock) {
        if (typeBlock == null || typeBlock == this) {
            return;
        }
        if (getTypeId() != typeBlock.getTypeId()) {
            throw new IllegalArgumentException("Can not merge different id types: " + ((int) getTypeId()) + "!=" + ((int) typeBlock.getTypeId()));
        }
        setTypeName(typeBlock.getTypeName());
        getEntryArray().merge(typeBlock.getEntryArray());
    }

    @Override // java.lang.Comparable
    public int compareTo(TypeBlock typeBlock) {
        int id2 = getId();
        int id3 = typeBlock.getId();
        if (id2 != id3) {
            return Integer.compare(id2, id3);
        }
        StringBuilder sb = new StringBuilder();
        boolean zIsSparse = isSparse();
        String str = SchemaSymbols.ATTVAL_TRUE_1;
        String string = sb.append(zIsSparse ? SchemaSymbols.ATTVAL_TRUE_1 : "0").append(getResConfig().getQualifiers()).toString();
        StringBuilder sb2 = new StringBuilder();
        if (!typeBlock.isSparse()) {
            str = "0";
        }
        return string.compareTo(sb2.append(str).append(typeBlock.getResConfig().getQualifiers()).toString());
    }

    public boolean isEqualTypeName(String str) {
        return isEqualTypeName(getTypeName(), str);
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        return getTypeName() + '{' + getHeaderBlock() + '}';
    }

    public static boolean canHaveResourceFile(String str) {
        return !isEqualTypeName("string", str);
    }

    public static boolean isEqualTypeName(String str, String str2) {
        if (str == null) {
            return str2 == null;
        }
        if (str2 == null) {
            return false;
        }
        if (str.equals(str2)) {
            return true;
        }
        return trimTypeName(str).equals(trimTypeName(str2));
    }

    private static String trimTypeName(String str) {
        while (str.length() > 0 && isWildTypeNamePrefix(str.charAt(0))) {
            str = str.substring(1);
        }
        return str;
    }
}
