package com.reandroid.dex.common;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.arsc.base.OffsetSupplier;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerReference;
import com.reandroid.arsc.item.NumberIntegerReference;
import com.reandroid.dex.base.OffsetReceiver;
import com.reandroid.dex.base.PositionedItem;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes7.dex */
public class SectionItemContainer extends SectionItem implements BlockRefresh, PositionedItem, OffsetSupplier, OffsetReceiver {
    private static final Block[] EMPTY = new Block[0];
    private final Block[] mChildes;
    private IntegerReference mReference;

    protected boolean isValidOffset(int i) {
        return i > 0;
    }

    protected void onPreRefresh() {
    }

    protected void onRefreshed() {
    }

    @Override // com.reandroid.dex.base.PositionedItem
    public void removeLastAlign() {
    }

    public SectionItemContainer(int i) {
        Block[] blockArr;
        super(0);
        if (i == 0) {
            blockArr = EMPTY;
        } else {
            blockArr = new Block[i];
        }
        this.mChildes = blockArr;
    }

    @Override // com.reandroid.dex.common.SectionItem
    public int getIdx() {
        return getOffset();
    }

    @Override // com.reandroid.arsc.base.OffsetSupplier, com.reandroid.dex.base.DexArraySupplier
    public IntegerReference getOffsetReference() {
        return this.mReference;
    }

    @Override // com.reandroid.dex.base.OffsetReceiver
    public void setOffsetReference(IntegerReference integerReference) {
        this.mReference = integerReference;
    }

    @Override // com.reandroid.dex.base.PositionedItem
    public void setPosition(int i) {
        IntegerReference offsetReference = getOffsetReference();
        if (offsetReference == null) {
            setOffsetReference(new NumberIntegerReference(i));
        } else {
            offsetReference.set(i);
        }
    }

    public int getOffset() {
        IntegerReference offsetReference = getOffsetReference();
        if (offsetReference != null) {
            return offsetReference.get();
        }
        return 0;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public int countBytes() {
        Block[] childes;
        if (isNull() || (childes = getChildes()) == null) {
            return 0;
        }
        int iCountBytes = 0;
        for (Block block : childes) {
            if (block != null) {
                iCountBytes += block.countBytes();
            }
        }
        return iCountBytes;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        Block[] childes;
        byte[] bArrAddBytes = null;
        if (isNull() || (childes = getChildes()) == null) {
            return null;
        }
        for (Block block : childes) {
            if (block != null) {
                bArrAddBytes = addBytes(bArrAddBytes, block.getBytes());
            }
        }
        return bArrAddBytes;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public int onWriteBytes(OutputStream outputStream) throws IOException {
        Block[] childes;
        if (isNull() || (childes = getChildes()) == null) {
            return 0;
        }
        int iWriteBytes = 0;
        for (Block block : childes) {
            if (block != null) {
                iWriteBytes += block.writeBytes(outputStream);
            }
        }
        return iWriteBytes;
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        Block[] childes = getChildes();
        if (childes == null) {
            return;
        }
        if (skipReading(this, blockReader)) {
            return;
        }
        for (Block block : childes) {
            if (block != null && !skipReading(block, blockReader)) {
                block.readBytes(blockReader);
            }
        }
    }

    protected void nonCheckRead(BlockReader blockReader) throws IOException {
        Block[] childes = getChildes();
        if (childes == null) {
            return;
        }
        for (Block block : childes) {
            if (block != null) {
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

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        blockCounter.setCurrent(this);
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
            return;
        }
        Block[] childes = getChildes();
        if (childes == null) {
            return;
        }
        int length = childes.length;
        for (int i = 0; i < length && !blockCounter.FOUND; i++) {
            Block block = childes[i];
            if (block != null) {
                block.onCountUpTo(blockCounter);
            }
        }
    }

    public void addChild(int i, Block block) {
        this.mChildes[i] = block;
        if (block != null) {
            block.setIndex(i);
            block.setParent(this);
        }
    }

    @Override // com.reandroid.arsc.base.BlockRefresh
    public void refresh() {
        if (isNull()) {
            return;
        }
        onPreRefresh();
        refreshChildes();
        onRefreshed();
    }

    protected void refreshChildes() {
        Object[] childes = getChildes();
        if (childes == null) {
            return;
        }
        for (Object obj : childes) {
            if (obj instanceof BlockRefresh) {
                ((BlockRefresh) obj).refresh();
            }
        }
    }

    public int getChildesCount() {
        return this.mChildes.length;
    }

    public Block[] getChildes() {
        return this.mChildes;
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected final byte[] getBytesInternal() {
        throw new RuntimeException("Not block item");
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected void onBytesChanged() {
        throw new RuntimeException("Not block item");
    }

    @Override // com.reandroid.arsc.item.BlockItem
    public final int readBytes(InputStream inputStream) throws IOException {
        throw new RuntimeException("Not block item");
    }
}
