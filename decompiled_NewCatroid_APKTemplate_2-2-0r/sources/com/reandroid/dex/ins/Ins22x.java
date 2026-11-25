package com.reandroid.dex.ins;

import com.reandroid.dex.smali.SmaliWriter;

/* JADX INFO: loaded from: classes11.dex */
public class Ins22x extends Ins22 {
    @Override // com.reandroid.dex.ins.SizeXIns
    void appendCodeData(SmaliWriter smaliWriter) {
    }

    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public int getRegisterLimit(int i) {
        return i == 0 ? 255 : 65535;
    }

    public Ins22x(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public int getRegister(int i) {
        if (i == 0) {
            return getByteUnsigned(1);
        }
        return getShortUnsigned(2);
    }

    @Override // com.reandroid.dex.ins.Ins22, com.reandroid.dex.ins.RegistersSet
    public void setRegister(int i, int i2) {
        if (i == 0) {
            setByte(1, i2);
        } else {
            setShort(2, i2);
        }
    }
}
