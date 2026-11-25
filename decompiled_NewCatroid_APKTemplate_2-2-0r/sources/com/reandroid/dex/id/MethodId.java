package com.reandroid.dex.id;

import com.reandroid.dex.data.TypeList;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.reference.IdItemIndirectReference;
import com.reandroid.dex.reference.IdItemIndirectShortReference;
import com.reandroid.dex.reference.IdReference;
import com.reandroid.dex.reference.IndirectStringReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class MethodId extends IdItem implements Comparable<MethodId> {
    private static final int SIZE = 8;
    private final IdItemIndirectReference<TypeId> defining;
    private final IndirectStringReference nameReference;
    private final IdItemIndirectReference<ProtoId> proto;

    public MethodId() {
        super(8);
        this.defining = new IdItemIndirectShortReference(SectionType.TYPE_ID, this, 0, USAGE_METHOD);
        this.proto = new IdItemIndirectShortReference(SectionType.PROTO_ID, this, 2, USAGE_METHOD);
        this.nameReference = new IndirectStringReference(this, 4, StringId.USAGE_METHOD_NAME);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.singleThree(this, SingleIterator.of(this.defining.getItem()), SingleIterator.of(this.nameReference.getItem()), ((ProtoId) this.proto.getItem()).usedIds());
    }

    public String getName() {
        return this.nameReference.getString();
    }

    public void setName(String str) {
        this.nameReference.setString(str);
    }

    public void setName(StringKey stringKey) {
        this.nameReference.setItem(stringKey);
    }

    IndirectStringReference getNameReference() {
        return this.nameReference;
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

    public int getParametersCount() {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getParametersCount();
        }
        return 0;
    }

    public int getParameterRegistersCount() {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getParameterRegistersCount();
        }
        return 0;
    }

    public TypeId getParameter(int i) {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getParameter(i);
        }
        return null;
    }

    public String[] getParameterNames() {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getParameterNames();
        }
        return null;
    }

    public TypeList getParameterTypes() {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getTypeList();
        }
        return null;
    }

    public ProtoId getProto() {
        return (ProtoId) this.proto.getItem();
    }

    public void setProto(ProtoId protoId) {
        this.proto.setItem(protoId);
    }

    public String getReturnTypeName() {
        TypeKey returnType = getReturnType();
        if (returnType != null) {
            return returnType.getTypeName();
        }
        return null;
    }

    public TypeKey getReturnType() {
        ProtoId proto = getProto();
        if (proto != null) {
            return proto.getReturnType();
        }
        return null;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<MethodId> getSectionType() {
        return SectionType.METHOD_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public MethodKey getKey() {
        return (MethodKey) checkKey(MethodKey.create(this));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        setKey((MethodKey) key);
    }

    public void setKey(MethodKey methodKey) {
        MethodKey key = getKey();
        if (methodKey.equals(key)) {
            return;
        }
        this.defining.setItem(methodKey.getDeclaring());
        this.nameReference.setString(methodKey.getName());
        this.proto.setItem(methodKey.getProtoKey());
        keyChanged(key);
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.defining.refresh();
        this.proto.refresh();
        this.nameReference.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.defining.pullItem();
        this.proto.pullItem();
        this.nameReference.pullItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        append(smaliWriter, true);
    }

    public void append(SmaliWriter smaliWriter, boolean z) throws IOException {
        if (z) {
            getDefiningId().append(smaliWriter);
            smaliWriter.append("->");
        }
        smaliWriter.append((CharSequence) getName());
        smaliWriter.appendRequired(getProto());
    }

    @Override // java.lang.Comparable
    public int compareTo(MethodId methodId) {
        if (methodId == null) {
            return -1;
        }
        int iCompareTo = this.defining.compareTo((IdReference) methodId.defining);
        if (iCompareTo != 0) {
            return iCompareTo;
        }
        int iCompareTo2 = this.nameReference.compareTo((IdReference) methodId.nameReference);
        return iCompareTo2 != 0 ? iCompareTo2 : this.proto.compareTo((IdReference) methodId.proto);
    }

    public String toString() {
        return getDefiningId() + "->" + getName() + getProto();
    }

    public static boolean equals(MethodId methodId, MethodId methodId2) {
        return equals(false, methodId, methodId2);
    }

    public static boolean equals(boolean z, MethodId methodId, MethodId methodId2) {
        if (methodId == methodId2) {
            return true;
        }
        if (methodId == null || !IndirectStringReference.equals(methodId.getNameReference(), methodId2.getNameReference())) {
            return false;
        }
        if (z || TypeId.equals(methodId.getDefiningId(), methodId2.getDefiningId())) {
            return TypeList.equals(methodId.getParameterTypes(), methodId2.getParameterTypes());
        }
        return false;
    }
}
