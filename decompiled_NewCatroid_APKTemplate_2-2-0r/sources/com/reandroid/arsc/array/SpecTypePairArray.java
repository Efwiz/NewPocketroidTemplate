package com.reandroid.arsc.array;

import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.chunk.PackageBlock;
import com.reandroid.arsc.chunk.SpecBlock;
import com.reandroid.arsc.chunk.TypeBlock;
import com.reandroid.arsc.container.SpecTypePair;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.arsc.pool.TypeStringPool;
import com.reandroid.arsc.value.Entry;
import com.reandroid.arsc.value.ResConfig;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.json.JSONObject;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
public class SpecTypePairArray extends BlockArray<SpecTypePair> implements JSONConvert<JSONArray>, Comparator<SpecTypePair> {
    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    public void trimConfigSizes(int i) {
        Iterator<SpecTypePair> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            it.next().trimConfigSizes(i);
        }
    }

    public void sort() {
        Iterator<SpecTypePair> it = listItems().iterator();
        while (it.hasNext()) {
            it.next().sortTypes();
        }
        sort(this);
    }

    public void removeEmptyPairs() {
        Iterator<T> itClonedIterator = clonedIterator();
        while (itClonedIterator.hasNext()) {
            SpecTypePair specTypePair = (SpecTypePair) itClonedIterator.next();
            specTypePair.removeEmptyTypeBlocks();
            if (specTypePair.isEmpty()) {
                super.remove(specTypePair);
            }
        }
    }

    public boolean isEmpty() {
        Iterator<SpecTypePair> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            if (!it.next().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public Entry getOrCreateEntry(byte b, short s, String str) {
        return getOrCreateTypeBlock(b, str).getOrCreateEntry(s);
    }

    public Entry getOrCreateEntry(byte b, short s, ResConfig resConfig) {
        return getOrCreateTypeBlock(b, resConfig).getOrCreateEntry(s);
    }

    public Entry getEntry(byte b, short s, String str) {
        TypeBlock typeBlock = getTypeBlock(b, str);
        if (typeBlock == null) {
            return null;
        }
        return typeBlock.getEntry(s);
    }

    public TypeBlock getOrCreateTypeBlock(byte b, String str) {
        return getOrCreate(b).getOrCreateTypeBlock(str);
    }

    public TypeBlock getOrCreateTypeBlock(byte b, ResConfig resConfig) {
        return getOrCreate(b).getOrCreateTypeBlock(resConfig);
    }

    public TypeBlock getTypeBlock(byte b, String str) {
        SpecTypePair specTypePair = getSpecTypePair(b);
        if (specTypePair == null) {
            return null;
        }
        return specTypePair.getTypeBlock(str);
    }

    public TypeBlock getOrCreate(byte b, ResConfig resConfig) {
        return getOrCreate(b).getTypeBlockArray().getOrCreate(resConfig);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpecTypePair getOrCreate(byte b) {
        SpecTypePair specTypePair = getSpecTypePair(b);
        if (specTypePair != null) {
            return specTypePair;
        }
        SpecTypePair specTypePair2 = (SpecTypePair) createNext();
        specTypePair2.setTypeId(b);
        return specTypePair2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpecTypePair getOrCreate(String str) {
        SpecTypePair specTypePair = getSpecTypePair(str);
        if (specTypePair != null) {
            return specTypePair;
        }
        byte id2 = (byte) getOrCreateTypeString(str).getId();
        SpecTypePair specTypePair2 = (SpecTypePair) createNext();
        specTypePair2.setTypeId(id2);
        return specTypePair2;
    }

    public TypeBlock getOrCreateTypeBlock(String str, ResConfig resConfig) {
        return getOrCreate(str).getOrCreateTypeBlock(resConfig);
    }

    public TypeBlock getOrCreateTypeBlock(String str, String str2) {
        return getOrCreate(str).getOrCreateTypeBlock(str2);
    }

    public SpecTypePair getSpecTypePair(int i) {
        return getSpecTypePair((byte) i);
    }

    public SpecTypePair getSpecTypePair(byte b) {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            SpecTypePair specTypePair = (SpecTypePair) it.next();
            if (specTypePair != null && specTypePair.getTypeId() == b) {
                return specTypePair;
            }
        }
        return null;
    }

    public SpecTypePair getSpecTypePair(String str) {
        if (str == null) {
            return null;
        }
        Iterator<SpecTypePair> it = m2755lambda$listItems$0$comreandroidarscbaseBlockArray(true);
        while (it.hasNext()) {
            SpecTypePair next = it.next();
            if (next.isEqualTypeName(str)) {
                return next;
            }
        }
        return null;
    }

    public Entry getAnyEntry(byte b, short s) {
        SpecTypePair specTypePair;
        if (b == 0 || (specTypePair = getSpecTypePair(b)) == null) {
            return null;
        }
        return specTypePair.getAnyEntry(s);
    }

    public Entry getAnyEntry(String str, String str2) {
        SpecTypePair specTypePair = getSpecTypePair(str);
        if (specTypePair != null) {
            return specTypePair.getAnyEntry(str2);
        }
        return null;
    }

    public Entry getEntry(String str, String str2, String str3) {
        ResConfig resConfig = new ResConfig();
        resConfig.parseQualifiers(str);
        return getEntry(resConfig, str2, str3);
    }

    public Entry getEntry(ResConfig resConfig, String str, String str2) {
        SpecTypePair specTypePair = getSpecTypePair(str);
        if (specTypePair != null) {
            return specTypePair.getEntry(resConfig, str2);
        }
        return null;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public SpecTypePair newInstance() {
        return new SpecTypePair();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public SpecTypePair[] newArrayInstance(int i) {
        return new SpecTypePair[i];
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        validateEntryCounts();
    }

    private void validateEntryCounts() {
        for (Map.Entry<Byte, Integer> entry : mapHighestEntryCount().entrySet()) {
            byte bByteValue = entry.getKey().byteValue();
            int iIntValue = entry.getValue().intValue();
            SpecTypePair specTypePair = getSpecTypePair(bByteValue);
            specTypePair.getSpecBlock().setEntryCount(iIntValue);
            specTypePair.getTypeBlockArray().setEntryCount(iIntValue);
        }
    }

    private Map<Byte, Integer> mapHighestEntryCount() {
        HashMap map = new HashMap();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            SpecTypePair specTypePair = (SpecTypePair) it.next();
            int highestEntryCount = specTypePair.getHighestEntryCount();
            byte typeId = specTypePair.getTypeId();
            Integer num = (Integer) map.get(Byte.valueOf(typeId));
            if (num == null || highestEntryCount > num.intValue()) {
                map.put(Byte.valueOf(typeId), Integer.valueOf(highestEntryCount));
            }
        }
        return map;
    }

    public int getSmallestTypeId() {
        Iterator<T> it = iterator();
        int i = 0;
        boolean z = false;
        while (it.hasNext()) {
            int id2 = ((SpecTypePair) it.next()).getId();
            if (!z) {
                i = id2;
            }
            if (id2 < i) {
                i = id2;
            }
            z = true;
        }
        return i;
    }

    public int getHighestTypeId() {
        Iterator<T> it = iterator();
        int i = 0;
        while (it.hasNext()) {
            int id2 = ((SpecTypePair) it.next()).getId();
            if (id2 > i) {
                i = id2;
            }
        }
        return i;
    }

    private TypeString getOrCreateTypeString(String str) {
        TypeStringPool typeStringPool = getTypeStringPool();
        if (typeStringPool == null) {
            return null;
        }
        TypeString string = typeStringPool.getString(str);
        return string != null ? string : typeStringPool.getOrCreate(typeStringPool.getLastId() + 1, str);
    }

    private TypeStringPool getTypeStringPool() {
        PackageBlock packageBlock = getPackageBlock();
        if (packageBlock != null) {
            return packageBlock.getTypeStringPool();
        }
        return null;
    }

    private PackageBlock getPackageBlock() {
        return (PackageBlock) getParentInstance(PackageBlock.class);
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        return toJson(false);
    }

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
        if (jSONArray == null) {
            return;
        }
        int length = jSONArray.length();
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            getOrCreate((byte) jSONObject.getJSONObject(SpecBlock.NAME_spec).getInt("id")).fromJson(jSONObject);
        }
    }

    public JSONArray toJson(boolean z) {
        JSONArray jSONArray = new JSONArray();
        Iterator<SpecTypePair> it = listItems().iterator();
        int i = 0;
        while (it.hasNext()) {
            JSONObject json = it.next().toJson(z);
            if (json != null) {
                jSONArray.put(i, json);
                i++;
            }
        }
        return jSONArray;
    }

    public void merge(SpecTypePairArray specTypePairArray) {
        if (specTypePairArray == null || specTypePairArray == this) {
            return;
        }
        for (SpecTypePair specTypePair : specTypePairArray.listItems()) {
            if (!specTypePair.isEmpty()) {
                getOrCreate(specTypePair.getTypeId()).merge(specTypePair);
            }
        }
    }

    @Deprecated
    public SpecTypePair searchByTypeName(String str) {
        if (str == null) {
            return null;
        }
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            SpecTypePair specTypePair = (SpecTypePair) it.next();
            if (str.equals(specTypePair.getTypeName())) {
                return specTypePair;
            }
        }
        return null;
    }

    @Override // java.util.Comparator
    public int compare(SpecTypePair specTypePair, SpecTypePair specTypePair2) {
        return specTypePair.compareTo(specTypePair2);
    }
}
