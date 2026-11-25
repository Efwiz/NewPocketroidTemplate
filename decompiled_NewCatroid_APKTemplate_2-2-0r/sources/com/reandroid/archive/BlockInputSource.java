package com.reandroid.archive;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.base.BlockRefresh;
import com.reandroid.utils.CRCDigest;
import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class BlockInputSource<T extends Block> extends ByteInputSource {
    private final T mBlock;

    public BlockInputSource(String str, T t) {
        super(new byte[0], str);
        this.mBlock = t;
    }

    public BlockInputSource(InputSource inputSource, T t) {
        this(inputSource.getAlias(), t);
        setMethod(inputSource.getMethod());
        setSort(inputSource.getSort());
    }

    public T getBlock() {
        T t = this.mBlock;
        if (t instanceof BlockRefresh) {
            ((BlockRefresh) t).refresh();
        }
        return t;
    }

    @Override // com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getBlock().countBytes();
    }

    @Override // com.reandroid.archive.InputSource
    public long getCrc() throws IOException {
        Block block = getBlock();
        CRCDigest cRCDigest = new CRCDigest();
        block.writeBytes(cRCDigest);
        return cRCDigest.getValue();
    }

    @Override // com.reandroid.archive.ByteInputSource, com.reandroid.archive.InputSource
    public long write(OutputStream outputStream) throws IOException {
        return getBlock().writeBytes(outputStream);
    }

    @Override // com.reandroid.archive.ByteInputSource
    public byte[] getBytes() {
        return getBlock().getBytes();
    }
}
