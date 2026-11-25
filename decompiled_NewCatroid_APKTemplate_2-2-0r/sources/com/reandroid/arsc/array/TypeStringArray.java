package com.reandroid.arsc.array;

import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.TypeString;

/* JADX INFO: loaded from: classes10.dex */
public class TypeStringArray extends StringArray<TypeString> {
    private int lastCreateIndex;

    public TypeStringArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        super(offsetArray, integerItem, integerItem2, z);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public TypeString newInstance() {
        TypeString typeString = new TypeString(isUtf8());
        this.lastCreateIndex++;
        typeString.set("type-" + this.lastCreateIndex, false);
        return typeString;
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public TypeString[] newArrayInstance(int i) {
        return new TypeString[i];
    }
}
