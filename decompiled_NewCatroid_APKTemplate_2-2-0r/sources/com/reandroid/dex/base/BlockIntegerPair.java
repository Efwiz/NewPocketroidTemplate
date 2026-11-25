package com.reandroid.dex.base;

import com.huawei.hms.ml.language.common.utils.Constant;
import com.reandroid.arsc.item.BlockItem;
import com.reandroid.arsc.item.IndirectInteger;
import com.reandroid.arsc.item.IntegerReference;

/* JADX INFO: loaded from: classes7.dex */
public class BlockIntegerPair extends BlockItem implements IntegerPair {
    private final IntegerReference first;
    private final IntegerReference second;

    public BlockIntegerPair() {
        super(8);
        this.first = new IndirectInteger(this, 0);
        this.second = new IndirectInteger(this, 4);
    }

    @Override // com.reandroid.dex.base.IntegerPair
    public IntegerReference getFirst() {
        return this.first;
    }

    @Override // com.reandroid.dex.base.IntegerPair
    public IntegerReference getSecond() {
        return this.second;
    }

    public String toString() {
        return "(" + getFirst() + ", " + getSecond() + Constant.AFTER_QUTO;
    }
}
