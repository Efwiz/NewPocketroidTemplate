package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.io.BlockReader;
import java.io.IOException;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlEndNamespace extends ResXmlNamespaceChunk {
    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getComment() {
        return super.getComment();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ int getCommentReference() {
        return super.getCommentReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ int getLineNumber() {
        return super.getLineNumber();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getName() {
        return super.getName();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ ResXmlElement getParentResXmlElement() {
        return super.getParentResXmlElement();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.common.Namespace
    public /* bridge */ /* synthetic */ String getPrefix() {
        return super.getPrefix();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk
    public /* bridge */ /* synthetic */ int getPrefixReference() {
        return super.getPrefixReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getUri() {
        return super.getUri();
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ int getUriReference() {
        return super.getUriReference();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setComment(String str) {
        super.setComment(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setCommentReference(int i) {
        super.setCommentReference(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setLineNumber(int i) {
        super.setLineNumber(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ void setPrefix(String str) {
        super.setPrefix(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk
    public /* bridge */ /* synthetic */ void setPrefixReference(int i) {
        super.setPrefixReference(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.ResXmlNamespace
    public /* bridge */ /* synthetic */ void setUri(String str) {
        super.setUri(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.ResXmlNamespaceChunk, com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public ResXmlEndNamespace() {
        super(ChunkType.XML_END_NAMESPACE);
    }

    public ResXmlStartNamespace getStart() {
        return (ResXmlStartNamespace) getPair();
    }

    public void setStart(ResXmlStartNamespace resXmlStartNamespace) {
        setPair(resXmlStartNamespace);
    }

    @Override // com.reandroid.arsc.chunk.Chunk, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        if (blockReader.readHeaderBlock().getChunkSize() < 8) {
            super.onReadChildes(blockReader);
        } else {
            super.onReadBytes(blockReader);
        }
    }
}
