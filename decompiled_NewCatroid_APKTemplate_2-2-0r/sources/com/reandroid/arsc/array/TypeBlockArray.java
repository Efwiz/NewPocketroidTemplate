package com.reandroid.arsc.array;

import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.chunk.SpecBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.header.TypeHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.collection.ComputeIterator;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class TypeBlockArray extends BlockArray<TypeBlock> implements JSONConvert<JSONArray>, Comparator<TypeBlock> {
    private static final Predicate<TypeBlock> NON_EMPTY_TESTER = new Predicate<TypeBlock>() { // from class: com.reandroid.arsc.array.TypeBlockArray.2
        @Override // java.util.function.Predicate
        public boolean test(TypeBlock typeBlock) {
            if (typeBlock == null || typeBlock.isNull()) {
                return false;
            }
            return !typeBlock.isEmpty();
        }
    };
    private Boolean mHasComplexEntry;
    private byte mTypeId;

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    public Boolean hasComplexEntry() {
        Boolean bool = this.mHasComplexEntry;
        if (bool != null) {
            return bool;
        }
        Iterator<TypeBlock> it = listItems(true).iterator();
        while (it.hasNext()) {
            Boolean boolHasComplexEntry = it.next().getEntryArray().hasComplexEntry();
            if (boolHasComplexEntry != null) {
                this.mHasComplexEntry = boolHasComplexEntry;
            }
        }
        return this.mHasComplexEntry;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void destroy() {
        for (TypeBlock typeBlock : listItems()) {
            if (typeBlock != null) {
                typeBlock.destroy();
            }
        }
        clear();
    }

    public void sort() {
        sort(this);
    }

    public boolean removeNullEntries(int i) {
        Iterator<TypeBlock> it = listItems().iterator();
        while (true) {
            boolean z = true;
            while (it.hasNext()) {
                boolean zRemoveNullEntries = it.next().removeNullEntries(i);
                if (!z || !zRemoveNullEntries) {
                    z = false;
                }
            }
            return z;
        }
    }

    public void removeEmptyBlocks() {
        removeIf(new Predicate() { // from class: com.reandroid.arsc.array.TypeBlockArray$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((TypeBlock) obj).isEmpty();
            }
        });
    }

    public Entry getOrCreateEntry(short s, String str) {
        return getOrCreate(str).getOrCreateEntry(s);
    }

    public Entry getOrCreateEntry(short s, ResConfig resConfig) {
        return getOrCreate(resConfig).getOrCreateEntry(s);
    }

    public boolean isEmpty() {
        for (TypeBlock typeBlock : listItems()) {
            if (typeBlock != null && !typeBlock.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Entry getEntry(short s, String str) {
        TypeBlock typeBlock = getTypeBlock(str);
        if (typeBlock == null) {
            return null;
        }
        return typeBlock.getEntry(s);
    }

    public Entry getEntry(ResConfig resConfig, String str) {
        TypeBlock typeBlock = getTypeBlock(resConfig);
        if (typeBlock != null) {
            return typeBlock.getEntry(str);
        }
        return null;
    }

    public TypeBlock getOrCreate(ResConfig resConfig) {
        return getOrCreate(resConfig, false);
    }

    public TypeBlock getOrCreate(ResConfig resConfig, boolean z) {
        return getOrCreate(resConfig, z, false);
    }

    public TypeBlock getOrCreate(ResConfig resConfig, boolean z, boolean z2) {
        TypeBlock typeBlock = getTypeBlock(resConfig, z);
        if (typeBlock != null) {
            return typeBlock;
        }
        byte typeId = getTypeId();
        TypeBlock typeBlockCreateNext = createNext(z, z2);
        typeBlockCreateNext.setTypeId(typeId);
        typeBlockCreateNext.getResConfig().copyFrom(resConfig);
        return typeBlockCreateNext;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TypeBlock getOrCreate(String str) {
        TypeBlock typeBlock = getTypeBlock(str);
        if (typeBlock != null) {
            return typeBlock;
        }
        int highestEntryCount = getHighestEntryCount();
        TypeBlock typeBlock2 = (TypeBlock) createNext();
        typeBlock2.ensureEntriesCount(highestEntryCount);
        typeBlock2.getResConfig().parseQualifiers(str);
        return typeBlock2;
    }

    public TypeBlock getTypeBlock(String str) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeBlock typeBlock = (TypeBlock) it.next();
            if (typeBlock.getResConfig().isEqualQualifiers(str)) {
                return typeBlock;
            }
        }
        return null;
    }

    public TypeBlock getTypeBlock(ResConfig resConfig) {
        if (resConfig == null) {
            return null;
        }
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeBlock typeBlock = (TypeBlock) it.next();
            if (typeBlock != null && resConfig.equals(typeBlock.getResConfig())) {
                return typeBlock;
            }
        }
        return null;
    }

    public TypeBlock getTypeBlock(ResConfig resConfig, boolean z) {
        if (resConfig == null) {
            return null;
        }
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeBlock typeBlock = (TypeBlock) it.next();
            if (typeBlock != null && z == typeBlock.isSparse() && resConfig.equals(typeBlock.getResConfig())) {
                return typeBlock;
            }
        }
        return null;
    }

    public void setTypeId(byte b) {
        this.mTypeId = b;
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ((TypeBlock) it.next()).setTypeId(b);
        }
    }

    public byte getTypeId() {
        byte typeId;
        byte typeId2;
        SpecBlock specBlock = getSpecBlock();
        if (specBlock != null && (typeId2 = specBlock.getTypeId()) != 0) {
            return typeId2;
        }
        byte b = this.mTypeId;
        if (b != 0) {
            return b;
        }
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeBlock typeBlock = (TypeBlock) it.next();
            if (typeBlock != null && (typeId = typeBlock.getTypeId()) != 0) {
                if (specBlock != null) {
                    specBlock.setTypeId(typeId);
                }
                this.mTypeId = typeId;
                return typeId;
            }
        }
        return (byte) 0;
    }

    public Set<ResConfig> listResConfig() {
        HashSet hashSet = new HashSet();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            hashSet.add(((TypeBlock) it.next()).getResConfig());
        }
        return hashSet;
    }

    public Iterator<ResConfig> getResConfigs() {
        return new ComputeIterator(super.m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true), new Function() { // from class: com.reandroid.arsc.array.TypeBlockArray$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeBlock) obj).getResConfig();
            }
        });
    }

    public Iterator<TypeBlock> iteratorNonEmpty() {
        return super.iterator(NON_EMPTY_TESTER);
    }

    public Iterator<TypeBlock> iterator(final ResConfig resConfig) {
        return iterator(new Predicate<TypeBlock>() { // from class: com.reandroid.arsc.array.TypeBlockArray.1
            @Override // java.util.function.Predicate
            public boolean test(TypeBlock typeBlock) {
                return typeBlock.getResConfig().equals(resConfig);
            }
        });
    }

    public boolean hasDuplicateResConfig(boolean z) {
        Iterator<TypeBlock> it;
        HashSet hashSet = new HashSet();
        if (z) {
            it = iteratorNonEmpty();
        } else {
            it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        }
        while (it.hasNext()) {
            Integer numValueOf = Integer.valueOf(it.next().getResConfig().hashCode());
            if (hashSet.contains(numValueOf)) {
                return true;
            }
            hashSet.add(numValueOf);
        }
        return false;
    }

    private SpecBlock getSpecBlock() {
        SpecTypePair specTypePair = (SpecTypePair) getParent(SpecTypePair.class);
        if (specTypePair != null) {
            return specTypePair.getSpecBlock();
        }
        return null;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public TypeBlock newInstance() {
        byte typeId = getTypeId();
        TypeBlock typeBlock = new TypeBlock(false, false);
        typeBlock.setTypeId(typeId);
        return typeBlock;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public TypeBlock[] newArrayInstance(int i) {
        return new TypeBlock[i];
    }

    public TypeBlock createNext(boolean z, boolean z2) {
        byte typeId = getTypeId();
        TypeBlock typeBlock = new TypeBlock(z, z2);
        typeBlock.setTypeId(typeId);
        add(typeBlock);
        return typeBlock;
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        boolean typeBlockArray = true;
        while (typeBlockArray) {
            typeBlockArray = readTypeBlockArray(blockReader);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean readTypeBlockArray(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        if (headerBlock == null || headerBlock.getChunkType() != ChunkType.TYPE) {
            return false;
        }
        TypeHeader typeHeader = TypeHeader.read(blockReader);
        byte typeId = getTypeId();
        if (typeId != 0 && typeHeader.getId().unsignedInt() != typeId) {
            return false;
        }
        int position = blockReader.getPosition();
        ((TypeBlock) createNext()).readBytes(blockReader);
        return blockReader.getPosition() > position;
    }

    public int getHighestEntryId() {
        Iterator<T> it = iterator();
        int i = -1;
        while (it.hasNext()) {
            int highestEntryId = ((TypeBlock) it.next()).getEntryArray().getHighestEntryId();
            if (highestEntryId > i) {
                i = highestEntryId;
            }
        }
        return i;
    }

    public int getHighestEntryCount() {
        Iterator<T> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int size = ((TypeBlock) it.next()).getEntryArray().size();
            if (size > i) {
                i = size;
            }
        }
        return i;
    }

    public void setEntryCount(int i) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeBlock typeBlock = (TypeBlock) it.next();
            if (!typeBlock.isSparse()) {
                typeBlock.setEntryCount(i);
            }
        }
    }

    public TypeString getTypeString() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            TypeString typeString = ((TypeBlock) it.next()).getTypeString();
            if (typeString != null) {
                return typeString;
            }
        }
        return null;
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        JSONArray jSONArray = new JSONArray();
        Iterator<TypeBlock> it = listItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            JSONObject json = it.next().toJson();
            if (json != null) {
                jSONArray.put(i, json);
                i++;
            }
        }
        return jSONArray;
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            createNext(jSONObject.optBoolean(TypeBlock.NAME_is_sparse, false), jSONObject.optBoolean(TypeBlock.NAME_is_offset16, false)).fromJson(jSONObject);
        }
    }

    public void merge(TypeBlockArray typeBlockArray) {
        if (typeBlockArray == null || typeBlockArray == this) {
            return;
        }
        for (TypeBlock typeBlock : typeBlockArray.listItems()) {
            getOrCreate(typeBlock.getResConfig(), typeBlock.isSparse()).merge(typeBlock);
        }
    }

    @Override // java.util.Comparator
    public int compare(TypeBlock typeBlock, TypeBlock typeBlock2) {
        return typeBlock.compareTo(typeBlock2);
    }
}
