package com.reandroid.apk;

import com.reandroid.archive.BlockInputSource;
import com.reandroid.archive.FileInputSource;
import com.reandroid.archive.InputSource;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.json.JSONException;
import com.reandroid.json.JSONObject;
import com.reandroid.utils.CRCDigest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* JADX INFO: loaded from: classes10.dex */
public class SingleJsonTableInputSource extends BlockInputSource<TableBlock> {
    private final InputSource inputSource;
    private TableBlock mCache;

    public SingleJsonTableInputSource(InputSource inputSource) {
        super(TableBlock.FILE_NAME, (Block) null);
        this.inputSource = inputSource;
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
        TableBlock tableBlock2 = new TableBlock();
        try {
            tableBlock2.fromJson(new JSONObject(this.inputSource.openStream()));
            this.mCache = tableBlock2;
            return tableBlock2;
        } catch (JSONException e) {
            throw new IOException(this.inputSource.getAlias(), e);
        }
    }

    public static SingleJsonTableInputSource fromFile(File file, File file2) {
        return new SingleJsonTableInputSource(new FileInputSource(file2, ApkUtil.jsonToArchiveResourcePath(file, file2)));
    }
}
