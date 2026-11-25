package com.reandroid.dex.ins;

import com.reandroid.dex.smali.SmaliWriter;
import com.reandroid.dex.smali.model.SmaliInstruction;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public class Ins10x extends Size2Ins {
    @Override // com.reandroid.dex.ins.Size2Ins, com.reandroid.dex.ins.SizeXIns
    public int getData() {
        return 0;
    }

    @Override // com.reandroid.dex.ins.SizeXIns
    public void setData(int i) {
    }

    public Ins10x(Opcode<?> opcode) {
        super(opcode);
    }

    @Override // com.reandroid.dex.ins.SizeXIns, com.reandroid.dex.ins.Ins
    public void appendCode(SmaliWriter smaliWriter) throws IOException {
        smaliWriter.append((CharSequence) getOpcode().getName());
    }

    @Override // com.reandroid.dex.ins.SizeXIns, com.reandroid.dex.ins.Ins
    public void fromSmali(SmaliInstruction smaliInstruction) throws IOException {
        validateOpcode(smaliInstruction);
    }
}
