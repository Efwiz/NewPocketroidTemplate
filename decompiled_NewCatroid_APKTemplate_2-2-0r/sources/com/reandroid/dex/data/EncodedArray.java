package com.reandroid.dex.data;

import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.base.Ule128Item;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.key.DataKey;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.key.ModifiableKeyItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.dex.value.DexValueBlock;
import com.reandroid.dex.value.DexValueType;
import com.reandroid.dex.value.NullValue;
import com.reandroid.dex.value.SectionValue;
import com.reandroid.utils.ObjectsUtil;
import com.reandroid.utils.collection.InstanceIterator;
import com.reandroid.utils.collection.IterableIterator;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes11.dex */
public class EncodedArray extends DataItem implements ModifiableKeyItem, Iterable<DexValueBlock<?>> {
    private static final Creator<DexValueBlock<?>> CREATOR = new Creator<DexValueBlock<?>>() { // from class: com.reandroid.dex.data.EncodedArray.2
        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
        public DexValueBlock<?>[] newArrayInstance(int i) {
            if (i == 0) {
                return EncodedArray.EMPTY;
            }
            return new DexValueBlock[i];
        }

        @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
        public DexValueBlock<?> newInstance() {
            return NullValue.PLACE_HOLDER;
        }
    };
    static final DexValueBlock<?>[] EMPTY = new DexValueBlock[0];
    private final DataKey<EncodedArray> itemKey;
    private final BlockList<DexValueBlock<?>> valueList;
    private final Ule128Item valuesCountReference;

    public EncodedArray() {
        super(2);
        Ule128Item ule128Item = new Ule128Item();
        this.valuesCountReference = ule128Item;
        BlockList<DexValueBlock<?>> blockList = new BlockList<>();
        this.valueList = blockList;
        blockList.setCreator(CREATOR);
        addChild(0, ule128Item);
        addChild(1, blockList);
        this.itemKey = new DataKey<>(this);
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItem, com.reandroid.dex.key.KeyItem
    public DataKey<EncodedArray> getKey() {
        return this.itemKey;
    }

    @Override // com.reandroid.dex.key.ModifiableKeyItem
    public void setKey(Key key) {
        merge((EncodedArray) ((DataKey) key).getItem());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<EncodedArray> getSectionType() {
        return SectionType.ENCODED_ARRAY;
    }

    public DexValueBlock<?> get(int i) {
        return (DexValueBlock) getValueList().get(i);
    }

    public <T1 extends IdItem> SectionValue<T1> getOrCreate(SectionType<T1> sectionType, int i) {
        return (SectionValue) getOrCreate(DexValueType.get(sectionType), i);
    }

    public <T1 extends DexValueBlock<?>> T1 getOrCreate(DexValueType<T1> dexValueType, int i) {
        BlockList<DexValueBlock<?>> valueList = getValueList();
        valueList.ensureSize(i + 1);
        T1 t1 = (T1) valueList.get(i);
        if (t1 != null && t1 != NullValue.PLACE_HOLDER && t1.getValueType() == dexValueType) {
            return t1;
        }
        T1 t2 = (T1) dexValueType.newInstance();
        valueList.set(i, t2);
        return t2;
    }

    public int size() {
        return getValueList().getCount();
    }

    public void add(DexValueBlock<?> dexValueBlock) {
        getValueList().add(dexValueBlock);
    }

    public boolean remove(int i) {
        return getValueList().remove(i) != null;
    }

    public boolean remove(DexValueBlock<?> dexValueBlock) {
        return getValueList().remove(dexValueBlock);
    }

    public boolean removeIf(Predicate<? super DexValueBlock<?>> predicate) {
        return getValueList().removeIf(predicate);
    }

    public void set(int i, DexValueBlock<?> dexValueBlock) {
        ensureSize(i + 1);
        getValueList().set(i, dexValueBlock);
    }

    public void clear() {
        getValueList().clearChildes();
    }

    public void removeAll() {
        getValueList().clearTemporarily();
    }

    public boolean sort(Comparator<? super DexValueBlock<?>> comparator) {
        return getValueList().sort(comparator);
    }

    public void trimNull() {
        int size = size();
        int i = 0;
        for (int i2 = 0; i2 < size; i2++) {
            DexValueBlock<?> dexValueBlock = get(i2);
            if (dexValueBlock != null && !dexValueBlock.is(DexValueType.NULL) && !dexValueBlock.isTemporary()) {
                i = i2 + 1;
            }
        }
        if (i != size) {
            setSize(i);
        }
    }

    public void ensureSize(int i) {
        if (i > size()) {
            setSize(i);
        }
    }

    public void setSize(int i) {
        if (i < 0) {
            throw new IndexOutOfBoundsException("Invalid size: " + i);
        }
        this.valuesCountReference.set(i);
        BlockList<DexValueBlock<?>> valueList = getValueList();
        if (i == 0) {
            valueList.clearTemporarily();
            return;
        }
        int size = valueList.size();
        if (i <= size) {
            if (i < size) {
                valueList.setSize(i);
                return;
            }
            return;
        }
        NullValue nullValue = NullValue.PLACE_HOLDER;
        int i2 = i - size;
        valueList.ensureCapacity(i2);
        for (int i3 = 0; i3 < i2; i3++) {
            valueList.add(nullValue);
        }
    }

    @Override // java.lang.Iterable
    public Iterator<DexValueBlock<?>> iterator() {
        return getValueList().iterator();
    }

    public <T1 extends DexValueBlock<?>> Iterator<T1> iterator(Class<T1> cls) {
        return InstanceIterator.of(iterator(), cls);
    }

    public <T1 extends DexValueBlock<?>> Iterator<T1> iterator(Class<T1> cls, Predicate<? super T1> predicate) {
        return InstanceIterator.of(iterator(), cls, predicate);
    }

    public Iterator<DexValueBlock<?>> clonedIterator() {
        return getValueList().clonedIterator();
    }

    private BlockList<DexValueBlock<?>> getValueList() {
        return this.valueList;
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.valuesCountReference.onReadBytes(blockReader);
        BlockList<DexValueBlock<?>> valueList = getValueList();
        int i = this.valuesCountReference.get();
        valueList.ensureCapacity(i);
        for (int i2 = 0; i2 < i; i2++) {
            DexValueBlock<?> dexValueBlockCreate = DexValueType.create(blockReader);
            valueList.add(dexValueBlockCreate);
            dexValueBlockCreate.onReadBytes(blockReader);
        }
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer
    protected void onRefreshed() {
        super.onRefreshed();
        this.valuesCountReference.set(size());
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.IdUsageIterator
    public Iterator<IdItem> usedIds() {
        return new IterableIterator<DexValueBlock<?>, IdItem>(iterator()) { // from class: com.reandroid.dex.data.EncodedArray.1
            @Override // com.reandroid.utils.collection.IterableIterator
            public Iterator<IdItem> iterator(DexValueBlock<?> dexValueBlock) {
                return dexValueBlock.usedIds();
            }
        };
    }

    @Override // com.reandroid.dex.data.DataItem
    public void copyFrom(DataItem dataItem) {
        merge((EncodedArray) dataItem);
    }

    public void merge(EncodedArray encodedArray) {
        int size = encodedArray.size();
        getValueList().ensureCapacity(size);
        for (int i = 0; i < size; i++) {
            DexValueBlock<?> dexValueBlock = encodedArray.get(i);
            getOrCreate(dexValueBlock.getValueType(), i).merge(dexValueBlock);
        }
    }

    public int hashCode() {
        int size = size();
        int iHashCode = 1;
        for (int i = 0; i < size; i++) {
            iHashCode = (iHashCode * 31) + get(i).hashCode();
        }
        return iHashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        EncodedArray encodedArray = (EncodedArray) obj;
        int size = size();
        if (size != encodedArray.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!ObjectsUtil.equals(get(i), encodedArray.get(i))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        Iterator<DexValueBlock<?>> it = iterator();
        boolean z = false;
        while (it.hasNext()) {
            if (z) {
                sb.append(", ");
            }
            sb.append(it.next());
            z = true;
        }
        sb.append(']');
        return sb.toString();
    }
}
