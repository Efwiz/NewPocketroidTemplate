package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.arsc.pool.ResXmlStringPool;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlText extends BaseXmlChunk {
    private final IntegerItem mReserved;

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

    public ResXmlText() {
        super(ChunkType.XML_CDATA, 1);
        IntegerItem integerItem = new IntegerItem();
        this.mReserved = integerItem;
        addChild(integerItem);
        setStringReference(0);
    }

    public String getText() {
        ResXmlString resXmlString = getResXmlString(getTextReference());
        if (resXmlString != null) {
            return resXmlString.getHtml();
        }
        return null;
    }

    public int getTextReference() {
        return getNamespaceReference();
    }

    public void setTextReference(int i) {
        setNamespaceReference(i);
    }

    public void setText(String str) {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool == null) {
            return;
        }
        setTextReference(stringPool.getOrCreate(str).getIndex());
    }

    @Override // com.reandroid.arsc.base.Block
    public boolean isNull() {
        return getText() == null || super.isNull();
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public String toString() {
        String text = getText();
        return text != null ? text : super.toString();
    }
}
