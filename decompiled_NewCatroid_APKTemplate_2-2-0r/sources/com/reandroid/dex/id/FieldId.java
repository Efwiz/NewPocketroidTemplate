package com.reandroid.dex.id;

import com.reandroid.dex.key.FieldKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.IdItemIndirectReference;
import com.reandroid.dex.reference.IdItemIndirectShortReference;
import com.reandroid.dex.reference.IndirectStringReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: loaded from: classes11.dex */
public class FieldId extends IdItem implements Comparable<FieldId> {
    private final IdItemIndirectReference<TypeId> defining;
    private final IdItemIndirectReference<TypeId> fieldType;
    private final IndirectStringReference nameReference;

    public FieldId() {
        super(8);
        this.defining = new IdItemIndirectShortReference(SectionType.TYPE_ID, this, 0, USAGE_FIELD_CLASS);
        this.fieldType = new IdItemIndirectShortReference(SectionType.TYPE_ID, this, 2, USAGE_FIELD_TYPE);
        this.nameReference = new IndirectStringReference(this, 4, USAGE_FIELD_NAME);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.singleThree(this, SingleIterator.of(this.defining.getItem()), SingleIterator.of(this.nameReference.getItem()), SingleIterator.of(this.fieldType.getItem()));
    }

    public String getName() {
        return this.nameReference.getString();
    }

    public void setName(String str) {
        this.nameReference.setString(str);
    }

    public StringId getNameId() {
        return (StringId) this.nameReference.getItem();
    }

    IndirectStringReference getNameReference() {
        return this.nameReference;
    }

    public String getClassName() {
        TypeId definingId = getDefiningId();
        if (definingId != null) {
            return definingId.getName();
        }
        return null;
    }

    public TypeKey getDefining() {
        return (TypeKey) this.defining.getKey();
    }

    public void setDefining(TypeKey typeKey) {
        this.defining.setItem(typeKey);
    }

    public TypeId getDefiningId() {
        return (TypeId) this.defining.getItem();
    }

    public void setDefining(TypeId typeId) {
        this.defining.setItem(typeId);
    }

    public TypeKey getFieldType() {
        return (TypeKey) this.fieldType.getKey();
    }

    public void setFieldType(TypeKey typeKey) {
        this.fieldType.setItem(typeKey);
    }

    public TypeId getFieldTypeId() {
        return (TypeId) this.fieldType.getItem();
    }

    public void setFieldType(TypeId typeId) {
        this.fieldType.setItem(typeId);
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.defining.refresh();
        this.fieldType.refresh();
        this.nameReference.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.defining.pullItem();
        this.fieldType.pullItem();
        this.nameReference.pullItem();
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<FieldId> getSectionType() {
        return SectionType.FIELD_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public FieldKey getKey() {
        return (FieldKey) checkKey(FieldKey.create(this));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        setKey((FieldKey) key);
    }

    public void setKey(FieldKey fieldKey) {
        FieldKey key = getKey();
        if (Objects.equals(fieldKey, key)) {
            return;
        }
        this.defining.setItem(fieldKey.getDeclaring());
        this.nameReference.setString(fieldKey.getName());
        this.fieldType.setItem(fieldKey.getType());
        keyChanged(key);
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        append(smaliWriter, true);
    }

    public void append(SmaliWriter smaliWriter, boolean z) throws IOException {
        if (z) {
            smaliWriter.appendRequired(getDefiningId());
            smaliWriter.append("->");
        }
        smaliWriter.append((CharSequence) getName());
        smaliWriter.append(':');
        smaliWriter.appendRequired(getFieldTypeId());
    }

    @Override // java.lang.Comparable
    public int compareTo(FieldId fieldId) {
        if (fieldId == null) {
            return -1;
        }
        int iCompare = CompareUtil.compare(getDefiningId(), fieldId.getDefiningId());
        if (iCompare != 0) {
            return iCompare;
        }
        int iCompare2 = CompareUtil.compare(getNameReference(), fieldId.getNameReference());
        return iCompare2 != 0 ? iCompare2 : CompareUtil.compare(getFieldTypeId(), fieldId.getFieldTypeId());
    }

    public String toString() {
        FieldKey key = getKey();
        if (key != null) {
            return key.toString();
        }
        return getDefiningId() + "->" + getNameId() + ":" + getFieldTypeId();
    }

    public static boolean equals(FieldId fieldId, FieldId fieldId2) {
        return equals(false, fieldId, fieldId2);
    }

    public static boolean equals(boolean z, FieldId fieldId, FieldId fieldId2) {
        if (fieldId == fieldId2) {
            return true;
        }
        if (fieldId != null && IndirectStringReference.equals(fieldId.getNameReference(), fieldId2.getNameReference())) {
            return z || TypeId.equals(fieldId.getDefiningId(), fieldId2.getDefiningId());
        }
        return false;
    }
}
