package com.reandroid.dex.base;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.Creator;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.dex.header.DexHeader;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class DexItemArray<T extends Block> extends CreatorArray<T> implements OffsetSupplier, DexArraySupplier<T> {
    private final IntegerPair countAndOffset;

    public DexItemArray(IntegerPair integerPair, Creator<T> creator) {
        super(creator);
        this.countAndOffset = integerPair;
    }

    @Override // com.reandroid.arsc.container.BlockList, com.reandroid.arsc.base.Block
    public int countBytes() {
        Block block = get(0);
        if (block instanceof FixedSizeBlock) {
            return block.countBytes() * getCount();
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
        IntegerPair countAndOffset = getCountAndOffset();
        if (skipReading(countAndOffset, blockReader)) {
            return;
        }
        positionItem(this, blockReader);
        setSize(countAndOffset.getFirst().get());
        readChildes(blockReader);
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
        int size = size();
        for (int i = 0; i < size; i++) {
            Block block = get(i);
            if (block != null && !skipReading(block, blockReader)) {
                positionItem(block, blockReader);
                block.readBytes(blockReader);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean skipReading(Block block, BlockReader blockReader) {
        IntegerReference offsetReference;
        if ((block instanceof OffsetSupplier) && (offsetReference = ((OffsetSupplier) block).getOffsetReference()) != null) {
            int i = offsetReference.get();
            if (!isValidOffset(i)) {
                return true;
            }
            blockReader.seek(i);
        }
        return false;
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

    @Override // com.reandroid.dex.base.CreatorArray, com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
        getCountAndOffset().getFirst().set(size());
    }
}
