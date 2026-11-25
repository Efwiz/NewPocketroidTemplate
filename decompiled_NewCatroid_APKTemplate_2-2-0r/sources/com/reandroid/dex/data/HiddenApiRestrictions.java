package com.reandroid.dex.data;

import com.reandroid.arsc.io.BlockReader;
import com.reandroid.arsc.item.IntegerItem;
import com.reandroid.dex.key.Key;
import com.reandroid.dex.sections.SectionType;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiRestrictions extends DataItem {
    private final HiddenApiDataList hiddenApiDataList;
    private final HiddenApiIndexList hiddenApiIndexList;
    private final IntegerItem sizeReference;

    public HiddenApiRestrictions() {
        super(3);
        IntegerItem integerItem = new IntegerItem();
        this.sizeReference = integerItem;
        HiddenApiIndexList hiddenApiIndexList = new HiddenApiIndexList();
        this.hiddenApiIndexList = hiddenApiIndexList;
        HiddenApiDataList hiddenApiDataList = new HiddenApiDataList();
        this.hiddenApiDataList = hiddenApiDataList;
        addChild(0, integerItem);
        addChild(1, hiddenApiIndexList);
        addChild(2, hiddenApiDataList);
    }

    public HiddenApiFlagValue getFlagValue(Key key) {
        return getHiddenApiIndexList().getFlagValue(key);
    }

    public HiddenApiIndexList getHiddenApiIndexList() {
        return this.hiddenApiIndexList;
    }

    public HiddenApiDataList getHiddenApiDataList() {
        return this.hiddenApiDataList;
    }

    @Override // com.reandroid.dex.data.DataItem, com.reandroid.dex.common.SectionItemContainer
    protected void onRefreshed() {
        super.onRefreshed();
        updateSizeFast();
    }

    private void updateSizeFast() {
        int iCountBytes;
        HiddenApiData last = this.hiddenApiDataList.getLast();
        if (last != null) {
            iCountBytes = last.getOffset();
            if (iCountBytes != 0) {
                iCountBytes += last.countBytes();
            }
        } else {
            iCountBytes = 0;
        }
        if (iCountBytes == 0 || iCountBytes != this.sizeReference.get()) {
            iCountBytes = countBytes();
        }
        this.sizeReference.set(iCountBytes);
    }

    int getHiddenApiDataListOffset() {
        return this.sizeReference.countBytes() + this.hiddenApiIndexList.countBytes();
    }

    @Override // com.reandroid.dex.common.SectionItemContainer, com.reandroid.arsc.item.BlockItem, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        this.sizeReference.readBytes(blockReader);
        BlockReader blockReaderCreate = blockReader.create(this.sizeReference.get());
        this.hiddenApiIndexList.onReadBytes(blockReaderCreate);
        this.hiddenApiDataList.onReadBytes(blockReaderCreate);
        blockReaderCreate.close();
        blockReader.seek(this.sizeReference.get());
    }

    @Override // com.reandroid.dex.common.SectionItem
    public SectionType<HiddenApiRestrictions> getSectionType() {
        return SectionType.HIDDEN_API;
    }
}
