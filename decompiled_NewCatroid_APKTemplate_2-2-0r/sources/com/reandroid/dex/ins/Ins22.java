package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public abstract class Ins22 extends Size4Ins implements RegistersSet {
    @Override // com.reandroid.dex.ins.RegistersSet
    public abstract int getRegister(int i);

    @Override // com.reandroid.dex.ins.RegistersSet
    public abstract int getRegisterLimit(int i);

    @Override // com.reandroid.dex.ins.RegistersSet
    public final int getRegistersCount() {
        return 2;
    }

    @Override // com.reandroid.dex.ins.RegistersSet
    public abstract void setRegister(int i, int i2);

    @Override // com.reandroid.dex.ins.RegistersSet
    public final void setRegistersCount(int i) {
    }

    public Ins22(Opcode<?> opcode) {
        super(opcode);
    }
}
