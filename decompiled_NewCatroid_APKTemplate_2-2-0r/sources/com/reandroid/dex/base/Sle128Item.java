package com.reandroid.dex.base;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.dex.io.StreamUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class Sle128Item extends Le128 {
    @Override // com.reandroid.dex.base.Le128
    protected void writeValue(int i) {
        setBytesLength(5, false);
        setBytesLength(writeSleb128(getBytesInternal(), 0, i), false);
    }

    @Override // com.reandroid.dex.base.Le128
    protected int readLe128(BlockReader blockReader) throws IOException {
        int position = blockReader.getPosition();
        int sleb128 = readSleb128(StreamUtil.createByteReader(blockReader));
        int position2 = blockReader.getPosition() - position;
        blockReader.seek(position);
        setBytesLength(position2, false);
        blockReader.readFully(getBytesInternal());
        return sleb128;
    }
}
