package com.reandroid.dex.data;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerArrayBlock;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.common.IntegerArray;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.OffsetReceiver;
import com.reandroid.dex.base.PositionedItem;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class IntegerList extends DataItem implements IntegerArray, PositionedItem, OffsetSupplier, OffsetReceiver {
    private final IntegerArray arrayBlock;
    private final IntegerReference itemCount;
    private final DexPositionAlign positionAlign;

    void onChanged() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    IntegerList(int i, IntegerArray integerArray, DexPositionAlign dexPositionAlign) {
        super(i + 3);
        this.positionAlign = dexPositionAlign;
        IntegerItem integerItem = new IntegerItem();
        this.itemCount = integerItem;
        this.arrayBlock = integerArray;
        addChild(0, integerItem);
        addChild(1, (Block) integerArray);
        addChild(2, dexPositionAlign);
    }

    public IntegerList(IntegerReference integerReference) {
        super(1);
        this.itemCount = integerReference;
        IntegerArrayBlock integerArrayBlock = new IntegerArrayBlock();
        this.arrayBlock = integerArrayBlock;
        addChild(0, integerArrayBlock);
        this.positionAlign = null;
    }

    public IntegerList() {
        this(0, new IntegerArrayBlock(), new DexPositionAlign());
    }

    public IntegerList(DexPositionAlign dexPositionAlign) {
        this(0, new IntegerArrayBlock(), dexPositionAlign);
    }

    public DexPositionAlign getPositionAlign() {
        return this.positionAlign;
    }

    public int[] toArray() {
        return IntegerArray.toArray(this.arrayBlock);
    }

    public boolean removeValue(int i) {
        return remove(indexOf(i));
    }

    public boolean remove(int i) {
        int size;
        if (i < 0 || i >= (size = size())) {
            return false;
        }
        int i2 = size - 1;
        while (i < i2) {
            int i3 = i + 1;
            put(i, get(i3));
            i = i3;
        }
        setSize(i2);
        return true;
    }

    public int indexOf(int i) {
        int size = size();
        for (int i2 = 0; i2 < size; i2++) {
            if (i == get(i2)) {
                return i2;
            }
        }
        return -1;
    }

    public boolean addIfAbsent(int i) {
        if (indexOf(i) >= 0) {
            return false;
        }
        add(i);
        return true;
    }

    public void add(int i) {
        int size = size();
        setSize(size + 1, false);
        put(size, i);
        onChanged();
    }

    @Override // com.reandroid.common.IntegerArray
    public void put(int i, int i2) {
        this.arrayBlock.put(i, i2);
    }

    @Override // com.reandroid.common.IntegerArray
    public int get(int i) {
        return this.arrayBlock.get(i);
    }

    @Override // com.reandroid.common.IntegerArray
    public int size() {
        return this.arrayBlock.size();
    }

    @Override // com.reandroid.common.IntegerArray
    public void setSize(int i) {
        setSize(i, true);
    }

    void setSize(int i, boolean z) {
        int size = size();
        this.arrayBlock.setSize(i);
        this.itemCount.set(i);
        if (!z || size == i) {
            return;
        }
        onChanged();
    }

    void ensureSize(int i) {
        if (i > size()) {
            setSize(i, false);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        IntegerReference integerReference = this.itemCount;
        if (integerReference instanceof Block) {
            Block block = (Block) integerReference;
            if (block.getParent() == this) {
                int position = blockReader.getPosition();
                block.readBytes(blockReader);
                blockReader.seek(position);
            }
        }
        this.arrayBlock.setSize(integerReference.get());
        super.onReadBytes(blockReader);
        onChanged();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(": ");
        IntegerReference offsetReference = getOffsetReference();
        if (offsetReference != null) {
            sb.append("offset=");
            sb.append(offsetReference.get());
            sb.append(", ");
        }
        if (this.arrayBlock.size() != this.itemCount.get()) {
            sb.append("count=");
            sb.append(this.itemCount);
            sb.append(", ");
        }
        sb.append(IntegerArray.toString(this));
        return sb.toString();
    }
}
