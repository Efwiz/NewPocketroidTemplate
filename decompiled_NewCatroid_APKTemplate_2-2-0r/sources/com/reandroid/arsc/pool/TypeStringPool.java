package com.reandroid.arsc.pool;

import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.array.StringArray;
import com.reandroid.arsc.array.TypeStringArray;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.TypeString;
import com.reandroid.utils.collection.CollectionUtil;

/* JADX INFO: loaded from: classes10.dex */
public class TypeStringPool extends StringPool<TypeString> {
    private final IntegerItem mTypeIdOffset;

    public TypeStringPool(boolean z, IntegerItem integerItem) {
        super(z, false);
        this.mTypeIdOffset = integerItem;
    }

    public int getLastId() {
        return toTypeId(size() - 1);
    }

    public int idOf(String str) {
        return idOf(getByName(str));
    }

    public int idOf(TypeString typeString) {
        if (typeString == null) {
            return 0;
        }
        return toTypeId(typeString.getIndex());
    }

    public TypeString getByName(String str) {
        for (TypeString typeString : this) {
            if (str.equals(typeString.get())) {
                return typeString;
            }
        }
        return null;
    }

    public TypeString getById(int i) {
        return (TypeString) super.get(toIndex(i));
    }

    public TypeString getOrCreate(int i, String str) {
        getStringsArray().ensureSize(toIndex(i) + 1);
        TypeString byId = getById(i);
        byId.set(str);
        return byId;
    }

    private int toIndex(int i) {
        return (i - 1) - this.mTypeIdOffset.get();
    }

    private int toTypeId(int i) {
        return i + 1 + this.mTypeIdOffset.get();
    }

    @Override // com.reandroid.arsc.pool.StringPool
    @Deprecated
    public final TypeString getOrCreate(String str) {
        TypeString typeString = (TypeString) CollectionUtil.getSingle(getAll(str));
        if (typeString != null) {
            return typeString;
        }
        throw new IllegalArgumentException("Can not create TypeString (" + str + ") without type id. use getOrCreate(typeId, typeName)");
    }

    @Override // com.reandroid.arsc.pool.StringPool
    StringArray<TypeString> newInstance(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        return new TypeStringArray(offsetArray, integerItem, integerItem2, z);
    }
}
