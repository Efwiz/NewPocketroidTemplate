package com.reandroid.dex.data;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.pool.DexSectionPool;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliDirective;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.ComputeIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Function;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: loaded from: classes11.dex */
public class TypeList extends ShortList implements ModifiableKeyItem, SmaliFormat, Iterable<TypeId>, Comparable<TypeList> {
    private TypeId[] typeIds;

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public TypeListKey getKey() {
        return (TypeListKey) checkKey(TypeListKey.create(this));
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        setKey((TypeListKey) key);
    }

    public void setKey(TypeListKey typeListKey) {
        TypeListKey key = getKey();
        if (typeListKey.equals(key)) {
            return;
        }
        String[] parameterNames = typeListKey.getParameterNames();
        if (parameterNames == null) {
            setSize(0);
            return;
        }
        setSize(0);
        DexSectionPool orCreatePool = getOrCreatePool(SectionType.TYPE_ID);
        for (String str : parameterNames) {
            add((TypeId) orCreatePool.getOrCreate(TypeKey.create(str)));
        }
        keyChanged(key);
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<TypeList> getSectionType() {
        return SectionType.TYPE_LIST;
    }

    public void addAll(Iterator<String> it) {
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public void add(Iterator<TypeKey> it) {
        while (it.hasNext()) {
            add(it.next());
        }
    }

    public void add(String str) {
        add(TypeKey.create(str));
    }

    public void add(TypeKey typeKey) {
        if (typeKey != null) {
            add((TypeId) getOrCreateSectionItem(SectionType.TYPE_ID, typeKey));
        }
    }

    public void add(TypeId typeId) {
        if (typeId != null) {
            add(typeId.getIdx());
        } else {
            add(0);
        }
    }

    public boolean remove(TypeId typeId) {
        if (typeId != null) {
            return remove(indexOf(typeId.getIdx()));
        }
        return false;
    }

    public boolean remove(TypeKey typeKey) {
        return remove(get(typeKey));
    }

    public Iterator<TypeKey> getTypeKeys() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.data.TypeList$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeId) obj).getKey();
            }
        });
    }

    public Iterator<String> getTypeNames() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.data.TypeList$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((TypeId) obj).getName();
            }
        });
    }

    @Override // java.lang.Iterable
    public Iterator<TypeId> iterator() {
        return ArrayIterator.of(getTypeIds());
    }

    @Override // com.reandroid.dex.data.IntegerList, com.reandroid.common.IntegerArray
    public int size() {
        return super.size();
    }

    public boolean contains(TypeKey typeKey) {
        if (typeKey == null) {
            return false;
        }
        Iterator<TypeId> it = iterator();
        while (it.hasNext()) {
            if (typeKey.equals(it.next().getKey())) {
                return true;
            }
        }
        return false;
    }

    public TypeId get(TypeKey typeKey) {
        if (typeKey == null) {
            return null;
        }
        for (TypeId typeId : this) {
            if (typeKey.equals(typeId.getKey())) {
                return typeId;
            }
        }
        return null;
    }

    public TypeId[] getTypeIds() {
        return this.typeIds;
    }

    public String[] getNames() {
        TypeId[] typeIds = getTypeIds();
        if (typeIds == null) {
            return null;
        }
        int length = typeIds.length;
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = typeIds[i].getName();
        }
        return strArr;
    }

    public TypeId getTypeId(int i) {
        TypeId[] typeIds = getTypeIds();
        if (typeIds == null || i < 0 || i >= typeIds.length) {
            return null;
        }
        return typeIds[i];
    }

    public TypeId getTypeIdForRegister(int i) {
        TypeId[] typeIds = getTypeIds();
        if (typeIds == null) {
            return null;
        }
        int i2 = 0;
        for (TypeId typeId : typeIds) {
            if (i2 == i) {
                return typeId;
            }
            i2 = typeId.isWide() ? i2 + 2 : i2 + 1;
        }
        return null;
    }

    public int getParameterRegistersCount() {
        TypeId[] typeIds = getTypeIds();
        if (typeIds == null) {
            return 0;
        }
        int i = 0;
        for (TypeId typeId : typeIds) {
            i = typeId.isWide() ? i + 2 : i + 1;
        }
        return i;
    }

    @Override // com.reandroid.dex.data.IntegerList
    void onChanged() {
        cacheTypeIds();
    }

    private void cacheTypeIds() {
        TypeId[] typeIdArr = (TypeId[]) getSectionItem(SectionType.TYPE_ID, toArray());
        this.typeIds = typeIdArr;
        if (typeIdArr == null) {
            return;
        }
        for (TypeId typeId : typeIdArr) {
            typeId.addUsageType(UsageMarker.USAGE_INTERFACE);
        }
    }

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected void onPreRefresh() {
        refreshTypeIds();
    }

    private void refreshTypeIds() {
        TypeId[] typeIds = getTypeIds();
        if (typeIds == null) {
            setSize(0);
            return;
        }
        int length = typeIds.length;
        setSize(length, false);
        for (int i = 0; i < length; i++) {
            TypeId typeId = (TypeId) typeIds[i].getReplace();
            typeIds[i] = typeId;
            typeId.addUsageType(UsageMarker.USAGE_INTERFACE);
            put(i, typeIds[i].getIdx());
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        Iterator<TypeId> it = iterator();
        while (it.hasNext()) {
            it.next().append(smaliWriter);
        }
    }

    public void appendInterfaces(SmaliWriter smaliWriter) throws IOException {
        SmaliDirective smaliDirective = null;
        for (TypeId typeId : this) {
            smaliWriter.newLine();
            if (smaliDirective == null) {
                smaliWriter.appendComment(Namer.METADATA_SUPERTYPES);
                smaliWriter.newLine();
                smaliDirective = SmaliDirective.IMPLEMENTS;
            }
            smaliDirective.append(smaliWriter);
            typeId.append(smaliWriter);
        }
        if (smaliDirective != null) {
            smaliWriter.newLine();
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(TypeList typeList) {
        if (typeList == null) {
            return -1;
        }
        return SectionTool.compareIdx(iterator(), typeList.iterator());
    }

    @Override // com.reandroid.dex.data.ShortList, com.reandroid.dex.data.IntegerList
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<TypeId> it = iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }

    public static boolean equals(TypeList typeList, TypeList typeList2) {
        if (typeList == typeList2) {
            return true;
        }
        return typeList != null && CompareUtil.compare(typeList.getNames(), typeList2.getNames()) == 0;
    }
}
