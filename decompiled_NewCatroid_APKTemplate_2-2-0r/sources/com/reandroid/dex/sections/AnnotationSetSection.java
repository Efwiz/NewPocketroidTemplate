package com.reandroid.dex.sections;

import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.data.AnnotationSet;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationSetSection extends DataSection<AnnotationSet> {
    public AnnotationSetSection(IntegerPair integerPair, SectionType<AnnotationSet> sectionType) {
        super(integerPair, sectionType);
    }

    @Override // com.reandroid.dex.sections.Section, com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        ensureNotEmpty();
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            add(new AnnotationSet.EmptyAnnotationSet());
        }
    }
}
