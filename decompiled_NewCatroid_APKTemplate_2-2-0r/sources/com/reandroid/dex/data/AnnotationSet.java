package com.reandroid.dex.data;

import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.dex.base.UsageMarker;
import com.reandroid.dex.common.FullRefresh;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.key.TypeKey;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.smali.SmaliFormat;
import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliAnnotationItem;
import com.reandroid.dex.smali.model.SmaliAnnotationSet;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.utils.collection.CollectionUtil;
import com.reandroid.utils.collection.FilterIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class AnnotationSet extends IntegerDataItemList<AnnotationItem> implements ModifiableKeyItem, SmaliFormat, PositionAlignedItem, FullRefresh {
    private final DataKey<AnnotationSet> mKey;

    public AnnotationSet() {
        super(SectionType.ANNOTATION_ITEM, UsageMarker.USAGE_ANNOTATION, new DexPositionAlign());
        this.mKey = new DataKey<>(this);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<AnnotationSet> getKey() {
        return this.mKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((AnnotationSet) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<AnnotationSet> getSectionType() {
        return SectionType.ANNOTATION_SET;
    }

    public DexValueBlock<?> getValue(TypeKey typeKey, String str) {
        AnnotationElement element = getElement(typeKey, str);
        if (element != null) {
            return element.getValue();
        }
        return null;
    }

    public AnnotationElement getElement(TypeKey typeKey, String str) {
        AnnotationItem annotationItem = get(typeKey);
        if (annotationItem != null) {
            return annotationItem.getElement(str);
        }
        return null;
    }

    public AnnotationItem get(TypeKey typeKey) {
        for (AnnotationItem annotationItem : this) {
            if (typeKey.equals(annotationItem.getTypeKey())) {
                return annotationItem;
            }
        }
        return null;
    }

    public Iterator<AnnotationItem> getAll(final TypeKey typeKey) {
        return FilterIterator.of(iterator(), new Predicate() { // from class: com.reandroid.dex.data.AnnotationSet$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return typeKey.equals(((AnnotationItem) obj).getTypeKey());
            }
        });
    }

    public boolean contains(String str) {
        Iterator<AnnotationItem> it = iterator();
        while (it.hasNext()) {
            if (str.equals(it.next().getTypeName())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(TypeKey typeKey) {
        Iterator<AnnotationItem> it = iterator();
        while (it.hasNext()) {
            if (typeKey.equals(it.next().getTypeKey())) {
                return true;
            }
        }
        return false;
    }

    public AnnotationItem getOrCreate(TypeKey typeKey) {
        AnnotationItem annotationItem = get(typeKey);
        return annotationItem != null ? annotationItem : addNewItem(typeKey);
    }

    public AnnotationItem getOrCreate(TypeKey typeKey, String str) {
        AnnotationItem annotationItem = get(typeKey, str);
        return annotationItem != null ? annotationItem : addNew(typeKey, str);
    }

    public AnnotationItem addNewItem(TypeKey typeKey) {
        AnnotationItem annotationItemAddNew = addNew();
        annotationItemAddNew.setType(typeKey);
        return annotationItemAddNew;
    }

    public AnnotationItem addNew(TypeKey typeKey, String str) {
        AnnotationItem annotationItemAddNew = addNew();
        annotationItemAddNew.setType(typeKey);
        annotationItemAddNew.getOrCreateElement(str);
        return annotationItemAddNew;
    }

    public AnnotationItem get(TypeKey typeKey, String str) {
        for (AnnotationItem annotationItem : this) {
            if (typeKey.equals(annotationItem.getTypeKey()) && annotationItem.containsName(str)) {
                return annotationItem;
            }
        }
        return null;
    }

    public void replaceKeys(Key key, Key key2) {
        Iterator<AnnotationItem> it = iterator();
        while (it.hasNext()) {
            it.next().replaceKeys(key, key2);
        }
    }

    @Override // com.reandroid.dex.common.FullRefresh
    public void refreshFull() {
        sort();
    }

    public boolean sort() {
        return super.sort(CollectionUtil.getComparator());
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return new IterableIterator<AnnotationItem, IdItem>(iterator()) { // from class: com.reandroid.dex.data.AnnotationSet.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(AnnotationItem annotationItem) {
                return annotationItem.usedIds();
            }
        };
    }

    public void merge(AnnotationSet annotationSet) {
        if (annotationSet == this) {
            return;
        }
        Iterator<AnnotationItem> it = annotationSet.iterator();
        while (it.hasNext()) {
            addNew(it.next().getKey());
        }
    }

    public void fromSmali(SmaliAnnotationSet smaliAnnotationSet) {
        for (SmaliAnnotationItem smaliAnnotationItem : smaliAnnotationSet) {
            addNewItem(smaliAnnotationItem.getType()).fromSmali(smaliAnnotationItem);
        }
    }

    @Override // com.reandroid.dex.smali.SmaliFormat
    public void append(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.appendAllWithDoubleNewLine(iterator());
    }

    @Override // com.reandroid.dex.data.IntegerList
    public String toString() {
        if (getOffsetReference() == null) {
            return super.toString();
        }
        if (size() == 0) {
            return "EMPTY";
        }
        StringBuilder sb = new StringBuilder();
        boolean z = false;
        for (AnnotationItem annotationItem : this) {
            if (z) {
                sb.append(',');
            }
            sb.append(annotationItem);
            z = true;
        }
        return sb.toString();
    }

    public static class EmptyAnnotationSet extends AnnotationSet {
        @Override // com.reandroid.dex.data.AnnotationSet, com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
        public /* bridge */ /* synthetic */ Key getKey() {
            return super.getKey();
        }

        public EmptyAnnotationSet() {
            addUsageType(UsageMarker.USAGE_ANNOTATION);
        }

        @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer
        protected void onRefreshed() {
            super.onRefreshed();
            if (getSection(getSectionType()).getCount() == 1) {
                addUsageType(UsageMarker.USAGE_ANNOTATION);
            }
        }

        @Override // com.reandroid.dex.common.SectionItem, com.reandroid.dex.base.UsageMarker
        public void clearUsageType() {
            if (getSection(getSectionType()).getCount() != 1) {
                super.clearUsageType();
            }
        }
    }
}
