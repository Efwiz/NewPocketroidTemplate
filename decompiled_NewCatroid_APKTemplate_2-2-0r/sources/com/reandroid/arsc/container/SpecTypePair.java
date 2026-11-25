package com.reandroid.arsc.container;

import com.reandroid.arsc.array.TypeBlockArray;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockContainer;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.SpecBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.header.TypeHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.model.ResourceEntry;
import com.reandroid.arsc.pool.SpecStringPool;
import com.reandroid.arsc.pool.TableStringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.arsc.value.ValueItem;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.ComputeIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.MergingIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import org.xmlpull.v1.XmlSerializer;

/* JADX INFO: loaded from: classes10.dex */
public class SpecTypePair extends BlockContainer<Block> implements Iterable<TypeBlock>, JSONConvert<JSONObject>, Comparable<SpecTypePair> {
    public static final String NAME_sparse_types = "sparse_types";
    public static final String NAME_types = "types";
    private final Block[] mChildes;
    private final SpecBlock mSpecBlock;
    private final TypeBlockArray mTypeBlockArray;

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
    }

    public SpecTypePair(SpecBlock specBlock, TypeBlockArray typeBlockArray) {
        this.mSpecBlock = specBlock;
        this.mTypeBlockArray = typeBlockArray;
        this.mChildes = new Block[]{specBlock, typeBlockArray};
        specBlock.setIndex(0);
        typeBlockArray.setIndex(1);
        specBlock.setParent(this);
        typeBlockArray.setParent(this);
    }

    public SpecTypePair() {
        this(new SpecBlock(), new TypeBlockArray());
    }

    public Iterator<ValueItem> allValues() {
        return new MergingIterator(new ComputeIterator(getTypeBlocks(), new Function() { // from class: com.reandroid.arsc.container.SpecTypePair$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeBlock) obj).allValues();
            }
        }));
    }

    public void trimConfigSizes(int i) {
        Iterator<TypeBlock> typeBlocks = getTypeBlocks();
        while (typeBlocks.hasNext()) {
            typeBlocks.next().getResConfig().trimToSize(i);
        }
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

    public ResourceEntry getResource(int i) {
        PackageBlock packageBlock;
        if (i < 0 || i > getHighestEntryId() || (packageBlock = getPackageBlock()) == null) {
            return null;
        }
        return new ResourceEntry(packageBlock, i | (packageBlock.getId() << 24) | (getId() << 16));
    }

    public ResourceEntry getResource(String str) {
        int iResolveResourceId;
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null || (iResolveResourceId = packageBlock.getSpecStringPool().resolveResourceId(this, str)) == 0) {
            return null;
        }
        return new ResourceEntry(packageBlock, iResolveResourceId);
    }

    public Iterator<ResourceEntry> getResources() {
        final PackageBlock packageBlock = getPackageBlock();
        if (packageBlock == null) {
            return EmptyIterator.of();
        }
        final int highestEntryId = getHighestEntryId();
        final int id2 = (packageBlock.getId() << 24) | (getId() << 16);
        return new Iterator<ResourceEntry>() { // from class: com.reandroid.arsc.container.SpecTypePair.1
            private int mIndex;

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.mIndex <= highestEntryId;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // java.util.Iterator
            public ResourceEntry next() {
                int i = this.mIndex;
                if (i > highestEntryId) {
                    throw new NoSuchElementException();
                }
                int i2 = id2 | i;
                this.mIndex = i + 1;
                return new ResourceEntry(packageBlock, i2);
            }
        };
    }

    public Iterator<Entry> getEntries(int i) {
        return getEntries(i, false);
    }

    public Iterator<Entry> getEntries(final int i, final boolean z) {
        return new ComputeIterator(getTypeBlocks(), new Function() { // from class: com.reandroid.arsc.container.SpecTypePair$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return SpecTypePair.lambda$getEntries$0(i, z, (TypeBlock) obj);
            }
        });
    }

    static /* synthetic */ Entry lambda$getEntries$0(int i, boolean z, TypeBlock typeBlock) {
        Entry entry = typeBlock.getEntry(i);
        if (entry == null) {
            return null;
        }
        if (z && entry.isNull()) {
            return null;
        }
        return entry;
    }

    public Iterator<TypeBlock> getTypeBlocks() {
        return getTypeBlockArray().iterator();
    }

    public Iterator<TypeBlock> getTypeBlocks(Predicate<TypeBlock> predicate) {
        return getTypeBlockArray().iterator(predicate);
    }

    public Iterator<TypeBlock> getTypeBlocks(ResConfig resConfig) {
        return getTypeBlockArray().iterator(resConfig);
    }

    public Boolean hasComplexEntry() {
        return getTypeBlockArray().hasComplexEntry();
    }

    public void linkTableStringsInternal(TableStringPool tableStringPool) {
        Iterator<TypeBlock> it = iterator();
        while (it.hasNext()) {
            it.next().linkTableStringsInternal(tableStringPool);
        }
    }

    public void linkSpecStringsInternal(SpecStringPool specStringPool) {
        Iterator<TypeBlock> it = iterator();
        while (it.hasNext()) {
            it.next().linkSpecStringsInternal(specStringPool);
        }
    }

    public void destroy() {
        getSpecBlock().destroy();
        getTypeBlockArray().destroy();
    }

    public Entry getAnyEntry(short s) {
        Iterator<T> it = getTypeBlockArray().iterator();
        Entry entry = null;
        while (it.hasNext()) {
            Entry entry2 = ((TypeBlock) it.next()).getEntry(s);
            if (entry2 != null) {
                if (!entry2.isNull()) {
                    return entry2;
                }
                if (entry == null) {
                    entry = entry2;
                }
            }
        }
        return entry;
    }

    public Entry getAnyEntry(String str) {
        Entry entry;
        for (TypeBlock typeBlock : getTypeBlockArray()) {
            if (typeBlock != null && (entry = typeBlock.getEntry(str)) != null) {
                return entry;
            }
        }
        return null;
    }

    public Entry getEntry(ResConfig resConfig, String str) {
        return getTypeBlockArray().getEntry(resConfig, str);
    }

    public void sortTypes() {
        getTypeBlockArray().sort();
    }

    public boolean removeNullEntries(int i) {
        int i2 = i & 65535;
        if (!getTypeBlockArray().removeNullEntries(i2)) {
            return false;
        }
        getSpecBlock().setEntryCount(i2);
        return true;
    }

    public void removeEmptyTypeBlocks() {
        getTypeBlockArray().removeEmptyBlocks();
    }

    public boolean isEmpty() {
        return getTypeBlockArray().isEmpty();
    }

    public int countTypeBlocks() {
        return getTypeBlockArray().size();
    }

    public Entry getOrCreateEntry(short s, String str) {
        return getTypeBlockArray().getOrCreateEntry(s, str);
    }

    public Entry getOrCreateEntry(short s, ResConfig resConfig) {
        return getTypeBlockArray().getOrCreateEntry(s, resConfig);
    }

    public Entry getEntry(short s, String str) {
        return getTypeBlockArray().getEntry(s, str);
    }

    public TypeBlock getOrCreateTypeBlock(String str) {
        return getTypeBlockArray().getOrCreate(str);
    }

    public TypeBlock getOrCreateTypeBlock(ResConfig resConfig) {
        return getTypeBlockArray().getOrCreate(resConfig);
    }

    public TypeBlock getTypeBlock(String str) {
        return getTypeBlockArray().getTypeBlock(str);
    }

    public TypeBlock getTypeBlock(ResConfig resConfig) {
        return getTypeBlockArray().getTypeBlock(resConfig);
    }

    public Set<ResConfig> listResConfig() {
        return this.mTypeBlockArray.listResConfig();
    }

    public Iterator<ResConfig> getResConfigs() {
        return this.mTypeBlockArray.getResConfigs();
    }

    public Iterator<TypeBlock> iteratorNonEmpty() {
        return this.mTypeBlockArray.iteratorNonEmpty();
    }

    public boolean hasDuplicateResConfig(boolean z) {
        return this.mTypeBlockArray.hasDuplicateResConfig(z);
    }

    public byte getTypeId() {
        return this.mSpecBlock.getTypeId();
    }

    public int getId() {
        return this.mSpecBlock.getId();
    }

    public void setTypeId(byte b) {
        this.mSpecBlock.setTypeId(b);
        this.mTypeBlockArray.setTypeId(b);
    }

    public String getTypeName() {
        TypeString typeString = getTypeString();
        if (typeString != null) {
            return typeString.get();
        }
        return null;
    }

    public boolean isEqualTypeName(String str) {
        return TypeBlock.isEqualTypeName(getTypeName(), str);
    }

    public SpecBlock getSpecBlock() {
        return this.mSpecBlock;
    }

    public TypeBlockArray getTypeBlockArray() {
        return this.mTypeBlockArray;
    }

    public PackageBlock getPackageBlock() {
        return (PackageBlock) getParent(PackageBlock.class);
    }

    public List<Entry> listEntries(int i) {
        ArrayCollection arrayCollection = new ArrayCollection();
        Iterator<TypeBlock> it = this.mTypeBlockArray.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = it.next().getEntry(i);
            if (entry != null && !entry.isNull()) {
                arrayCollection.add(entry);
            }
        }
        return arrayCollection;
    }

    public List<Entry> listEntries(String str) {
        ArrayCollection arrayCollection = new ArrayCollection();
        Iterator<TypeBlock> it = this.mTypeBlockArray.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            Entry entry = it.next().getEntry(str);
            if (entry != null) {
                arrayCollection.add(entry);
            }
        }
        return arrayCollection;
    }

    @Override // java.lang.Iterable
    public Iterator<TypeBlock> iterator() {
        return getTypeBlockArray().iterator();
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    public int getChildesCount() {
        return this.mChildes.length;
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    public Block[] getChildes() {
        return this.mChildes;
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        if (headerBlock == null) {
            return;
        }
        ChunkType chunkType = headerBlock.getChunkType();
        if (chunkType == ChunkType.TYPE) {
            readTypeBlock(blockReader);
            return;
        }
        if (chunkType != ChunkType.SPEC) {
            readUnexpectedNonSpecBlock(blockReader, headerBlock);
        }
        this.mSpecBlock.readBytes(blockReader);
    }

    private void readTypeBlock(BlockReader blockReader) throws IOException {
        TypeHeader typeHeader = TypeHeader.read(blockReader);
        this.mTypeBlockArray.createNext(typeHeader.isSparse(), typeHeader.isOffset16()).readBytes(blockReader);
    }

    private void readUnexpectedNonSpecBlock(BlockReader blockReader, HeaderBlock headerBlock) throws IOException {
        throw new IOException("Unexpected block: " + headerBlock.toString() + ", Should be: " + ChunkType.SPEC);
    }

    public int getHighestEntryCount() {
        return getTypeBlockArray().getHighestEntryCount();
    }

    public int getHighestEntryId() {
        return getTypeBlockArray().getHighestEntryId();
    }

    public TypeString getTypeString() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null) {
            return packageBlock.getTypeStringPool().getById(getId());
        }
        return null;
    }

    public void serializePublicXml(XmlSerializer xmlSerializer) throws IOException {
        ResourceEntry resource = getResource(getHighestEntryId());
        boolean zSerializePublicXml = resource != null ? resource.serializePublicXml(xmlSerializer) : false;
        Iterator<ResourceEntry> resources = getResources();
        while (resources.hasNext()) {
            ResourceEntry next = resources.next();
            if (!zSerializePublicXml || !next.equals(resource)) {
                next.serializePublicXml(xmlSerializer);
            }
        }
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONObject toJson() {
        return toJson(false);
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONObject jSONObject) {
        getSpecBlock().fromJson(jSONObject.getJSONObject(SpecBlock.NAME_spec));
        getTypeBlockArray().fromJson(jSONObject.optJSONArray(NAME_types));
    }

    public JSONObject toJson(boolean z) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put(SpecBlock.NAME_spec, getSpecBlock().toJson());
        if (!z) {
            jSONObject.put(NAME_types, getTypeBlockArray().toJson());
        }
        return jSONObject;
    }

    public void merge(SpecTypePair specTypePair) {
        if (specTypePair == null || specTypePair == this) {
            return;
        }
        if (getTypeId() != specTypePair.getTypeId()) {
            throw new IllegalArgumentException("Can not merge different id types: " + ((int) getTypeId()) + "!=" + ((int) specTypePair.getTypeId()));
        }
        getSpecBlock().merge(specTypePair.getSpecBlock());
        getTypeBlockArray().merge(specTypePair.getTypeBlockArray());
    }

    @Override // java.lang.Comparable
    public int compareTo(SpecTypePair specTypePair) {
        return Integer.compare(getId(), specTypePair.getId());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(HexUtil.toHex2(getTypeId()));
        sb.append(" (");
        TypeString typeString = getTypeString();
        if (typeString != null) {
            sb.append(typeString.get());
        } else {
            sb.append("null");
        }
        sb.append(") config count=");
        sb.append(getTypeBlockArray().size());
        return sb.toString();
    }
}
