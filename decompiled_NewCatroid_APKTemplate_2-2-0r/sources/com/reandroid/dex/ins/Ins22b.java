package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class Ins22b extends Ins22 implements RegistersSet {
    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public int getRegisterLimit(int i) {
        return 255;
    }

    public Ins22b(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public int getRegister(int i) {
        return getByteUnsigned(i + 1);
    }

    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i, int i2) {
        setByte(i + 1, i2);
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getByteUnsigned(3);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public int getSignedData() {
        return toSigned(getData(), 255);
    }

    @Override // com.reandroid.dex.ins.Size4Ins, com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        setByte(3, i);
    }
}
