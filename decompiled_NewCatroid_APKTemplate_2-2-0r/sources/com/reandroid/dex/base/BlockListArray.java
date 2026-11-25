package com.reandroid.dex.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.header.DexHeader;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class BlockListArray<T extends Block> extends BlockArray<T> implements OffsetSupplier, DexArraySupplier<T>, Creator<T> {
    private final IntegerPair countAndOffset;

    public BlockListArray(IntegerPair integerPair, Creator<T> creator) {
        super(creator);
        this.countAndOffset = integerPair;
    }

    @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
    public int countBytes() {
        T first = getFirst();
        if (first instanceof FixedSizeBlock) {
            return first.countBytes() * getCount();
        }
        return super.countBytes();
    }

    @Override // com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
    public IntegerReference getOffsetReference() {
        return getCountAndOffset().getSecond();
    }

    public IntegerPair getCountAndOffset() {
        return this.countAndOffset;
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        if (skipReading(getCountAndOffset(), blockReader)) {
            return;
        }
        readChildes(blockReader);
        trimToSize();
    }

    public void readChild(BlockReader blockReader, T t) throws IOException {
        positionItem(t, blockReader);
        t.readBytes(blockReader);
    }

    private boolean skipReading(IntegerPair integerPair, BlockReader blockReader) {
        IntegerReference second;
        if (integerPair != null && (second = integerPair.getSecond()) != null) {
            int i = second.get();
            if (!isValidOffset(i)) {
                return true;
            }
            blockReader.seek(i);
        }
        return false;
    }

    @Override // com.reandroid.arsc.container.BlockList
    public void readChildes(BlockReader blockReader) throws IOException {
        int i = this.countAndOffset.getFirst().get();
        setSize(i);
        for (int i2 = 0; i2 < i; i2++) {
            readChild(blockReader, get(i2));
        }
        onChanged();
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void positionItem(Block block, BlockReader blockReader) {
        if (block instanceof PositionedItem) {
            ((PositionedItem) block).setPosition(blockReader.getPosition());
        }
    }

    protected boolean isValidOffset(int i) {
        if (i == 0) {
            return isDexHeaderArray();
        }
        return i > 0;
    }

    private boolean isDexHeaderArray() {
        return get(0) instanceof DexHeader;
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        updateCount();
        super.onRefreshed();
    }

    protected void updateCount() {
        getCountAndOffset().getFirst().set(size());
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public T[] newArrayInstance(int i) {
        return (T[]) getCreator().newArrayInstance(i);
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public T newInstance() {
        return (T) getCreator().newInstance();
    }
}
