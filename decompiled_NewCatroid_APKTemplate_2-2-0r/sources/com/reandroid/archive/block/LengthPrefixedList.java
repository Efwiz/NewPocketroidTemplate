package com.reandroid.archive.block;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCreator;
import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.container.FixedBlockContainer;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.LongItem;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public abstract class LengthPrefixedList<T extends Block> extends FixedBlockContainer implements BlockCreator<T>, Iterable<T> {
    private final SingleBlockContainer<Block> bottomContainer;
    private final BlockList<T> elements;
    private final Block numberBlock;

    public LengthPrefixedList(boolean z) {
        Block integerItem;
        super(3);
        if (z) {
            integerItem = new LongItem();
        } else {
            integerItem = new IntegerItem();
        }
        this.numberBlock = integerItem;
        BlockList<T> blockList = new BlockList<>();
        this.elements = blockList;
        SingleBlockContainer<Block> singleBlockContainer = new SingleBlockContainer<>();
        this.bottomContainer = singleBlockContainer;
        addChild(0, integerItem);
        addChild(1, blockList);
        addChild(2, singleBlockContainer);
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

    public int size() {
        return this.elements.size();
    }

    @Override // java.lang.Iterable
    public Iterator<T> iterator() {
        return this.elements.iterator();
    }

    public List<T> getElements() {
        return this.elements.getChildes();
    }

    public T add(T t) {
        this.elements.add(t);
        return t;
    }

    public boolean remove(T t) {
        return this.elements.remove(t);
    }

    public void sort(Comparator<T> comparator) {
        this.elements.sort(comparator);
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        if (blockReader.isAvailable()) {
            this.numberBlock.readBytes(blockReader);
            int dataSize = (int) getDataSize();
            if (dataSize <= 0) {
                return;
            }
            BlockReader blockReaderCreate = blockReader.create(dataSize);
            readElements(blockReaderCreate);
            this.bottomContainer.readBytes(blockReaderCreate);
            blockReader.offset(dataSize);
        }
    }

    private void readElements(BlockReader blockReader) throws IOException {
        int iCountBytes = this.bottomContainer.countBytes() + 4;
        while (blockReader.available() > iCountBytes) {
            int position = blockReader.getPosition();
            add(newInstance()).readBytes(blockReader);
            if (position == blockReader.getPosition()) {
                return;
            }
        }
    }

    @Override // com.reandroid.arsc.container.FixedBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        setDataSize(countBytes() - this.numberBlock.countBytes());
    }

    public Block getBottomBlock() {
        return this.bottomContainer.getItem();
    }

    public void setBottomBlock(Block block) {
        this.bottomContainer.setItem(block);
    }

    public String toString() {
        return "size=" + this.numberBlock + ", count=" + size();
    }
}
