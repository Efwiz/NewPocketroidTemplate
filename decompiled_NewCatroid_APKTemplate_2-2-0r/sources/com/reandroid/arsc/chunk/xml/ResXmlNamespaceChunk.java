package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.item.ResXmlString;

/* JADX INFO: loaded from: classes10.dex */
abstract class ResXmlNamespaceChunk extends BaseXmlChunk implements ResXmlNamespace {
    private ResXmlNamespaceChunk mPair;

    void onUriReferenceChanged(int i, int i2) {
    }

    ResXmlNamespaceChunk(ChunkType chunkType) {
        super(chunkType, 0);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public String getUri() {
        return getString(getUriReference());
    }

    public void setUri(String str) {
        if (str == null) {
            setUriReference(-1);
            return;
        }
        ResXmlString orCreateString = getOrCreateString(str);
        if (orCreateString == null) {
            throw new IllegalArgumentException("Null ResXmlString, add to parent element first");
        }
        setUriReference(orCreateString.getIndex());
    }

    public String getPrefix() {
        return getString(getPrefixReference());
    }

    public void setPrefix(String str) {
        if (str == null) {
            setPrefixReference(-1);
            return;
        }
        ResXmlString orCreateString = getOrCreateString(str);
        if (orCreateString == null) {
            throw new IllegalArgumentException("Null ResXmlString, add to parent element first");
        }
        setPrefixReference(orCreateString.getIndex());
    }

    public int getUriReference() {
        return getStringReference();
    }

    void setUriReference(int i) {
        int uriReference = getUriReference();
        setStringReference(i);
        ResXmlNamespaceChunk pair = getPair();
        if (pair != null && pair.getUriReference() != i) {
            pair.setUriReference(i);
        }
        if (uriReference != i) {
            onUriReferenceChanged(uriReference, i);
        }
    }

    public int getPrefixReference() {
        return getNamespaceReference();
    }

    public void setPrefixReference(int i) {
        setNamespaceReference(i);
        ResXmlNamespaceChunk pair = getPair();
        if (pair == null || pair.getPrefixReference() == i) {
            return;
        }
        pair.setPrefixReference(i);
    }

    ResXmlNamespaceChunk getPair() {
        return this.mPair;
    }

    void setPair(ResXmlNamespaceChunk resXmlNamespaceChunk) {
        if (resXmlNamespaceChunk == this) {
            return;
        }
        this.mPair = resXmlNamespaceChunk;
        if (resXmlNamespaceChunk == null || resXmlNamespaceChunk.getPair() == this) {
            return;
        }
        resXmlNamespaceChunk.setPair(this);
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk
    public void setLineNumber(int i) {
        if (i == getLineNumber()) {
            return;
        }
        super.setLineNumber(i);
        ResXmlNamespaceChunk pair = getPair();
        if (pair != null) {
            pair.setLineNumber(i);
        }
    }

    @Override // com.reandroid.arsc.chunk.xml.BaseXmlChunk, com.reandroid.arsc.chunk.Chunk
    public String toString() {
        if (getUri() == null) {
            return super.toString();
        }
        return "xmlns:" + getPrefix() + "=\"" + getUri() + "\"";
    }
}
