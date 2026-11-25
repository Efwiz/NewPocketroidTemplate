package com.reandroid.archive.block;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.ExpandableBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.LongItem;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class LengthPrefixedBlock extends ExpandableBlockContainer {
    private final Block numberBlock;

    protected void onSizeLoaded(int i) {
    }

    public LengthPrefixedBlock(int i, boolean z) {
        Block integerItem;
        super(i + 1);
        if (z) {
            integerItem = new LongItem();
        } else {
            integerItem = new IntegerItem();
        }
        this.numberBlock = integerItem;
        addChild(integerItem);
    }

    public long getDataSize() {
        Block block = this.numberBlock;
        if (block instanceof LongItem) {
            return ((LongItem) block).get();
        }
        return ((IntegerItem) block).get();
    }

    public void setDataSize(long j) {
        Block block = this.numberBlock;
        if (block instanceof LongItem) {
            ((LongItem) block).set(j);
        } else {
            ((IntegerItem) block).set((int) j);
        }
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        if (blockReader.isAvailable()) {
            Block block = this.numberBlock;
            block.readBytes(blockReader);
            int dataSize = (int) getDataSize();
            if (dataSize <= 0) {
                onSizeLoaded(0);
                return;
            }
            onSizeLoaded(dataSize);
            BlockReader blockReaderCreate = blockReader.create(dataSize);
            for (Block block2 : getChildes()) {
                if (block2 != block) {
                    block2.readBytes(blockReaderCreate);
                }
            }
            blockReader.offset(dataSize);
        }
    }

    @Override // com.reandroid.arsc.container.ExpandableBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        setDataSize(countBytes() - this.numberBlock.countBytes());
    }

    public String toString() {
        return "size=" + this.numberBlock;
    }
}
