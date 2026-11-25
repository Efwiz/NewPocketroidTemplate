package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.id.StringId;

/* JADX INFO: loaded from: classes11.dex */
public class StringIdArray extends IdSectionArray<StringId> {
    public StringIdArray(IntegerPair integerPair, Creator<StringId> creator) {
        super(integerPair, creator);
    }

    public void link(StringDataArray stringDataArray) {
        int size = size();
        for (int i = 0; i < size; i++) {
            StringId stringId = (StringId) get(i);
            stringId.linkStringData(stringDataArray.getAt(stringId.get(), stringId.getIndex()));
        }
    }
}
