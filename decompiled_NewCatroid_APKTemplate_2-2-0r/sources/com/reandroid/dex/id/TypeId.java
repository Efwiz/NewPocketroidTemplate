package com.reandroid.dex.id;

import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.IdReference;
import com.reandroid.dex.reference.IndirectStringReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.EmptyIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class TypeId extends IdItem implements Comparable<TypeId> {
    private final IndirectStringReference nameReference;

    public TypeId() {
        super(4);
        this.nameReference = new IndirectStringReference(this, 0, StringId.USAGE_TYPE_NAME);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return EmptyIterator.of();
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<TypeId> getSectionType() {
        return SectionType.TYPE_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public TypeKey getKey() {
        return (TypeKey) checkKey(TypeKey.create(getName()));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        setKey((TypeKey) key);
    }

    public void setKey(TypeKey typeKey) {
        TypeKey key = getKey();
        if (Objects.equals(typeKey, key)) {
            return;
        }
        this.nameReference.setString(typeKey.getTypeName());
        keyChanged(key);
    }

    public String getName() {
        StringId nameId = getNameId();
        if (nameId != null) {
            return nameId.getString();
        }
        return null;
    }

    public boolean isWide() {
        TypeKey key = getKey();
        if (key != null) {
            return key.isWide();
        }
        return false;
    }

    public StringId getNameId() {
        return (StringId) this.nameReference.getItem();
    }

    public IndirectStringReference getNameReference() {
        return this.nameReference;
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.nameReference.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.nameReference.pullItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getName());
    }

    @Override // java.lang.Comparable
    public int compareTo(TypeId typeId) {
        if (typeId == null) {
            return -1;
        }
        return this.nameReference.compareTo((IdReference) typeId.nameReference);
    }

    public String toString() {
        String name2 = getName();
        return name2 != null ? name2 : getIndex() + ":string-index=" + this.nameReference.get();
    }

    public static boolean equals(TypeId typeId, TypeId typeId2) {
        if (typeId == typeId2) {
            return true;
        }
        if (typeId == null) {
            return false;
        }
        return ObjectsUtil.equals(typeId.getName(), typeId2.getName());
    }
}
