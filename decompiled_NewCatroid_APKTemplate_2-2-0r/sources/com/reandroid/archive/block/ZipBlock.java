package com.reandroid.archive.block;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.BlockItem;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class ZipBlock extends BlockItem {
    @Override // com.reandroid.arsc.item.BlockItem
    public abstract int readBytes(InputStream inputStream) throws IOException;

    public ZipBlock(int i) {
        super(i);
    }

    public void putBytes(byte[] bArr, int i, int i2, int i3) {
        if (i3 <= 0 || bArr.length == 0) {
            return;
        }
        int i4 = i2 + i3;
        if (i4 > countBytes()) {
            setBytesLength(i4, false);
        }
        System.arraycopy(bArr, i, getBytesInternal(), i2, i3);
    }

    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        readBytes((InputStream) blockReader);
    }

    byte[] getBytes(int i, int i2, boolean z) {
        byte[] bytesInternal = getBytesInternal();
        if (z && (i < 0 || i >= bytesInternal.length || i + i2 > bytesInternal.length)) {
            return null;
        }
        if (i < 0) {
            i = 0;
        }
        int length = bytesInternal.length - i;
        if (i2 <= 0 || length <= 0) {
            return new byte[0];
        }
        if (i2 > length) {
            i2 = length;
        }
        byte[] bArr = new byte[i2];
        System.arraycopy(getBytesInternal(), i, bArr, 0, i2);
        return bArr;
    }

    long getLong(int i) {
        return getLong(getBytesInternal(), i);
    }

    void putLong(int i, long j) {
        putLong(getBytesInternal(), i, j);
    }

    long getIntegerUnsigned(int i) {
        return ((long) getInteger(i)) & 4294967295L;
    }

    void putBit(int i, int i2, boolean z) {
        putBit(getBytesInternal(), i, i2, z);
    }

    boolean getBit(int i, int i2) {
        return getBit(getBytesInternal(), i, i2);
    }

    int getByteUnsigned(int i) {
        return getBytesInternal()[i] & 255;
    }

    int getShortUnsigned(int i) {
        return getShort(getBytesInternal(), i) & 65535;
    }

    int getInteger(int i) {
        return getInteger(getBytesInternal(), i);
    }

    void putInteger(int i, int i2) {
        putInteger(getBytesInternal(), i, i2);
    }

    void putInteger(int i, long j) {
        putInteger(getBytesInternal(), i, (int) j);
    }

    void putShort(int i, int i2) {
        putShort(getBytesInternal(), i, (short) i2);
    }
}
