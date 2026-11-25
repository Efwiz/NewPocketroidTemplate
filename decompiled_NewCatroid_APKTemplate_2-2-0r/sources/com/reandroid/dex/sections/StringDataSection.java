package com.reandroid.dex.sections;

import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.data.StringData;
import com.reandroid.dex.pool.StringDataPool;

/* JADX INFO: loaded from: classes11.dex */
public class StringDataSection extends DataSection<StringData> {
    public StringDataSection(IntegerPair integerPair, SectionType<StringData> sectionType) {
        super(sectionType, new StringDataArray(integerPair, sectionType.getCreator()));
    }

    @Override // com.reandroid.dex.sections.DataSection, com.reandroid.dex.sections.Section
    public StringDataPool getPool() {
        return (StringDataPool) super.getPool();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.reandroid.dex.sections.DataSection, com.reandroid.dex.sections.Section
    public StringDataPool createPool() {
        return new StringDataPool(this);
    }
}
