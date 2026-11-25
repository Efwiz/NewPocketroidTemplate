package com.reandroid.dex.sections;

import com.reandroid.dex.base.IntegerPair;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.Key;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class IdSection<T extends IdItem> extends Section<T> {
    public IdSection(IntegerPair integerPair, SectionType<T> sectionType) {
        super(sectionType, new IdSectionArray(integerPair, sectionType.getCreator()));
    }

    IdSection(SectionType<T> sectionType, IdSectionArray<T> idSectionArray) {
        super(sectionType, idSectionArray);
    }

    public int getFreeSpace() {
        return 65535 - getCount();
    }

    @Override // com.reandroid.arsc.base.BlockContainer
    protected void onPreRefresh() {
        super.onPreRefresh();
        sort();
    }

    @Override // com.reandroid.dex.sections.Section
    public boolean remove(Key key) {
        T sectionItem = getSectionItem(key);
        if (sectionItem == null || sectionItem.getParent() == null) {
            return false;
        }
        sectionItem.removeSelf();
        return true;
    }

    @Override // com.reandroid.dex.sections.Section
    public boolean removeWithKeys(final Predicate<? super Key> predicate) {
        return getItemArray().removeIf(new Predicate() { // from class: com.reandroid.dex.sections.IdSection$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return predicate.test(((IdItem) obj).getKey());
            }
        });
    }

    @Override // com.reandroid.dex.sections.Section
    public T getSectionItem(int i) {
        T t = (T) getItemArray().get(i);
        if (i < 0 || t != null) {
            return t;
        }
        throw new NullPointerException("Null id: " + i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.sections.Section
    public T[] getSectionItems(int[] iArr) {
        if (iArr == null || iArr.length == 0) {
            return null;
        }
        SectionArray<T> itemArray = getItemArray();
        int length = iArr.length;
        T[] tArrNewArrayInstance = itemArray.newArrayInstance(iArr.length);
        for (int i = 0; i < length; i++) {
            IdItem idItem = (IdItem) itemArray.get(iArr[i]);
            tArrNewArrayInstance[i] = idItem;
            if (idItem == null) {
                throw new NullPointerException("Null id: " + i);
            }
        }
        return tArrNewArrayInstance;
    }

    @Override // com.reandroid.dex.sections.Section
    public T createItem() {
        return getItemArray().createNext();
    }

    @Override // com.reandroid.dex.sections.Section
    void onRefreshed(int i) {
        updateNextSection(i + getItemArray().countBytes());
    }

    boolean canAddAll(Collection<IdItem> collection, int i) {
        if (i < 0) {
            i = 200;
        }
        int freeSpace = getFreeSpace();
        if (freeSpace <= i) {
            return false;
        }
        if (collection.size() < freeSpace) {
            return true;
        }
        HashSet hashSet = new HashSet();
        SectionType<T> sectionType = getSectionType();
        for (IdItem idItem : collection) {
            if (idItem.getSectionType() == sectionType) {
                Key key = idItem.getKey();
                if (hashSet.contains(key)) {
                    continue;
                } else {
                    hashSet.add(key);
                    if (!contains(key) && (freeSpace = freeSpace - 1) <= i) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
