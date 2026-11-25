package com.reandroid.dex.sections;

import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.pool.DexSectionPool;

/* JADX INFO: loaded from: classes11.dex */
public class DataSection<T extends DataItem> extends Section<T> {
    public DataSection(IntegerPair integerPair, SectionType<T> sectionType) {
        super(sectionType, new DataSectionArray(integerPair, sectionType.getCreator()));
    }

    DataSection(SectionType<T> sectionType, DataSectionArray<T> dataSectionArray) {
        super(sectionType, dataSectionArray);
    }

    @Override // com.reandroid.dex.sections.Section
    int clearUnused() {
        return super.clearUnused();
    }

    @Override // com.reandroid.dex.sections.Section
    public T getSectionItem(int i) {
        return (T) getItemArray().getAt(i);
    }

    @Override // com.reandroid.dex.sections.Section
    public T[] getSectionItems(int[] iArr) {
        return (T[]) getItemArray().getAt(iArr);
    }

    @Override // com.reandroid.dex.sections.Section
    public T createItem() {
        int iEstimateLastOffset = estimateLastOffset();
        T tCreateNext = getItemArray().createNext();
        tCreateNext.setPosition(iEstimateLastOffset);
        return tCreateNext;
    }

    @Override // com.reandroid.dex.sections.Section
    public DexSectionPool<T> getPool() {
        return super.getPool();
    }

    @Override // com.reandroid.dex.sections.Section
    DexSectionPool<T> createPool() {
        return new DexSectionPool<>(this);
    }

    @Override // com.reandroid.dex.sections.Section
    public DataSectionArray<T> getItemArray() {
        return (DataSectionArray) super.getItemArray();
    }

    private int estimateLastOffset() {
        T last = getItemArray().getLast();
        if (last != null) {
            return last.getOffsetReference().get() + last.countBytes();
        }
        int iCountBytes = countBytes() + getOffset();
        return iCountBytes == 0 ? estimateMainOffset() : iCountBytes;
    }

    private int estimateMainOffset() {
        int offset = getOffset();
        if (offset != 0) {
            return offset;
        }
        Section<?> nextSection = getNextSection();
        if (nextSection == null && (nextSection = getPreviousSection()) != null) {
            offset = nextSection.countBytes();
        }
        if (nextSection != null) {
            offset += nextSection.getOffset();
        }
        if (offset == 0) {
            offset = 1;
        }
        getOffsetReference().set(offset);
        return offset;
    }

    @Override // com.reandroid.dex.sections.Section
    void onRefreshed(int i) {
        updateItemOffsets(i);
    }

    @Override // com.reandroid.dex.sections.Section
    void alignSection(DexPositionAlign dexPositionAlign, int i) {
        if (isPositionAlignedItem()) {
            dexPositionAlign.setAlignment(4);
            dexPositionAlign.align(i);
        }
    }

    private boolean isPositionAlignedItem() {
        return getItemArray().get(0) instanceof PositionAlignedItem;
    }

    private void updateItemOffsets(int i) {
        updateNextSection(getItemArray().updatePositionedItemOffsets(i));
    }
}
