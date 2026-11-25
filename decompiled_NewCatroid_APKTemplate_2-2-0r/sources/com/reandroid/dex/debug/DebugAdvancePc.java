package com.reandroid.dex.debug;

import com.reandroid.dex.base.DexException;
import com.reandroid.dex.base.Ule128Item;

/* JADX INFO: loaded from: classes11.dex */
public class DebugAdvancePc extends DebugAdvance {
    public DebugAdvancePc() {
        super(DebugElementType.ADVANCE_PC, new Ule128Item());
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public int getAddressDiff() {
        return getAdvance();
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public void setAddressDiff(int i) {
        setAdvance(i);
    }

    @Override // com.reandroid.dex.debug.DebugAdvance
    public void setAdvance(int i) {
        if (i < 0) {
            throw new DexException("Can not set negative advance: " + i);
        }
        super.setAdvance(i);
    }

    @Override // com.reandroid.dex.debug.DebugElement
    public DebugElementType<DebugAdvancePc> getElementType() {
        return DebugElementType.ADVANCE_PC;
    }
}
