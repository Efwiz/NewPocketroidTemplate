package com.reandroid.dex.data;

import com.reandroid.arsc.item.ShortArrayBlock;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.base.DexPositionAlign;
import com.reandroid.dex.base.PositionAlignedItem;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes11.dex */
public class ShortList extends IntegerList implements PositionAlignedItem {
    public ShortList() {
        super(0, new ShortArrayBlock(), new DexPositionAlign());
    }

    @Override // com.reandroid.dex.data.IntegerList, com.reandroid.common.IntegerArray
    public void put(int i, int i2) {
        if (((-65536) & i2) != 0) {
            throw new DexException("Short value out of range " + HexUtil.toHex(i2, 4) + " > 0xffff");
        }
        super.put(i, i2);
    }

    @Override // com.reandroid.dex.data.IntegerList
    public String toString() {
        DexPositionAlign positionAlign = getPositionAlign();
        if (positionAlign.size() > 0) {
            return super.toString() + positionAlign;
        }
        return super.toString();
    }
}
