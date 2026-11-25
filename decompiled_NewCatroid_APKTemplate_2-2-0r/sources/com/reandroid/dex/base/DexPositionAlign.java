package com.reandroid.dex.base;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.AlignItem;
import java.io.IOException;

/* JADX INFO: loaded from: classes7.dex */
public class DexPositionAlign extends AlignItem {
    @Override // com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        super.align(blockReader.getPosition());
        super.onReadBytes(blockReader);
    }
}
