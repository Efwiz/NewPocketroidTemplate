package com.reandroid.dex.reference;

import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.data.TypeList;
import com.reandroid.dex.id.TypeId;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.EmptyIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class TypeListReference extends DataItemIndirectReference<TypeList> implements Iterable<TypeId> {
    public TypeListReference(SectionItem sectionItem, int i, int i2) {
        super(SectionType.TYPE_LIST, sectionItem, i, i2);
    }

    @Override // java.lang.Iterable
    public Iterator<TypeId> iterator() {
        TypeList typeList = (TypeList) getItem();
        if (typeList != null) {
            return typeList.iterator();
        }
        return EmptyIterator.of();
    }

    public Iterator<TypeKey> getTypeKeys() {
        TypeList typeList = (TypeList) getItem();
        if (typeList != null) {
            return typeList.getTypeKeys();
        }
        return EmptyIterator.of();
    }

    @Override // com.reandroid.dex.reference.DataItemIndirectReference, com.reandroid.dex.reference.DexReference
    public TypeListKey getKey() {
        return (TypeListKey) super.getKey();
    }

    public void add(String str) {
        TypeListKey typeListKey;
        TypeListKey key = getKey();
        if (key != null) {
            typeListKey = key.add(str);
        } else {
            typeListKey = new TypeListKey(new String[]{str});
        }
        setItem(typeListKey);
    }

    public void remove(int i) {
        TypeListKey key = getKey();
        if (key == null) {
            return;
        }
        setItem(key.remove(i));
    }

    public TypeKey getType(int i) {
        TypeId typeId = get(i);
        if (typeId != null) {
            return typeId.getKey();
        }
        return null;
    }

    public TypeId get(int i) {
        TypeList typeList = (TypeList) getItem();
        if (typeList != null) {
            return typeList.getTypeId(i);
        }
        return null;
    }

    public TypeId getForRegister(int i) {
        TypeList typeList = (TypeList) getItem();
        if (typeList != null) {
            return typeList.getTypeIdForRegister(i);
        }
        return null;
    }

    public int size() {
        TypeList typeList = (TypeList) getItem();
        if (typeList != null) {
            return typeList.size();
        }
        return 0;
    }

    public int indexOf(String str) {
        TypeListKey key = getKey();
        if (key != null) {
            return key.indexOf(str);
        }
        return -1;
    }
}
