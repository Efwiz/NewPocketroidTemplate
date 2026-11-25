package com.reandroid.arsc.array;

import com.reandroid.arsc.base.BlockArray;
import com.reandroid.arsc.header.HeaderBlock;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.ResXmlID;
import com.reandroid.arsc.item.ResXmlString;
import com.reandroid.arsc.pool.ResXmlStringPool;
import com.reandroid.utils.CompareUtil;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Predicate;

/* JADX INFO: loaded from: classes10.dex */
public class ResXmlIDArray extends BlockArray<ResXmlID> {
    private final HeaderBlock mHeaderBlock;
    private final Map<Integer, ResXmlID> mResIdMap = new HashMap();
    private boolean mUpdated;

    @Override // com.reandroid.arsc.container.BlockList
    protected void onRefreshed() {
    }

    public ResXmlIDArray(HeaderBlock headerBlock) {
        this.mHeaderBlock = headerBlock;
    }

    public void addResourceId(int i, int i2) {
        if (i < 0) {
            return;
        }
        ensureSize(i + 1);
        ResXmlID resXmlID = (ResXmlID) get(i);
        if (resXmlID != null) {
            resXmlID.set(i2);
        }
    }

    public ResXmlID getOrCreate(int i) {
        updateIdMap();
        ResXmlID resXmlID = this.mResIdMap.get(Integer.valueOf(i));
        if (resXmlID != null) {
            return resXmlID;
        }
        ResXmlID resXmlID2 = new ResXmlID(i);
        add(resXmlID2);
        this.mUpdated = true;
        this.mResIdMap.put(Integer.valueOf(i), resXmlID2);
        return resXmlID2;
    }

    public ResXmlID getByResourceId(int i) {
        updateIdMap();
        return this.mResIdMap.get(Integer.valueOf(i));
    }

    public void refreshIdMap() {
        this.mUpdated = false;
        updateIdMap();
    }

    private void updateIdMap() {
        if (this.mUpdated) {
            return;
        }
        this.mUpdated = true;
        this.mResIdMap.clear();
        Iterator<T> it = iterator();
        while (it.hasNext()) {
            ResXmlID resXmlID = (ResXmlID) it.next();
            this.mResIdMap.put(Integer.valueOf(resXmlID.get()), resXmlID);
        }
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockCreator
    public ResXmlID newInstance() {
        this.mUpdated = false;
        return new ResXmlID();
    }

    @Override // com.reandroid.arsc.base.Creator, com.reandroid.arsc.base.BlockArrayCreator
    public ResXmlID[] newArrayInstance(int i) {
        this.mUpdated = false;
        return new ResXmlID[i];
    }

    @Override // com.reandroid.arsc.container.BlockList
    protected void onPreRefresh() {
        sort();
        super.onPreRefresh();
    }

    @Override // com.reandroid.arsc.base.BlockArray, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        setSize((this.mHeaderBlock.getChunkSize() - this.mHeaderBlock.getHeaderSize()) / 4);
        super.onReadBytes(blockReader);
        updateIdMap();
    }

    public boolean sort() {
        return sort(CompareUtil.getComparableComparator());
    }

    @Override // com.reandroid.arsc.container.BlockList
    public boolean sort(Comparator<? super ResXmlID> comparator) {
        boolean zSort = super.sort(comparator);
        adjustIndexes();
        trimLastItems();
        return zSort;
    }

    private void trimLastItems() {
        trimLastIf(new Predicate() { // from class: com.reandroid.arsc.array.ResXmlIDArray$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((ResXmlID) obj).isEmpty();
            }
        });
    }

    private void adjustIndexes() {
        int index;
        Iterator<T> itClonedIterator = clonedIterator();
        boolean z = false;
        while (itClonedIterator.hasNext()) {
            ResXmlID resXmlID = (ResXmlID) itClonedIterator.next();
            ResXmlString resXmlString = resXmlID.getResXmlString();
            if (resXmlString != null && (index = resXmlString.getIndex()) != resXmlID.getIndex()) {
                moveTo(resXmlID, index);
                z = true;
            }
        }
        if (z) {
            ((ResXmlStringPool) getParentInstance(ResXmlStringPool.class)).linkResXmlIDMapInternal();
        }
    }
}
