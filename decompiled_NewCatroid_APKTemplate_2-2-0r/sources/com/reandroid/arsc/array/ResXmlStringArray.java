package com.reandroid.arsc.array;

import com.reandroid.arsc.chunk.xml.ResXmlDocument;
import com.reandroid.arsc.chunk.xml.ResXmlIDMap;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ResXmlID;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.utils.ObjectsUtil;
import java.util.Comparator;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlStringArray extends StringArray<ResXmlString> {
    public ResXmlStringArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        super(offsetArray, integerItem, integerItem2, z);
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void add(int i, ResXmlString resXmlString) {
        ResXmlIDArray resXmlIDArray = getResXmlIDMap().getResXmlIDArray();
        if (i < resXmlIDArray.size() - 1) {
            resXmlIDArray.add(i, new ResXmlID());
        }
        super.add(i, resXmlString);
    }

    private ResXmlIDMap getResXmlIDMap() {
        ResXmlDocument resXmlDocument = (ResXmlDocument) getParentInstance(ResXmlDocument.class);
        if (resXmlDocument != null) {
            return resXmlDocument.getResXmlIDMap();
        }
        return (ResXmlIDMap) ObjectsUtil.cast(null);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public ResXmlString newInstance() {
        return new ResXmlString(isUtf8());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public ResXmlString[] newArrayInstance(int i) {
        return new ResXmlString[i];
    }

    @Override // com.reandroid.arsc.array.StringArray, com.reandroid.arsc.container.BlockList
    public boolean sort(Comparator<? super ResXmlString> comparator) {
        boolean zSort = super.sort(comparator);
        getResXmlIDMap().getResXmlIDArray().sort();
        return zSort;
    }

    @Override // com.reandroid.arsc.array.StringArray, com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        sort();
        super.onPreRefresh();
    }
}
