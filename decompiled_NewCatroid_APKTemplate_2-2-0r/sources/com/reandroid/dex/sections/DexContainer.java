package com.reandroid.dex.sections;

import com.reandroid.arsc.container.BlockList;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.key.Key;
import com.reandroid.utils.collection.ComputeIterator;
import java.util.Iterator;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class DexContainer extends BlockList<DexLayout> implements Iterable<DexLayout> {
    private DexLayout current;

    public DexContainer() {
        DexLayout dexLayout = new DexLayout();
        this.current = dexLayout;
        add(dexLayout);
    }

    public DexLayout getDexLayout() {
        return this.current;
    }

    public <T1 extends SectionItem> Iterator<T1> getSectionItems(final SectionType<T1> sectionType, final Key key) {
        return ComputeIterator.of(getSectionLists(), new Function() { // from class: com.reandroid.dex.sections.DexContainer$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SectionList) obj).getSectionItem(sectionType, key);
            }
        });
    }

    public <T1 extends SectionItem> Iterator<Section<T1>> getSections(final SectionType<T1> sectionType) {
        return ComputeIterator.of(getSectionLists(), new Function() { // from class: com.reandroid.dex.sections.DexContainer$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((SectionList) obj).getSection(sectionType);
            }
        });
    }

    public <T1 extends SectionItem> Section<T1> getSection(SectionType<T1> sectionType) {
        Iterator<SectionList> sectionLists = getSectionLists();
        while (sectionLists.hasNext()) {
            Section<T1> section = sectionLists.next().getSection(sectionType);
            if (section != null) {
                return section;
            }
        }
        return null;
    }

    public Iterator<SectionList> getSectionLists() {
        return ComputeIterator.of(iterator(), new Function() { // from class: com.reandroid.dex.sections.DexContainer$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((DexLayout) obj).getSectionList();
            }
        });
    }
}
