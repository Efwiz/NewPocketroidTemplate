package com.reandroid.arsc.array;

import com.reandroid.arsc.value.ResValueMap;

/* JADX INFO: loaded from: classes10.dex */
public class ResValueMapArray extends CompoundItemArray<ResValueMap> {
    private static final ResValueMap[] empty_elements = new ResValueMap[0];

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public ResValueMap newInstance() {
        return new ResValueMap();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public ResValueMap[] newArrayInstance(int i) {
        if (i == 0) {
            return empty_elements;
        }
        return new ResValueMap[i];
    }
}
