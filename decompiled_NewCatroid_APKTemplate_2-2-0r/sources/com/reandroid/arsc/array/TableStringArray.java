package com.reandroid.arsc.array;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.TableString;

/* JADX INFO: loaded from: classes10.dex */
public class TableStringArray extends StringArray<TableString> {
    public TableStringArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        super(offsetArray, integerItem, integerItem2, z);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public TableString newInstance() {
        return new TableString(isUtf8());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public TableString[] newArrayInstance(int i) {
        return new TableString[i];
    }
}
