package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class InsConstWide16 extends Ins21s implements ConstNumberLong {
    private InsConstWide mReplaced;

    @Override // com.reandroid.dex.ins.RegistersSet
    public boolean isWideRegisterAt(int i) {
        return true;
    }

    public InsConstWide16() {
        super(Opcode.CONST_WIDE_16);
    }

    @Override // com.reandroid.dex.ins.ConstNumberLong
    public void set(long j) {
        InsConstWide insConstWide = this.mReplaced;
        if (insConstWide != null) {
            insConstWide.set(j);
        } else if (j <= 32767 && j >= -32767) {
            setData((int) j);
        } else {
            replaceIns(j);
        }
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public long getLong() {
        InsConstWide insConstWide = this.mReplaced;
        if (insConstWide != null) {
            return insConstWide.getLong();
        }
        return getData();
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister() {
        return getRegister(0);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i) {
        setRegister(0, i);
    }

    @Override // com.reandroid.dex.ins.Ins21s, com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        InsConstWide insConstWide = this.mReplaced;
        if (insConstWide != null) {
            return insConstWide.getData();
        }
        return super.getData();
    }

    @Override // com.reandroid.dex.ins.Ins21s, com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        InsConstWide insConstWide = this.mReplaced;
        if (insConstWide != null) {
            insConstWide.set(i);
        } else if (i <= 32767 && i >= -32767) {
            super.setData(i);
        } else {
            replaceIns(i);
        }
    }

    private void replaceIns(long j) {
        InsConstWide insConstWide = (InsConstWide) replace(Opcode.CONST_WIDE);
        insConstWide.setRegister(getRegister());
        insConstWide.setData(j);
        this.mReplaced = insConstWide;
    }
}
