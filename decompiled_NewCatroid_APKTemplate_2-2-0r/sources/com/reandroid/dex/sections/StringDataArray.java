package com.reandroid.dex.sections;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.data.StringData;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class StringDataArray extends DataSectionArray<StringData> {
    public StringDataArray(IntegerPair integerPair, Creator<StringData> creator) {
        super(integerPair, creator);
    }

    @Override // com.reandroid.dex.base.BlockListArray, com.reandroid.arsc.container.BlockList
    public void readChildes(BlockReader blockReader) throws IOException {
        super.readChildes(blockReader);
        getStringIdArray().link(this);
    }

    private StringIdArray getStringIdArray() {
        StringIdSection stringIdSection;
        SectionList sectionList = (SectionList) getParent(SectionList.class);
        if (sectionList == null || (stringIdSection = (StringIdSection) sectionList.getSection(SectionType.STRING_ID)) == null) {
            return null;
        }
        return stringIdSection.getItemArray();
    }
}
