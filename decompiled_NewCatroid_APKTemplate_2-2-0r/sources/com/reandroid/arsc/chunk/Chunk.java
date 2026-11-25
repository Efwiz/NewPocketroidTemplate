package com.reandroid.arsc.chunk;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.container.ExpandableBlockContainer;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.header.InfoHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.AlignItem;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Chunk<T extends HeaderBlock> extends ExpandableBlockContainer {
    private AlignItem alignItem;
    protected final SingleBlockContainer<Block> firstPlaceHolder;
    private final T mHeaderBlock;

    public void onChunkLoaded() {
    }

    protected abstract void onChunkRefreshed();

    protected Chunk(T t, int i) {
        super(i + 3);
        this.mHeaderBlock = t;
        SingleBlockContainer<Block> singleBlockContainer = new SingleBlockContainer<>();
        this.firstPlaceHolder = singleBlockContainer;
        addChild(t);
        addChild(singleBlockContainer);
    }

    public AlignItem getAlignItem() {
        AlignItem alignItem = this.alignItem;
        if (alignItem != null) {
            return alignItem;
        }
        AlignItem alignItem2 = new AlignItem();
        addChild(alignItem2);
        this.alignItem = alignItem2;
        return alignItem2;
    }

    public SingleBlockContainer<Block> getFirstPlaceHolder() {
        return this.firstPlaceHolder;
    }

    void setHeaderLoaded(HeaderBlock.HeaderLoaded headerLoaded) {
        getHeaderBlock().setHeaderLoaded(headerLoaded);
    }

    public final T getHeaderBlock() {
        return this.mHeaderBlock;
    }

    @Override // com.reandroid.arsc.container.ExpandableBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected final void onRefreshed() {
        updateAlign();
        getHeaderBlock().refreshHeader();
        onChunkRefreshed();
    }

    private void updateAlign() {
        AlignItem alignItem = getAlignItem();
        alignItem.setSize(0);
        alignItem.align(this);
    }

    @Override // com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        InfoHeader headerBlock = blockReader.readHeaderBlock();
        checkInvalidChunk(headerBlock);
        BlockReader blockReaderCreate = blockReader.create(headerBlock.getChunkSize());
        onReadChildes(blockReaderCreate);
        blockReader.offset(headerBlock.getChunkSize());
        blockReaderCreate.close();
        onChunkLoaded();
    }

    protected void onReadChildes(BlockReader blockReader) throws IOException {
        AlignItem alignItem = getAlignItem();
        alignItem.setSize(0);
        super.onReadBytes(blockReader);
        alignItem.alignSafe(blockReader);
    }

    void checkInvalidChunk(HeaderBlock headerBlock) throws IOException {
        ChunkType chunkType = headerBlock.getChunkType();
        if (chunkType == null || chunkType == ChunkType.NULL) {
            throw new IOException("Invalid chunk: " + headerBlock);
        }
    }

    public String toString() {
        return getClass().getSimpleName() + ": " + getHeaderBlock();
    }
}
