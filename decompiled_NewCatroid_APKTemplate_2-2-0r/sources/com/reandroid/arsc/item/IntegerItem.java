package com.reandroid.arsc.item;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.DirectStreamReader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.HexUtil;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes10.dex */
public class IntegerItem extends BlockItem implements ReferenceItem, DirectStreamReader {
    private int mCache;

    public IntegerItem() {
        super(4);
    }

    public IntegerItem(int i) {
        this();
        set(i);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        if (i == this.mCache) {
            return;
        }
        this.mCache = i;
        byte[] bytesInternal = getBytesInternal();
        bytesInternal[3] = (byte) ((i >>> 24) & 255);
        bytesInternal[2] = (byte) ((i >>> 16) & 255);
        bytesInternal[1] = (byte) ((i >>> 8) & 255);
        bytesInternal[0] = (byte) (i & 255);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return this.mCache;
    }

    @Override // com.reandroid.arsc.item.ReferenceItem
    public <T1 extends Block> T1 getReferredParent(Class<T1> cls) {
        return (T1) getParentInstance(cls);
    }

    public long unsignedLong() {
        return ((long) get()) & 4294967295L;
    }

    public String toHex() {
        return HexUtil.toHex8(get());
    }

    @Override // com.reandroid.arsc.item.BlockItem
    protected void onBytesChanged() {
        this.mCache = readIntBytes();
    }

    private int readIntBytes() {
        byte[] bytesInternal = getBytesInternal();
        return ((bytesInternal[3] & 255) << 24) | (bytesInternal[0] & 255) | ((bytesInternal[1] & 255) << 8) | ((bytesInternal[2] & 255) << 16);
    }

    public String toString() {
        return String.valueOf(get());
    }

    public static int readInteger(BlockReader blockReader) throws IOException {
        IntegerItem integerItem = new IntegerItem();
        integerItem.readBytes(blockReader);
        return integerItem.get();
    }

    public static int readInteger(InputStream inputStream) throws IOException {
        IntegerItem integerItem = new IntegerItem();
        integerItem.readBytes(inputStream);
        return integerItem.get();
    }
}
