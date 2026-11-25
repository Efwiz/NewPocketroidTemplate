package com.reandroid.dex.data;

import com.reandroid.arsc.container.BlockList;
import com.reandroid.arsc.container.SingleBlockContainer;
import com.reandroid.arsc.io.BlockReader;
import com.reandroid.utils.CompareUtil;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public class HiddenApiDataList extends SingleBlockContainer<BlockList<HiddenApiData>> implements Iterable<HiddenApiData> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final BlockList<HiddenApiData> dataList;

    public HiddenApiDataList() {
        BlockList<HiddenApiData> blockList = new BlockList<>();
        this.dataList = blockList;
        setItem(blockList);
    }

    public HiddenApiData get(int i) {
        BlockList<HiddenApiData> blockList = this.dataList;
        int size = blockList.size();
        for (int i2 = 0; i2 < size; i2++) {
            HiddenApiData hiddenApiData = (HiddenApiData) blockList.get(i2);
            if (i == hiddenApiData.getOffset()) {
                return hiddenApiData;
            }
        }
        return null;
    }

    @Override // java.lang.Iterable
    public Iterator<HiddenApiData> iterator() {
        return this.dataList.iterator();
    }

    HiddenApiData getLast() {
        return (HiddenApiData) this.dataList.getLast();
    }

    private void add(HiddenApiData hiddenApiData) {
        this.dataList.add(hiddenApiData);
    }

    @Override // com.reandroid.arsc.container.SingleBlockContainer, com.reandroid.arsc.base.BlockContainer
    protected void onRefreshed() {
        super.onRefreshed();
        updateOffsets();
    }

    private void updateOffsets() {
        int offsetStart = getOffsetStart();
        for (HiddenApiData hiddenApiData : this) {
            hiddenApiData.setOffset(offsetStart);
            offsetStart += hiddenApiData.countBytes();
        }
    }

    @Override // com.reandroid.arsc.container.SingleBlockContainer, com.reandroid.arsc.base.BlockContainer, com.reandroid.arsc.base.Block
    public void onReadBytes(BlockReader blockReader) throws IOException {
        Iterator<HiddenApiIndex> hiddenApis = getHiddenApiList().getHiddenApis();
        while (hiddenApis.hasNext()) {
            HiddenApiIndex next = hiddenApis.next();
            HiddenApiData hiddenApiData = new HiddenApiData();
            add(hiddenApiData);
            next.linkData(hiddenApiData);
            hiddenApiData.onReadBytes(blockReader);
        }
        this.dataList.sort(new Comparator() { // from class: com.reandroid.dex.data.HiddenApiDataList$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return CompareUtil.compare(((HiddenApiData) obj).getOffset(), ((HiddenApiData) obj2).getOffset());
            }
        });
    }

    private int getOffsetStart() {
        return ((HiddenApiRestrictions) getParentInstance(HiddenApiRestrictions.class)).getHiddenApiDataListOffset();
    }

    private HiddenApiIndexList getHiddenApiList() {
        return ((HiddenApiRestrictions) getParentInstance(HiddenApiRestrictions.class)).getHiddenApiIndexList();
    }
}
