package com.reandroid.arsc.array;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.AlignItem;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.StringItem;
import com.reandroid.arsc.item.StyleItem;
import com.reandroid.arsc.pool.StringPool;
import com.reandroid.json.JSONArray;
import com.reandroid.json.JSONConvert;
import com.reandroid.utils.CompareUtil;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class StyleArray extends OffsetBlockArray<StyleItem> implements Iterable<StyleItem>, JSONConvert<JSONArray> {
    private static final byte END_BYTE = -1;

    @Override // com.reandroid.json.JSONConvert
    public void fromJson(JSONArray jSONArray) {
    }

    @Override // com.reandroid.json.JSONConvert
    public JSONArray toJson() {
        return null;
    }

    public StyleArray(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2) {
        super(offsetArray, integerItem, integerItem2);
    }

    @Override // com.reandroid.arsc.array.OffsetBlockArray, com.reandroid.arsc.base.BlockArray
    public void clear() {
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            StyleItem styleItem = (StyleItem) it.next();
            if (!styleItem.isNull()) {
                styleItem.onRemoved();
            }
        }
        super.clear();
    }

    @Override // com.reandroid.arsc.array.OffsetBlockArray
    void refreshAlignment(BlockReader blockReader, AlignItem alignItem) throws IOException {
        alignItem.clear();
        alignItem.setFill((byte) -1);
        if (blockReader.available() < 4) {
            return;
        }
        IntegerItem integerItem = new IntegerItem();
        while (blockReader.available() >= 4) {
            int position = blockReader.getPosition();
            integerItem.readBytes(blockReader);
            if (integerItem.get() != -1) {
                blockReader.seek(position);
                return;
            }
            alignItem.setSize(alignItem.size() + 4);
        }
    }

    @Override // com.reandroid.arsc.array.OffsetBlockArray
    void refreshAlignment(AlignItem alignItem) {
        if (size() == 0) {
            alignItem.clear();
        } else {
            alignItem.setFill((byte) -1);
            alignItem.ensureSize(8);
        }
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public StyleItem newInstance() {
        return new StyleItem();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public StyleItem[] newArrayInstance(int i) {
        return new StyleItem[i];
    }

    public boolean sort() {
        return sort(CompareUtil.getComparableComparator());
    }

    @Override // com.reandroid.arsc.container.BlockList
    public boolean sort(Comparator<? super StyleItem> comparator) {
        boolean zSort = super.sort(comparator);
        adjustIndexes();
        trimLastItems();
        return zSort;
    }

    private void adjustIndexes() {
        int index;
        Iterator<T> itClonedIterator = clonedIterator();
        boolean z = false;
        while (itClonedIterator.hasNext()) {
            StyleItem styleItem = (StyleItem) itClonedIterator.next();
            StringItem stringItemInternal = styleItem.getStringItemInternal();
            if (stringItemInternal != null && (index = stringItemInternal.getIndex()) != styleItem.getIndex()) {
                moveTo(styleItem, index);
                z = true;
            }
        }
        if (z) {
            ((StringPool) getParentInstance(StringPool.class)).linkStylesInternal();
        }
    }

    private void trimLastItems() {
        trimLastIf(new Predicate() { // from class: com.reandroid.arsc.array.StyleArray$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((StyleItem) obj).isEmpty();
            }
        });
    }
}
