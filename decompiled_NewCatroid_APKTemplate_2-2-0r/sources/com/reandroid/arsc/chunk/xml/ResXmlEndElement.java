package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.ChunkType;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlEndElement extends BaseXmlChunk {
    private ResXmlStartElement mResXmlStartElement;

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

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ String getUri() {
        return super.getUri();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setComment(String str) {
        super.setComment(str);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setCommentReference(int i) {
        super.setCommentReference(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public /* bridge */ /* synthetic */ void setLineNumber(int i) {
        super.setLineNumber(i);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }

    public ResXmlEndElement() {
        super(ChunkType.XML_END_ELEMENT, 0);
    }

    public void setResXmlStartElement(ResXmlStartElement resXmlStartElement) {
        this.mResXmlStartElement = resXmlStartElement;
    }

    public ResXmlStartElement getResXmlStartElement() {
        return this.mResXmlStartElement;
    }
}
