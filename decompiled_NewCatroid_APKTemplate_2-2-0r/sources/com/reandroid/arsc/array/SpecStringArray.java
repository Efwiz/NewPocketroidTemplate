package com.reandroid.arsc.array;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.SpecString;

/* JADX INFO: loaded from: classes10.dex */
public class SpecStringArray extends StringArray<SpecString> {
    public SpecStringArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        super(offsetArray, integerItem, integerItem2, z);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public SpecString newInstance() {
        return new SpecString(isUtf8());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public SpecString[] newArrayInstance(int i) {
        return new SpecString[i];
    }
}
