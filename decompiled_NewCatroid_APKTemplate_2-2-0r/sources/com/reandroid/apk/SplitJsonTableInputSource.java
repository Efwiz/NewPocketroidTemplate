package com.reandroid.apk;

import com.reandroid.archive.BlockInputSource;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.utils.CRCDigest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class SplitJsonTableInputSource extends BlockInputSource<TableBlock> {
    private TableBlock mCache;
    private final File resourcesDirectory;

    public SplitJsonTableInputSource(File file) {
        super(TableBlock.FILE_NAME, (Block) null);
        this.resourcesDirectory = file;
    }

    @Override // com.reandroid.archive.BlockInputSource
    public TableBlock getBlock() {
        try {
            return getTableBlock();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.reandroid.archive.BlockInputSource, com.reandroid.archive.ByteInputSource, com.reandroid.archive.InputSource
    public long write(OutputStream outputStream) throws IOException {
        return getTableBlock().writeBytes(outputStream);
    }

    @Override // com.reandroid.archive.ByteInputSource, com.reandroid.archive.InputSource
    public InputStream openStream() throws IOException {
        return new ByteArrayInputStream(getTableBlock().getBytes());
    }

    @Override // com.reandroid.archive.BlockInputSource, com.reandroid.archive.InputSource
    public long getLength() throws IOException {
        return getTableBlock().countBytes();
    }

    @Override // com.reandroid.archive.BlockInputSource, com.reandroid.archive.InputSource
    public long getCrc() throws IOException {
        CRCDigest cRCDigest = new CRCDigest();
        write(cRCDigest);
        return cRCDigest.getValue();
    }

    public TableBlock getTableBlock() throws IOException {
        TableBlock tableBlock = this.mCache;
        if (tableBlock != null) {
            return tableBlock;
        }
        TableBlock tableBlockScanDirectory = new TableBlockJsonBuilder().scanDirectory(this.resourcesDirectory);
        this.mCache = tableBlockScanDirectory;
        return tableBlockScanDirectory;
    }
}
