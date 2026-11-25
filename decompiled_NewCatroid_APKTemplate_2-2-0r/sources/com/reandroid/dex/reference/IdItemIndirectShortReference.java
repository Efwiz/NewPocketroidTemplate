package com.reandroid.dex.reference;

import com.reandroid.arsc.base.Block;
import com.reandroid.dex.base.DexException;
import com.reandroid.dex.common.SectionItem;
import com.reandroid.dex.id.IdItem;
import com.reandroid.dex.sections.SectionType;
import com.reandroid.utils.HexUtil;

/* JADX INFO: loaded from: classes11.dex */
public class IdItemIndirectShortReference<T extends IdItem> extends IdItemIndirectReference<T> {
    public IdItemIndirectShortReference(SectionType<T> sectionType, SectionItem sectionItem, int i, int i2) {
        super(sectionType, sectionItem, i, i2);
        Block.putShort(getBytesInternal(), getOffset(), 65535);
    }

    @Override // com.reandroid.dex.reference.IdItemIndirectReference, com.reandroid.arsc.item.IntegerReference
    public int get() {
        return Block.getShortUnsigned(getBytesInternal(), getOffset());
    }

    @Override // com.reandroid.dex.reference.IdItemIndirectReference, com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        if (((-65536) & i) != 0) {
            throw new DexException("Short value out of range " + HexUtil.toHex(i, 4) + " > 0xffff");
        }
        Block.putShort(getBytesInternal(), getOffset(), i);
    }

    @Override // com.reandroid.dex.reference.IdItemIndirectReference
    protected T pullItem(int i) {
        if (i == 65535) {
            return null;
        }
        return (T) super.pullItem(i);
    }
}
