package com.reandroid.dex.data;

import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.common.SectionTool;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiIndex extends BlockItem implements Comparable<HiddenApiIndex>, BlockRefresh {
    private final ClassId classId;
    private final IntegerReference dataOffset;
    private HiddenApiData hiddenApiData;

    public HiddenApiIndex(ClassId classId) {
        super(4);
        this.dataOffset = new IndirectInteger(this, 0);
        this.classId = classId;
    }

    public HiddenApiFlagValue get(Key key) {
        ClassData classData;
        Def<?> def;
        HiddenApiData hiddenApiData = getHiddenApiData();
        if (hiddenApiData == null || (classData = getClassId().getClassData()) == null || (def = classData.get(key)) == null) {
            return null;
        }
        return hiddenApiData.get(def);
    }

    public IntegerReference getDataOffset() {
        return this.dataOffset;
    }

    public boolean hasValidDataOffset() {
        return this.dataOffset.get() != 0;
    }

    public HiddenApiData getHiddenApiData() {
        return this.hiddenApiData;
    }

    void linkData(HiddenApiData hiddenApiData) {
        this.hiddenApiData = hiddenApiData;
        hiddenApiData.setOffsetReference(getDataOffset());
        hiddenApiData.setClassId(getClassId());
    }

    public TypeKey getClassType() {
        ClassId classId = getClassId();
        if (classId != null) {
            return classId.getKey();
        }
        return null;
    }

    public ClassId getClassId() {
        return this.classId;
    }

    private Section<ClassId> getClassIdSection() {
        SectionList parentSectionList = getParentSectionList();
        if (parentSectionList != null) {
            return parentSectionList.getSection(SectionType.CLASS_ID);
        }
        return null;
    }

    private SectionList getParentSectionList() {
        return (SectionList) getParentInstance(SectionList.class);
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.onReadBytes(blockReader);
    }

    public void removeSelf() {
        HiddenApiIndexList hiddenApiIndexList = (HiddenApiIndexList) getParentInstance(HiddenApiIndexList.class);
        if (hiddenApiIndexList != null) {
            hiddenApiIndexList.remove(this);
        }
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        ClassId classId = getClassId();
        return classId == null || classId.isRemoved();
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        if (getClassId().isRemoved()) {
            removeSelf();
        } else {
            HiddenApiData hiddenApiData = getHiddenApiData();
            this.dataOffset.set(hiddenApiData == null ? 0 : hiddenApiData.getOffset());
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(HiddenApiIndex hiddenApiIndex) {
        if (hiddenApiIndex == null) {
            return -1;
        }
        return SectionTool.compareIdx(getClassId(), hiddenApiIndex.getClassId());
    }

    public String toString() {
        return getClassType() + " {" + getDataOffset() + "}";
    }
}
