package com.reandroid.arsc.io;

import com.reandroid.arsc.base.Block;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public interface BlockLoad {
    void onBlockLoaded(BlockReader blockReader, Block block) throws IOException;
}
