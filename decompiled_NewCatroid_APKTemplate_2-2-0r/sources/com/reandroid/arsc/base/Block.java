package com.reandroid.arsc.base;

import com.reandroid.arsc.io.BlockLoad;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.HexUtil;
import com.reandroid.utils.ObjectsUtil;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Block {
    private BlockLoad mBlockLoad;
    private int mIndex = -1;
    private boolean mNull;
    private Block mParent;

    public abstract int countBytes();

    public abstract byte[] getBytes();

    public abstract void onCountUpTo(BlockCounter blockCounter);

    public void onIndexChanged(int i, int i2) {
    }

    protected void onReadBytes(BlockReader blockReader) throws IOException {
    }

    protected abstract int onWriteBytes(OutputStream outputStream) throws IOException;

    public final int countUpTo(Block block) {
        BlockCounter blockCounter = new BlockCounter(block);
        onCountUpTo(blockCounter);
        return blockCounter.getCountValue();
    }

    public final BlockLocator.Result locateBlock(int i) {
        BlockLocator blockLocator = new BlockLocator(i);
        onCountUpTo(blockLocator);
        return blockLocator.getResult();
    }

    public final void readBytes(BlockReader blockReader) throws IOException {
        onReadBytes(blockReader);
        notifyBlockLoad(blockReader);
    }

    public final void setBlockLoad(BlockLoad blockLoad) {
        this.mBlockLoad = blockLoad;
    }

    public void notifyBlockLoad() throws IOException {
        notifyBlockLoad(null);
    }

    private void notifyBlockLoad(BlockReader blockReader) throws IOException {
        BlockLoad blockLoad = this.mBlockLoad;
        if (blockLoad != null) {
            blockLoad.onBlockLoaded(blockReader, this);
        }
    }

    public final int writeBytes(OutputStream outputStream) throws IOException {
        if (isNull()) {
            return 0;
        }
        return onWriteBytes(outputStream);
    }

    public boolean isNull() {
        return this.mNull;
    }

    public void setNull(boolean z) {
        this.mNull = z;
    }

    public final int getIndex() {
        return this.mIndex;
    }

    public final void setIndex(int i) {
        int i2 = this.mIndex;
        if (i == i2) {
            return;
        }
        this.mIndex = i;
        if (i2 == -1 || i == -1) {
            return;
        }
        onIndexChanged(i2, i);
    }

    public final void setParent(Block block) {
        if (block == this) {
            return;
        }
        this.mParent = block;
    }

    public final Block getParent() {
        return this.mParent;
    }

    public final <T> T getParent(Class<T> cls) {
        for (Object obj = (T) getParent(); obj != null; obj = (T) ((Block) obj).getParent()) {
            if (obj.getClass() == cls) {
                return (T) obj;
            }
        }
        return (T) ObjectsUtil.cast(null);
    }

    public final <T> T getParentInstance(Class<T> cls) {
        for (Object obj = (T) getParent(); obj != null; obj = (T) ((Block) obj).getParent()) {
            if (cls.isInstance(obj)) {
                return (T) obj;
            }
        }
        return (T) ObjectsUtil.cast(null);
    }

    public static int getInteger(byte[] bArr, int i) {
        if (i + 4 > bArr.length) {
            return 0;
        }
        return ((bArr[i + 3] & 255) << 24) | (bArr[i] & 255) | ((bArr[i + 1] & 255) << 8) | ((bArr[i + 2] & 255) << 16);
    }

    public static short getShort(byte[] bArr, int i) {
        if (i + 2 > bArr.length) {
            return (short) 0;
        }
        return (short) (((bArr[i + 1] & 255) << 8) | (bArr[i] & 255));
    }

    public static int getShortUnsigned(byte[] bArr, int i) {
        if (i + 2 > bArr.length) {
            return 0;
        }
        return ((bArr[i + 1] & 255) << 8) | (bArr[i] & 255);
    }

    public static void putInteger(byte[] bArr, int i, int i2) {
        if (i + 4 > bArr.length) {
            return;
        }
        bArr[i + 3] = (byte) ((i2 >>> 24) & 255);
        bArr[i + 2] = (byte) ((i2 >>> 16) & 255);
        bArr[i + 1] = (byte) ((i2 >>> 8) & 255);
        bArr[i] = (byte) (i2 & 255);
    }

    public static void putShort(byte[] bArr, int i, short s) {
        bArr[i + 1] = (byte) ((s >>> 8) & 255);
        bArr[i] = (byte) (s & 255);
    }

    public static void putShort(byte[] bArr, int i, int i2) {
        bArr[i + 1] = (byte) ((i2 >>> 8) & 255);
        bArr[i] = (byte) (i2 & 255);
    }

    public static int getNibbleUnsigned(byte[] bArr, int i) {
        return ((bArr[i / 2] & 255) >> ((i % 2) * 4)) & 15;
    }

    public static void putNibbleUnsigned(byte[] bArr, int i, int i2) {
        if ((i2 & 15) != i2) {
            throw new IllegalArgumentException("Nibble value out of range " + HexUtil.toHex(i2, 1) + " > 0xf");
        }
        int i3 = i / 2;
        int i4 = (i % 2) * 4;
        bArr[i3] = (byte) ((i2 << i4) | (bArr[i3] & 255 & (i4 == 0 ? 240 : 15)));
    }

    public static boolean getBit(byte[] bArr, int i, int i2) {
        return (((bArr[i] & 255) >> i2) & 1) == 1;
    }

    public static void putBit(byte[] bArr, int i, int i2, boolean z) {
        int i3 = 1 << i2;
        bArr[i] = (byte) (((~i3) & 255 & bArr[i]) | (z ? i3 : 0));
    }

    public static long getLong(byte[] bArr, int i) {
        long j = 0;
        if (i + 8 > bArr.length) {
            return 0L;
        }
        for (int i2 = i + 7; i2 >= i; i2--) {
            j = (j << 8) | ((long) (bArr[i2] & 255));
        }
        return j;
    }

    public static void putLong(byte[] bArr, int i, long j) {
        int i2 = i + 8;
        if (i2 > bArr.length) {
            return;
        }
        while (i < i2) {
            bArr[i] = (byte) (255 & j);
            j >>>= 8;
            i++;
        }
    }

    public static byte[] getBytes(byte[] bArr, int i, int i2) {
        if (bArr.length == 0) {
            return new byte[0];
        }
        int length = bArr.length - i;
        if (length < 0) {
            length = 0;
        }
        if (i2 > length) {
            i2 = length;
        }
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    protected static byte[] addBytes(byte[] bArr, byte[] bArr2) {
        boolean z = true;
        boolean z2 = bArr == null || bArr.length == 0;
        if (bArr2 != null && bArr2.length != 0) {
            z = false;
        }
        if (z2 && z) {
            return null;
        }
        if (z2) {
            return bArr2;
        }
        if (z) {
            return bArr;
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        int length = bArr.length;
        System.arraycopy(bArr, 0, bArr3, 0, length);
        System.arraycopy(bArr2, 0, bArr3, length, bArr2.length);
        return bArr3;
    }

    public static boolean areEqual(Block[] blockArr, Block[] blockArr2) {
        if (blockArr == blockArr2) {
            return true;
        }
        if (blockArr == null) {
            return blockArr2.length == 0;
        }
        int length = blockArr.length;
        if (length != blockArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!areEqual(blockArr[i], blockArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static boolean areEqual(Block block, Block block2) {
        if (block == block2) {
            return true;
        }
        return areEqual(block == null ? null : block.getBytes(), block2 != null ? block2.getBytes() : null);
    }

    public static boolean areEqual(byte[] bArr, byte[] bArr2) {
        if (bArr == bArr2) {
            return true;
        }
        if (bArr == null) {
            return bArr2.length == 0;
        }
        int length = bArr.length;
        if (length != bArr2.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (bArr[i] != bArr2[i]) {
                return false;
            }
        }
        return true;
    }

    public static int hashCodeOf(Block[] blockArr) {
        if (blockArr == null) {
            return 0;
        }
        return hashCodeOf(blockArr, 0, blockArr.length);
    }

    public static int hashCodeOf(Block[] blockArr, int i, int i2) {
        if (blockArr == null || blockArr.length == 0) {
            return 0;
        }
        int iHashCodeOf = 1;
        while (i < i2) {
            iHashCodeOf = (iHashCodeOf * 31) + hashCodeOf(blockArr[i]);
            i++;
        }
        return iHashCodeOf;
    }

    public static int hashCodeOf(Block block) {
        if (block == null) {
            return 0;
        }
        return hashCodeOf(block.getBytes());
    }

    public static int hashCodeOf(byte[] bArr) {
        if (bArr == null || (bArr.length) == 0) {
            return 0;
        }
        int i = 1;
        for (byte b : bArr) {
            i = (i * 31) + (b & 255);
        }
        return i;
    }

    public static long longHashCode(byte[] bArr) {
        if (bArr == null || (bArr.length) == 0) {
            return 0L;
        }
        long j = 1;
        for (byte b : bArr) {
            j = (j * 31) + ((long) (b & 255));
        }
        return j;
    }
}
