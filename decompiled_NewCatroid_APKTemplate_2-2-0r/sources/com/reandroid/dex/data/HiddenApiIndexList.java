package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.id.ClassId;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionList;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.FilterIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiIndexList extends BlockList<HiddenApiIndex> {
    public HiddenApiIndexList() {
        setCreator(new HiddenApiIndexCreator(this));
    }

    public HiddenApiFlagValue getFlagValue(Key key) {
        HiddenApiIndex hiddenApiIndex = get(key.getDeclaring());
        if (hiddenApiIndex != null) {
            return hiddenApiIndex.get(key);
        }
        return null;
    }

    public HiddenApiIndex get(TypeKey typeKey) {
        Section<ClassId> classIdSection = getClassIdSection();
        if (classIdSection == null) {
            return null;
        }
        if (classIdSection.getCount() != size()) {
            sortItems();
        }
        ClassId classId = (ClassId) classIdSection.get(typeKey);
        if (classId == null || classId.isRemoved()) {
            return null;
        }
        return get(classId.getIdx());
    }

    public Iterator<HiddenApiIndex> getHiddenApis() {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.data.HiddenApiIndexList$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((HiddenApiIndex) obj).hasValidDataOffset();
            }
        });
    }

    @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
    public int countBytes() {
        return size() * 4;
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        super.onPreRefresh();
        clearNullIndex();
        sortItems();
    }

    private void clearNullIndex() {
        removeIf(new Predicate() { // from class: com.reandroid.dex.data.HiddenApiIndexList$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((HiddenApiIndex) obj).isNull();
            }
        });
    }

    private void sortItems() {
        sort(CompareUtil.getComparableComparator());
        if (ensureClassSectionSize()) {
            sort(CompareUtil.getComparableComparator());
        }
    }

    private boolean ensureClassSectionSize() {
        Section<ClassId> classIdSection = getClassIdSection();
        int i = 0;
        if (classIdSection == null) {
            return false;
        }
        boolean z = false;
        while (i < size()) {
            if (get(i).getClassId().getIndex() != i) {
                add(i, new HiddenApiIndex((ClassId) classIdSection.get(i)));
                i--;
                z = true;
            }
            i++;
        }
        setSize(classIdSection.getCount());
        return z;
    }

    @Override // com.reandroid.arsc.base.Block
    protected void onReadBytes(BlockReader blockReader) throws IOException {
        setSize(getClassIdSectionCount());
        super.readChildes(blockReader);
    }

    private int getClassIdSectionCount() {
        Section<ClassId> classIdSection = getClassIdSection();
        if (classIdSection != null) {
            return classIdSection.getCount();
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Section<ClassId> getClassIdSection() {
        SectionList parentSectionList = getParentSectionList();
        if (parentSectionList != null) {
            return parentSectionList.getSection(SectionType.CLASS_ID);
        }
        return null;
    }

    private SectionList getParentSectionList() {
        return (SectionList) getParentInstance(SectionList.class);
    }

    static class HiddenApiIndexCreator implements Creator<HiddenApiIndex> {
        private final HiddenApiIndexList hiddenApiIndexList;

        HiddenApiIndexCreator(HiddenApiIndexList hiddenApiIndexList) {
            this.hiddenApiIndexList = hiddenApiIndexList;
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public HiddenApiIndex[] newArrayInstance(int i) {
            return new HiddenApiIndex[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public HiddenApiIndex newInstance() {
            throw new RuntimeException("Must call newInstanceAt(index)");
        }

        @Override // com.reandroid.arsc.base.Creator
        public HiddenApiIndex newInstanceAt(int i) {
            return new HiddenApiIndex((ClassId) this.hiddenApiIndexList.getClassIdSection().get(i));
        }
    }
}
