package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class Size6Ins extends SizeXIns {
    @Override // com.reandroid.dex.ins.Ins
    public int getCodeUnits() {
        return 3;
    }

    public Size6Ins(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getShortUnsigned(2);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
        setShort(2, i);
    }
}
