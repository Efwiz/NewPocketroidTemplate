package com.reandroid.dex.id;

import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.MethodHandleKey;
import com.reandroid.dex.key.MethodKey;
import com.reandroid.dex.reference.IdItemIndirectReference;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.CombiningIterator;
import java.io.IOException;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class MethodHandle extends IdItem implements Comparable<MethodHandle> {

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    private final IdItemIndirectReference<MethodId> f239id;
    private final IdItemIndirectReference<MethodId> member;

    public MethodHandle() {
        super(8);
        this.f239id = new IdItemIndirectReference<>(SectionType.METHOD_ID, this, 0);
        this.member = new IdItemIndirectReference<>(SectionType.METHOD_ID, this, 4);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public MethodHandleKey getKey() {
        return (MethodHandleKey) checkKey(new MethodHandleKey(getIdKey(), getMemberKey()));
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        MethodHandleKey methodHandleKey = (MethodHandleKey) key;
        setId(methodHandleKey.getId());
        setMember(methodHandleKey.getMember());
    }

    public MethodId getId() {
        return (MethodId) this.f239id.getItem();
    }

    public MethodKey getIdKey() {
        return (MethodKey) this.f239id.getKey();
    }

    public void setId(MethodKey methodKey) {
        this.f239id.setItem(methodKey);
    }

    public MethodId getMember() {
        return (MethodId) this.member.getItem();
    }

    public MethodKey getMemberKey() {
        return (MethodKey) this.member.getKey();
    }

    public void setMember(MethodKey methodKey) {
        this.member.setItem(methodKey);
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return CombiningIterator.two(getId().usedIds(), getMember().usedIds());
    }

    @Override // com.reandroid.dex.id.IdItem, com.reandroid.dex.common.SectionItem
    public SectionType<MethodHandle> getSectionType() {
        return SectionType.METHOD_HANDLE;
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        this.f239id.refresh();
        this.member.refresh();
    }

    @Override // com.reandroid.dex.id.IdItem
    void cacheItems() {
        this.f239id.pullItem();
        this.member.pullItem();
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        MethodId id2 = getId();
        if (id2 == null) {
            smaliWriter.append("error id = ");
            smaliWriter.appendInteger(this.f239id.get());
        } else {
            id2.append(smaliWriter);
        }
        smaliWriter.append(", ");
        MethodId member = getMember();
        if (member == null) {
            smaliWriter.append("error member = ");
            smaliWriter.appendInteger(this.member.get());
        } else {
            member.append(smaliWriter);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(MethodHandle methodHandle) {
        if (methodHandle == null) {
            return -1;
        }
        int iCompare = CompareUtil.compare(getId(), methodHandle.getId());
        return iCompare != 0 ? iCompare : CompareUtil.compare(getMember(), methodHandle.getMember());
    }

    public String toString() {
        return SmaliWriter.toStringSafe(this);
    }
}
