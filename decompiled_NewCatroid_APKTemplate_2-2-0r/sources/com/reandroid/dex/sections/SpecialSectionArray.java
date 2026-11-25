package com.reandroid.dex.sections;

import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.base.ParallelIntegerPair;
import com.reandroid.dex.base.ParallelReference;
import com.reandroid.dex.sections.SpecialItem;

/* JADX INFO: loaded from: classes11.dex */
public class SpecialSectionArray<T extends SpecialItem> extends SectionArray<T> {
    public SpecialSectionArray(IntegerReference integerReference, SectionType<T> sectionType) {
        this(IntegerPair.of(new NumberIntegerReference(), new ParallelReference(integerReference)), sectionType);
    }

    public SpecialSectionArray(IntegerPair integerPair, SectionType<T> sectionType) {
        super(new ParallelIntegerPair(integerPair), sectionType.getCreator());
    }

    @Override // com.reandroid.arsc.container.BlockList
    public boolean add(T t) {
        boolean zAdd = super.add(t);
        updateCount();
        return zAdd;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void add(int i, T t) {
        super.add(i, t);
        updateCount();
    }

    @Override // com.reandroid.dex.base.BlockListArray
    public ParallelIntegerPair getCountAndOffset() {
        return (ParallelIntegerPair) super.getCountAndOffset();
    }
}
