package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.chunk.Chunk;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.header.XmlNodeHeader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.arsc.pool.ResXmlStringPool;

/* JADX INFO: loaded from: classes10.dex */
class BaseXmlChunk extends Chunk<XmlNodeHeader> {
    private final IntegerItem mNamespaceReference;
    private final IntegerItem mStringReference;

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
    }

    BaseXmlChunk(ChunkType chunkType, int i) {
        super(new XmlNodeHeader(chunkType), i + 2);
        IntegerItem integerItem = new IntegerItem(-1);
        this.mNamespaceReference = integerItem;
        IntegerItem integerItem2 = new IntegerItem(-1);
        this.mStringReference = integerItem2;
        addChild(integerItem);
        addChild(integerItem2);
    }

    void onRemoved() {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool == null) {
            return;
        }
        stringPool.removeReference(getHeaderBlock().getCommentReference());
        stringPool.removeReference(this.mNamespaceReference);
        stringPool.removeReference(this.mStringReference);
    }

    void linkStringReferences() {
        linkStringReference(getHeaderBlock().getCommentReference());
        linkStringReference(this.mNamespaceReference);
        linkStringReference(this.mStringReference);
    }

    private void linkStringReference(IntegerItem integerItem) {
        ResXmlString resXmlString = getResXmlString(integerItem.get());
        if (resXmlString != null) {
            resXmlString.addReferenceIfAbsent(integerItem);
        }
    }

    void unLinkStringReference(IntegerItem integerItem) {
        ResXmlString resXmlString = getResXmlString(integerItem.get());
        if (resXmlString != null) {
            resXmlString.removeReference(integerItem);
        }
    }

    public void setLineNumber(int i) {
        getHeaderBlock().getLineNumber().set(i);
    }

    public int getLineNumber() {
        return getHeaderBlock().getLineNumber().get();
    }

    public void setCommentReference(int i) {
        if (i == getCommentReference()) {
            return;
        }
        IntegerItem commentReference = getHeaderBlock().getCommentReference();
        unLinkStringReference(commentReference);
        getHeaderBlock().getCommentReference().set(i);
        linkStringReference(commentReference);
    }

    public int getCommentReference() {
        return getHeaderBlock().getCommentReference().get();
    }

    void setNamespaceReference(int i) {
        if (i == getNamespaceReference()) {
            return;
        }
        unLinkStringReference(this.mNamespaceReference);
        this.mNamespaceReference.set(i);
        linkStringReference(this.mNamespaceReference);
    }

    int getNamespaceReference() {
        return this.mNamespaceReference.get();
    }

    void setStringReference(int i) {
        if (i == getStringReference()) {
            return;
        }
        unLinkStringReference(this.mStringReference);
        this.mStringReference.set(i);
        linkStringReference(this.mStringReference);
    }

    int getStringReference() {
        return this.mStringReference.get();
    }

    ResXmlString setString(String str) {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool == null) {
            return null;
        }
        ResXmlString orCreate = stringPool.getOrCreate(str);
        setStringReference(orCreate.getIndex());
        return orCreate;
    }

    ResXmlStringPool getStringPool() {
        for (Block parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof ResXmlDocument) {
                return ((ResXmlDocument) parent).getStringPool();
            }
            if (parent instanceof ResXmlElement) {
                return ((ResXmlElement) parent).getStringPool();
            }
        }
        return null;
    }

    ResXmlString getResXmlString(int i) {
        ResXmlStringPool stringPool;
        if (i >= 0 && (stringPool = getStringPool()) != null) {
            return stringPool.get(i);
        }
        return null;
    }

    ResXmlString getOrCreateResXmlString(String str) {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool != null) {
            return stringPool.getOrCreate(str);
        }
        return null;
    }

    String getString(int i) {
        ResXmlString resXmlString = getResXmlString(i);
        if (resXmlString != null) {
            return resXmlString.get();
        }
        return null;
    }

    ResXmlString getOrCreateString(String str) {
        ResXmlStringPool stringPool = getStringPool();
        if (stringPool == null) {
            return null;
        }
        return stringPool.getOrCreate(str);
    }

    public String getName() {
        return getString(getStringReference());
    }

    public String getUri() {
        return getString(getNamespaceReference());
    }

    public String getComment() {
        return getString(getCommentReference());
    }

    public void setComment(String str) {
        if (str == null || str.length() == 0) {
            setCommentReference(-1);
        } else {
            if (str.equals(getComment())) {
                return;
            }
            setCommentReference(getOrCreateResXmlString(str).getIndex());
        }
    }

    public ResXmlElement getParentResXmlElement() {
        return (ResXmlElement) getParent(ResXmlElement.class);
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public String toString() {
        ChunkType chunkType = getHeaderBlock().getChunkType();
        if (chunkType == null) {
            return super.toString();
        }
        return chunkType.toString() + ": line=" + getLineNumber() + " {" + getName() + "}";
    }
}
