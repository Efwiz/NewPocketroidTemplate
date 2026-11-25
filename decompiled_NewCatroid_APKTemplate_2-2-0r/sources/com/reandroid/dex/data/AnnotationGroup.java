package com.reandroid.dex.data;

import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.IterableIterator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationGroup extends IntegerDataItemList<AnnotationSet> implements ModifiableKeyItem {
    private final DataKey<AnnotationGroup> mKey;

    @Override // com.reandroid.dex.data.IntegerDataItemList
    void removeNulls() {
    }

    public AnnotationGroup() {
        super(SectionType.ANNOTATION_SET, UsageMarker.USAGE_ANNOTATION, null);
        this.mKey = new DataKey<>(this);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<AnnotationGroup> getKey() {
        return this.mKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((AnnotationGroup) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<AnnotationGroup> getSectionType() {
        return SectionType.ANNOTATION_GROUP;
    }

    public void replaceKeys(Key key, Key key2) {
        Iterator<AnnotationSet> it = iterator();
        while (it.hasNext()) {
            it.next().replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return new IterableIterator<AnnotationSet, IdItem>(iterator()) { // from class: com.reandroid.dex.data.AnnotationGroup.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationSet annotationSet) {
                return annotationSet.usedIds();
            }
        };
    }

    public void merge(AnnotationGroup annotationGroup) {
        AnnotationSet[] items = annotationGroup.getItems();
        if (items == null) {
            return;
        }
        for (AnnotationSet annotationSet : items) {
            if (annotationSet == null) {
                addNull();
            } else {
                addNew(annotationSet.getKey());
            }
        }
    }
}
