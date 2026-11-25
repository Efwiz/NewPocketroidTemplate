package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockCounter;
import com.reandroid.arsc.io.BlockReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class BlockItem extends Block {
    private static final byte[] EMPTY = new byte[0];
    private byte[] mBytes;

    protected void onBytesChanged() {
    }

    public BlockItem(int i) {
        if (i == 0) {
            this.mBytes = EMPTY;
        } else {
            this.mBytes = new byte[i];
        }
    }

    protected byte[] getBytesInternal() {
        return this.mBytes;
    }

    void setBytesInternal(byte[] bArr) {
        setBytesInternal(bArr, true);
    }

    void setBytesInternal(byte[] bArr, boolean z) {
        if (bArr == null || bArr.length == 0) {
            bArr = EMPTY;
        }
        if (bArr == this.mBytes) {
            return;
        }
        this.mBytes = bArr;
        if (z) {
            onBytesChanged();
        }
    }

    final void setBytesLength(int i) {
        setBytesLength(i, true);
    }

    protected final void setBytesLength(int i, boolean z) {
        if (i < 0) {
            i = 0;
        }
        if (i == 0) {
            this.mBytes = EMPTY;
            if (z) {
                onBytesChanged();
                return;
            }
            return;
        }
        byte[] bArr = this.mBytes;
        int length = bArr.length;
        if (i == length) {
            return;
        }
        byte[] bArr2 = new byte[i];
        if (i >= length) {
            i = length;
        }
        System.arraycopy(bArr, 0, bArr2, 0, i);
        this.mBytes = bArr2;
        if (z) {
            onBytesChanged();
        }
    }

    int getBytesLength() {
        return this.mBytes.length;
    }

    @Override // com.reandroid.arsc.base.Block
    public int countBytes() {
        if (isNull()) {
            return 0;
        }
        return getBytesInternal().length;
    }

    @Override // com.reandroid.arsc.base.Block
    public byte[] getBytes() {
        if (isNull()) {
            return null;
        }
        return getBytesInternal();
    }

    @Override // com.reandroid.arsc.base.Block
    public void onCountUpTo(BlockCounter blockCounter) {
        if (blockCounter.FOUND) {
            return;
        }
        if (blockCounter.END == this) {
            blockCounter.FOUND = true;
        } else {
            blockCounter.setCurrent(this);
            blockCounter.addCount(countBytes());
        }
    }

    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        byte[] bytesInternal = getBytesInternal();
        if (bytesInternal.length == 0) {
            return;
        }
        blockReader.readFully(bytesInternal);
        onBytesChanged();
    }

    @Override // com.reandroid.arsc.base.Block
    protected int onWriteBytes(OutputStream outputStream) throws IOException {
        byte[] bytesInternal = getBytesInternal();
        int length = bytesInternal.length;
        if (length == 0) {
            return 0;
        }
        outputStream.write(bytesInternal, 0, length);
        return length;
    }

    public int readBytes(InputStream inputStream) throws IOException {
        byte[] bytesInternal = getBytesInternal();
        int i = 0;
        if (bytesInternal != null && bytesInternal.length != 0) {
            int length = bytesInternal.length;
            int i2 = length;
            while (length > 0 && i2 > 0) {
                i2 = inputStream.read(bytesInternal, i, length);
                length -= i2;
                i += i2;
            }
            onBytesChanged();
            super.notifyBlockLoad();
        }
        return i;
    }

    public void setBytes(BlockItem blockItem) {
        if (blockItem != this) {
            setBytesInternal((byte[]) blockItem.getBytesInternal().clone());
        }
    }
}
