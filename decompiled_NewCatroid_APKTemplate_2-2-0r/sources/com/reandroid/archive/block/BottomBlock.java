package com.reandroid.archive.block;

import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.io.BlockReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class BottomBlock extends BlockList<LengthPrefixedBytes> {
    @Override // com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        while (blockReader.isAvailable()) {
            LengthPrefixedBytes lengthPrefixedBytes = new LengthPrefixedBytes(false);
            lengthPrefixedBytes.readBytes(blockReader);
            add(lengthPrefixedBytes);
        }
    }
}
