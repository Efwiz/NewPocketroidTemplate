package com.reandroid.dex.sections;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.base.ParallelIntegerPair;
import com.reandroid.dex.base.ParallelReference;
import com.reandroid.dex.sections.SpecialItem;

/* JADX INFO: loaded from: classes11.dex */
public class SpecialSection<T extends SpecialItem> extends Section<T> {
    @Override // com.reandroid.dex.sections.Section
    int clearUnused() {
        return 0;
    }

    @Override // com.reandroid.dex.sections.Section
    void clearUsageTypes() {
    }

    public SpecialSection(IntegerReference integerReference, SectionType<T> sectionType) {
        this(IntegerPair.of(new NumberIntegerReference(), new ParallelReference(integerReference)), sectionType);
    }

    public SpecialSection(IntegerPair integerPair, SectionType<T> sectionType) {
        super(sectionType, new SpecialSectionArray(new ParallelIntegerPair(integerPair), sectionType));
    }

    @Override // com.reandroid.dex.sections.Section
    void onRefreshed(int i) {
        updateItemOffsets(i);
    }

    private void updateItemOffsets(int i) {
        updateNextSection(getItemArray().updatePositionedItemOffsets(i));
    }

    @Override // com.reandroid.dex.sections.Section
    void alignSection(DexPositionAlign dexPositionAlign, int i) {
        dexPositionAlign.setAlignment(4);
        dexPositionAlign.align(i);
    }

    public ParallelIntegerPair getCountAndOffset() {
        return getItemArray().getCountAndOffset();
    }

    @Override // com.reandroid.dex.sections.Section
    public SpecialSectionArray<T> getItemArray() {
        return (SpecialSectionArray) super.getItemArray();
    }
}
