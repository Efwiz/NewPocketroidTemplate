package com.reandroid.arsc.chunk.xml;

import com.reandroid.arsc.array.ResXmlIDArray;
import com.reandroid.arsc.chunk.Chunk;
import com.reandroid.arsc.chunk.ChunkType;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.item.ResXmlID;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.arsc.pool.ResXmlStringPool;
import java.util.Iterator;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlIDMap extends Chunk<HeaderBlock> implements Iterable<ResXmlID> {
    private final ResXmlIDArray mResXmlIDArray;

    @Override // com.reandroid.arsc.chunk.Chunk
    protected void onChunkRefreshed() {
    }

    public ResXmlIDMap() {
        super(new HeaderBlock(ChunkType.XML_RESOURCE_MAP), 1);
        ResXmlIDArray resXmlIDArray = new ResXmlIDArray(getHeaderBlock());
        this.mResXmlIDArray = resXmlIDArray;
        addChild(resXmlIDArray);
    }

    void removeSafely(ResXmlID resXmlID) {
        ResXmlString resXmlString;
        ResXmlStringPool xmlStringPool;
        if (resXmlID == null || resXmlID.getParent() == null || resXmlID.getIndex() < 0 || resXmlID.hasReference() || (resXmlString = resXmlID.getResXmlString()) == null || resXmlString.getParent() == null || resXmlString.getIndex() < 0 || resXmlString.hasReference() || (xmlStringPool = getXmlStringPool()) == null) {
            return;
        }
        resXmlID.set(0);
        getResXmlIDArray().remove(resXmlID);
        xmlStringPool.removeString(resXmlString);
    }

    public int size() {
        return getResXmlIDArray().size();
    }

    public ResXmlID get(int i) {
        return (ResXmlID) getResXmlIDArray().get(i);
    }

    public void destroy() {
        getResXmlIDArray().clear();
    }

    public ResXmlIDArray getResXmlIDArray() {
        return this.mResXmlIDArray;
    }

    @Override // java.lang.Iterable
    public Iterator<ResXmlID> iterator() {
        return getResXmlIDArray().iterator();
    }

    public void addResourceId(int i, int i2) {
        getResXmlIDArray().addResourceId(i, i2);
    }

    public ResXmlID getOrCreate(int i) {
        return getResXmlIDArray().getOrCreate(i);
    }

    public ResXmlID getByResourceId(int i) {
        return getResXmlIDArray().getByResourceId(i);
    }

    ResXmlStringPool getXmlStringPool() {
        ResXmlDocument resXmlDocument = (ResXmlDocument) getParentInstance(ResXmlDocument.class);
        if (resXmlDocument != null) {
            return resXmlDocument.getStringPool();
        }
        return null;
    }

    @Override // com.reandroid.arsc.chunk.Chunk
    public void onChunkLoaded() {
        super.onChunkLoaded();
        ResXmlStringPool xmlStringPool = getXmlStringPool();
        if (xmlStringPool != null) {
            xmlStringPool.linkResXmlIDMapInternal();
        }
    }
}
