package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class Ins21ih extends Size4Ins implements RegistersSet {
    private InsConst mReplaced;

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegisterLimit(int i) {
        return 255;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegistersCount() {
        return 1;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegistersCount(int i) {
    }

    public Ins21ih(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister(int i) {
        return getByteUnsigned(1);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i, int i2) {
        setByte(1, i2);
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        InsConst insConst = this.mReplaced;
        if (insConst != null) {
            return insConst.getData();
        }
        return getShortUnsigned(2) << 16;
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        InsConst insConst = this.mReplaced;
        if (insConst != null) {
            insConst.setData(i);
        } else if ((65535 & i) != 0) {
            replaceIns(i);
        } else {
            setShort(2, i >>> 16);
        }
    }

    private void replaceIns(int i) {
        InsConst insConst = (InsConst) replace(Opcode.CONST);
        insConst.setRegister(getRegister());
        insConst.setData(i);
        this.mReplaced = insConst;
    }
}
