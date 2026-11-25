package com.reandroid.dex.sections;

import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.id.ClassId;

/* JADX INFO: loaded from: classes11.dex */
public class ClassIdSection extends IdSection<ClassId> {
    public ClassIdSection(IntegerPair integerPair) {
        super(SectionType.CLASS_ID, new ClassIdSectionArray(integerPair));
    }

    @Override // com.reandroid.dex.sections.Section
    public ClassIdSectionArray getItemArray() {
        return (ClassIdSectionArray) super.getItemArray();
    }
}
