package com.reandroid.arsc.pool;

import com.reandroid.arsc.array.OffsetArray;
import com.reandroid.arsc.array.StringArray;
import com.reandroid.arsc.array.TableStringArray;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.chunk.TableBlock;
import com.reandroid.arsc.chunk.UnknownChunk;
import com.reandroid.arsc.header.TableHeader;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.TableString;
import com.reandroid.xml.StyleDocument;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes10.dex */
public class TableStringPool extends StringPool<TableString> {
    public TableStringPool(boolean z) {
        super(z);
    }

    public TableString getOrCreateStyled(String str) throws XmlPullParserException, IOException {
        return getOrCreate(StyleDocument.parseStyledString(str));
    }

    public TableString getOrCreate(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        return getOrCreate(StyleDocument.parseNext(xmlPullParser));
    }

    @Override // com.reandroid.arsc.pool.StringPool
    void linkStrings() {
        super.linkStrings();
        TableBlock tableBlock = (TableBlock) getParentInstance(TableBlock.class);
        if (tableBlock != null) {
            tableBlock.linkTableStringsInternal(this);
        }
    }

    @Override // com.reandroid.arsc.pool.StringPool
    StringArray<TableString> newInstance(OffsetArray offsetArray, IntegerItem integerItem, IntegerItem integerItem2, boolean z) {
        return new TableStringArray(offsetArray, integerItem, integerItem2, z);
    }

    public static TableStringPool readFromTable(InputStream inputStream) throws IOException {
        TableHeader tableHeader = new TableHeader();
        tableHeader.readBytes(inputStream);
        if (tableHeader.getChunkType() != ChunkType.TABLE) {
            throw new IOException("Not TableBlock: " + tableHeader);
        }
        UnknownChunk unknownChunk = new UnknownChunk();
        unknownChunk.readBytes(inputStream);
        if (unknownChunk.getHeaderBlock().getChunkType() != ChunkType.STRING) {
            throw new IOException("Not StringPool chunk: " + unknownChunk);
        }
        BlockReader blockReader = new BlockReader(unknownChunk.getBytes());
        TableStringPool tableStringPool = new TableStringPool(true);
        tableStringPool.readBytes(blockReader);
        blockReader.close();
        return tableStringPool;
    }
}
