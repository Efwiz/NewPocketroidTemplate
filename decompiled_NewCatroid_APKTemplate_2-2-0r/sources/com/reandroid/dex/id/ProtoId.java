package com.reandroid.dex.id;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.data.TypeList;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ProtoKey;
import com.reandroid.dex.key.StringKey;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.key.TypeListKey;
import com.reandroid.dex.reference.IdItemIndirectReference;
import com.reandroid.dex.reference.IndirectStringReference;
import com.reandroid.dex.reference.TypeListReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.collection.CombiningIterator;
import com.reandroid.utils.collection.EmptyIterator;
import com.reandroid.utils.collection.SingleIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class ProtoId extends IdItem implements Comparable<ProtoId> {
    private static final int SIZE = 12;
    private final TypeListReference parameters;
    private final IdItemIndirectReference<TypeId> returnType;
    private final IndirectStringReference shorty;

    public ProtoId() {
        super(12);
        this.shorty = new IndirectStringReference(this, 0, UsageMarker.USAGE_SHORTY);
        this.returnType = new IdItemIndirectReference<>(SectionType.TYPE_ID, this, 4, UsageMarker.USAGE_PROTO);
        this.parameters = new TypeListReference(this, 8, UsageMarker.USAGE_PROTO);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.singleThree(this, SingleIterator.of(this.returnType.getItem()), getParameterIds(), SingleIterator.of(this.shorty.getItem()));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<ProtoId> getSectionType() {
        return SectionType.PROTO_ID;
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public ProtoKey getKey() {
        return (ProtoKey) checkKey(ProtoKey.create(this));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        setKey((ProtoKey) key);
    }

    public void setKey(ProtoKey protoKey) {
        ProtoKey key = getKey();
        if (protoKey.equals(key)) {
            return;
        }
        this.shorty.setString(protoKey.getShorty());
        this.returnType.setItem(protoKey.getReturnType());
        this.parameters.setItem(protoKey.getParameterListKey());
        keyChanged(key);
    }

    public int getParameterRegistersCount() {
        TypeList typeList = getTypeList();
        if (typeList != null) {
            return typeList.getParameterRegistersCount();
        }
        return 0;
    }

    public int getParametersCount() {
        TypeList typeList = getTypeList();
        if (typeList != null) {
            return typeList.size();
        }
        return 0;
    }

    public TypeId getParameter(int i) {
        return this.parameters.get(i);
    }

    public TypeId getForRegister(int i) {
        return this.parameters.getForRegister(i);
    }

    public String[] getParameterNames() {
        TypeList typeList = getTypeList();
        if (typeList != null) {
            return typeList.getNames();
        }
        return null;
    }

    public Iterator<TypeId> getParameterIds() {
        TypeList typeList = getTypeList();
        if (typeList != null) {
            return typeList.iterator();
        }
        return EmptyIterator.of();
    }

    public TypeList getTypeList() {
        return (TypeList) this.parameters.getItem();
    }

    public TypeListReference getParametersReference() {
        return this.parameters;
    }

    public void setParameters(TypeListKey typeListKey) {
        this.parameters.setItem(typeListKey);
    }

    public TypeListKey getParameters() {
        return this.parameters.getKey();
    }

    public TypeKey getReturnType() {
        return (TypeKey) this.returnType.getKey();
    }

    public TypeId getReturnTypeId() {
        return (TypeId) this.returnType.getItem();
    }

    public String getShorty() {
        return this.shorty.getString();
    }

    public void setShorty(StringKey stringKey) {
        this.shorty.setItem(stringKey);
    }

    public void setShorty(String str) {
        this.shorty.setString(str);
    }

    public void rebuildShorty() {
        this.shorty.setString(getKey().getShorty());
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.shorty.refresh();
        this.returnType.refresh();
        this.parameters.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.shorty.pullItem();
        this.returnType.pullItem();
        this.parameters.pullItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append('(');
        Iterator<TypeId> parameterIds = getParameterIds();
        while (parameterIds.hasNext()) {
            parameterIds.next().append(smaliWriter);
        }
        smaliWriter.append(')');
        smaliWriter.appendRequired(getReturnTypeId());
    }

    @Override // java.lang.Comparable
    public int compareTo(ProtoId protoId) {
        if (protoId == this) {
            return 0;
        }
        if (protoId == null) {
            return -1;
        }
        int iCompareIdx = SectionTool.compareIdx(getReturnTypeId(), protoId.getReturnTypeId());
        return iCompareIdx != 0 ? iCompareIdx : SectionTool.compareIdx(getParameterIds(), protoId.getParameterIds());
    }

    public String toString() {
        ProtoKey key = getKey();
        if (key != null) {
            return key.toString();
        }
        return "(" + getTypeList() + Constant.AFTER_QUTO + getReturnTypeId();
    }
}
