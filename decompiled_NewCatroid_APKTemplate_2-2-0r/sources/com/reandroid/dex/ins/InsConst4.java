package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class InsConst4 extends Size2Ins implements RegistersSet, ConstNumber {
    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegisterLimit(int i) {
        return 15;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegistersCount() {
        return 1;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegistersCount(int i) {
    }

    public InsConst4() {
        super(Opcode.CONST_4);
    }

    @Override // com.reandroid.dex.ins.Size2Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getNibble(3);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public int getSignedData() {
        return toSigned(getData(), 15);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        setNibble(3, i & 15);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister(int i) {
        return getNibble(2);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i, int i2) {
        setNibble(2, i2);
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public int get() {
        return getData();
    }

    @Override // com.reandroid.arsc.item.IntegerReference
    public void set(int i) {
        setData(i);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public int getRegister() {
        return getRegister(0);
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i) {
        setRegister(0, i);
    }
}
