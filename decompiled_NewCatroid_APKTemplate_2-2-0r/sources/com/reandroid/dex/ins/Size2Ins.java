package com.reandroid.dex.ins;

/* JADX INFO: loaded from: classes11.dex */
public class Size2Ins extends SizeXIns {
    @Override // com.reandroid.dex.ins.Ins
    public int getCodeUnits() {
        return 1;
    }

    public Size2Ins(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return getNibble(2);
    }
}
