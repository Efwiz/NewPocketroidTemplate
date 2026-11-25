package com.reandroid.dex.pool;

import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.KeyItem;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.sections.Section;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.CompareUtil;
import com.reandroid.utils.collection.ArrayCollection;
import com.reandroid.utils.collection.MultiMap;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes11.dex */
public class DexSectionPool<T extends SectionItem> extends MultiMap<Key, T> {
    private boolean keyItems;
    private boolean keyItemsChecked;
    private boolean keyItemsCreate;
    private final Section<T> section;

    public DexSectionPool(Section<T> section) {
        this.section = section;
    }

    public T getOrCreate(Key key) {
        if (key == null || !isKeyItemsCreate()) {
            return null;
        }
        T t = get(key);
        if (t != null) {
            return t;
        }
        T t2 = (T) createNext(key);
        put(key, t2);
        return t2;
    }

    public SectionType<T> getSectionType() {
        return getSection().getSectionType();
    }

    public void remove(T t) {
        if (t != null) {
            super.remove(t.getKey(), t);
        }
    }

    public boolean contains(Key key) {
        return super.containsKey(key);
    }

    public void load() {
        if (isKeyItems()) {
            putAll(new Function() { // from class: com.reandroid.dex.pool.DexSectionPool$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((SectionItem) obj).getKey();
                }
            }, getSection().iterator());
        }
    }

    T createNext(Key key) {
        T t = (T) getSection().createItem();
        ((ModifiableKeyItem) t).setKey(key);
        return t;
    }

    Section<T> getSection() {
        return this.section;
    }

    public int clearDuplicates() {
        if (size() == 0 || size() == getSection().getCount()) {
            return 0;
        }
        final ArrayCollection arrayCollection = new ArrayCollection();
        findDuplicates(new Comparator() { // from class: com.reandroid.dex.pool.DexSectionPool$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DexSectionPool.lambda$clearDuplicates$0((SectionItem) obj, (SectionItem) obj2);
            }
        }, new Consumer() { // from class: com.reandroid.dex.pool.DexSectionPool$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                DexSectionPool.lambda$clearDuplicates$1(arrayCollection, (List) obj);
            }
        });
        getSection().getItemArray().removeAll(arrayCollection);
        return arrayCollection.size();
    }

    static /* synthetic */ int lambda$clearDuplicates$0(SectionItem sectionItem, SectionItem sectionItem2) {
        int iCompare = CompareUtil.compare(sectionItem.isRemoved(), sectionItem2.isRemoved());
        return (iCompare != 0 || sectionItem.isRemoved()) ? iCompare : CompareUtil.compare(sectionItem.getKey(), sectionItem2.getKey());
    }

    static /* synthetic */ void lambda$clearDuplicates$1(ArrayCollection arrayCollection, List list) {
        SectionItem sectionItem = (SectionItem) list.get(0);
        for (int i = 1; i < list.size(); i++) {
            SectionItem sectionItem2 = (SectionItem) list.get(i);
            sectionItem2.setReplace(sectionItem);
            arrayCollection.add(sectionItem2);
        }
    }

    boolean isKeyItemsCreate() {
        isKeyItems();
        return this.keyItemsCreate;
    }

    private boolean isKeyItems() {
        if (this.keyItemsChecked) {
            return this.keyItems;
        }
        if (getSectionType().isIdSection()) {
            this.keyItemsChecked = true;
            this.keyItems = true;
            this.keyItemsCreate = true;
            return true;
        }
        SectionItem sectionItem = (SectionItem) getSectionType().getCreator().newInstance();
        this.keyItemsChecked = true;
        boolean z = sectionItem instanceof KeyItem;
        this.keyItems = z;
        this.keyItemsCreate = sectionItem instanceof ModifiableKeyItem;
        return z;
    }

    @Override // com.reandroid.utils.collection.MultiMap
    public String toString() {
        return getSectionType().getName() + "-Pool = " + size();
    }
}
