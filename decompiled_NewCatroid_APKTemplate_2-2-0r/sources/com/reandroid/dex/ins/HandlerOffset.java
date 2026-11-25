package com.reandroid.dex.ins;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.base.Block;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.utils.CompareUtil;

/* JADX INFO: loaded from: classes11.dex */
public class HandlerOffset extends BlockItem implements Comparable<HandlerOffset> {
    private TryItem mTryItem;

    HandlerOffset() {
        super(8);
    }

    public int getStartAddress() {
        return Block.getInteger(getBytesInternal(), 0);
    }

    public void setStartAddress(int i) {
        Block.putInteger(getBytesInternal(), 0, i);
    }

    public int getCatchCodeUnit() {
        return Block.getShortUnsigned(getBytesInternal(), 4);
    }

    public void setCatchCodeUnit(int i) {
        Block.putShort(getBytesInternal(), 4, i);
    }

    public int getOffset() {
        return Block.getShortUnsigned(getBytesInternal(), 6);
    }

    public void setOffset(int i) {
        Block.putShort(getBytesInternal(), 6, i);
    }

    TryItem getTryItem() {
        return this.mTryItem;
    }

    void setTryItem(TryItem tryItem) {
        this.mTryItem = tryItem;
    }

    void removeSelf() {
        this.mTryItem = null;
        HandlerOffsetArray handlerOffsetArray = (HandlerOffsetArray) getParentInstance(HandlerOffsetArray.class);
        if (handlerOffsetArray != null) {
            handlerOffsetArray.remove(this);
        }
    }

    @Override // java.lang.Comparable
    public int compareTo(HandlerOffset handlerOffset) {
        if (handlerOffset == null) {
            return 0;
        }
        TryItem tryItem = getTryItem();
        if (tryItem == null) {
            throw new NullPointerException("Unlinked handler offset: " + toString());
        }
        TryItem tryItem2 = handlerOffset.getTryItem();
        if (tryItem2 == null) {
            throw new NullPointerException("Unlinked handler offset: " + handlerOffset.toString());
        }
        return CompareUtil.compare(tryItem.getIndex(), tryItem2.getIndex());
    }

    public void merge(HandlerOffset handlerOffset) {
        setBytes(handlerOffset);
    }

    public String toString() {
        return "(" + getIndex() + " : start=" + getStartAddress() + ", catch=" + getCatchCodeUnit() + ", offset=" + getOffset() + Constant.AFTER_QUTO;
    }
}
