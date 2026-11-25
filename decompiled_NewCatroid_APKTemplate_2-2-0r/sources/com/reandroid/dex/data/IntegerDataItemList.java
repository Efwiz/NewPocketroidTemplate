package com.reandroid.dex.data;

import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.data.DataItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.collection.ArrayIterator;
import com.reandroid.utils.collection.ArraySort;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class IntegerDataItemList<T extends DataItem> extends IntegerList implements Iterable<T> {
    private T[] items;
    private final SectionType<T> sectionType;
    private final int usageType;

    static /* synthetic */ boolean lambda$remove$0(DataItem dataItem, DataItem dataItem2) {
        return dataItem2 == dataItem;
    }

    public IntegerDataItemList(SectionType<T> sectionType, int i, DexPositionAlign dexPositionAlign) {
        super(dexPositionAlign);
        this.sectionType = sectionType;
        this.usageType = i;
    }

    public T addNew(Key key) {
        T t = (T) getOrCreateSection(this.sectionType).getOrCreate(key);
        add(t.getIdx());
        return t;
    }

    public T addNew() {
        T t = (T) getOrCreateSection(this.sectionType).createItem();
        add(t.getIdx());
        return t;
    }

    public T getOrCreateAt(int i) {
        T t = (T) getItem(i);
        if (t != null) {
            return t;
        }
        ensureSize(i + 1);
        T t2 = (T) getOrCreateSection(this.sectionType).createItem();
        put(i, t2.getIdx());
        onChanged();
        return t2;
    }

    public void addNull() {
        add(0);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem
    public void removeSelf() {
        setItems(null);
        super.removeSelf();
    }

    public void remove(final T t) {
        removeIf(new Predicate() { // from class: com.reandroid.dex.data.IntegerDataItemList$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return IntegerDataItemList.lambda$remove$0(t, (DataItem) obj);
            }
        });
    }

    public void removeIf(Predicate<? super T> predicate) {
        T[] tArr = this.items;
        if (tArr == null) {
            return;
        }
        int length = tArr.length;
        boolean z = false;
        for (int i = 0; i < length; i++) {
            if (predicate.test(tArr[i])) {
                tArr[i] = null;
                z = true;
            }
        }
        if (z) {
            removeNulls();
        }
    }

    void removeNulls() {
        T[] tArr = this.items;
        if (tArr == null || tArr.length == 0) {
            setItems(null);
            return;
        }
        int length = tArr.length;
        int i = 0;
        for (T t : tArr) {
            if (t == null) {
                i++;
            }
        }
        if (i == 0) {
            return;
        }
        DataItem[] dataItemArr = (DataItem[]) this.sectionType.getCreator().newArrayInstance(length - i);
        int i2 = 0;
        for (T t2 : tArr) {
            if (t2 != null) {
                dataItemArr[i2] = t2;
                i2++;
            }
        }
        setItems(dataItemArr);
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return ArrayIterator.of(this.items);
    }

    public T getItem(int i) {
        T[] tArr;
        if (i >= 0 && (tArr = this.items) != null && i < tArr.length) {
            return tArr[i];
        }
        return null;
    }

    public T[] getItems() {
        return this.items;
    }

    public void setItems(T[] tArr) {
        if (tArr == this.items) {
            return;
        }
        if (isEmpty(tArr)) {
            this.items = null;
            setSize(0);
            return;
        }
        int length = tArr.length;
        setSize(length, false);
        for (int i = 0; i < length; i++) {
            T t = tArr[i];
            put(i, getData(t));
            updateUsage(t);
        }
        this.items = tArr;
    }

    public boolean isEmpty() {
        return isEmpty(this.items);
    }

    public boolean sort(Comparator<? super T> comparator) {
        T[] tArr = this.items;
        if (tArr == null || tArr.length < 2) {
            return false;
        }
        boolean zSort = ArraySort.sort(tArr, comparator);
        if (zSort) {
            setItems((DataItem[]) tArr.clone());
        }
        return zSort;
    }

    private void updateUsage(T[] tArr) {
        if (tArr == null) {
            return;
        }
        for (T t : tArr) {
            updateUsage(t);
        }
    }

    private void updateUsage(T t) {
        if (t == null) {
            return;
        }
        t.addUsageType(this.usageType);
    }

    @Override // com.reandroid.dex.data.IntegerList
    void onChanged() {
        super.onChanged();
        cacheItems();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer
    protected void onPreRefresh() {
        super.onPreRefresh();
        refreshItems();
    }

    private void refreshItems() {
        DataItem[] dataItemArr = this.items;
        if (isEmpty(dataItemArr)) {
            this.items = null;
            setSize(0);
            return;
        }
        int length = dataItemArr.length;
        setSize(length, false);
        boolean z = false;
        for (int i = 0; i < length; i++) {
            DataItem dataItem = dataItemArr[i];
            if (dataItem != null) {
                dataItem = (DataItem) dataItem.getReplace();
                dataItemArr[i] = dataItem;
            }
            int data = getData(dataItem);
            put(i, getData(dataItem));
            if (data == 0) {
                dataItemArr[i] = null;
                z = true;
            }
            updateUsage(dataItem);
        }
        if (z) {
            removeNulls();
        }
    }

    private int getData(T t) {
        if (t == null) {
            return 0;
        }
        return t.getIdx();
    }

    private void cacheItems() {
        T[] tArr = (T[]) ((DataItem[]) getSectionItem(this.sectionType, toArray()));
        this.items = tArr;
        updateUsage(tArr);
    }

    private boolean isEmpty(T[] tArr) {
        if (tArr != null && tArr.length != 0) {
            for (T t : tArr) {
                if (t != null) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int size = size();
        int iHashCode = 1;
        for (int i = 0; i < size; i++) {
            iHashCode *= 31;
            DataItem item = getItem(i);
            if (item != null) {
                iHashCode += item.hashCode();
            }
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        IntegerDataItemList integerDataItemList = (IntegerDataItemList) obj;
        int size = size();
        if (size != integerDataItemList.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(getItem(i), integerDataItemList.getItem(i))) {
                return false;
            }
        }
        return true;
    }
}
