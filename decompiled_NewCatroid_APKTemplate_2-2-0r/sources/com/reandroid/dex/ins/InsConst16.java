package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class InsConst16 extends Ins21s implements RegistersSet, ConstNumber {
    public InsConst16() {
        super(Opcode.CONST_16);
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
