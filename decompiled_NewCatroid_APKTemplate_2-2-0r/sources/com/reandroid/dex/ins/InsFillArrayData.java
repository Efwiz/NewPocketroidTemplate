package com.reandroid.dex.ins;

import com.reandroid.dex.data.InstructionList;

/* JADX INFO: loaded from: classes11.dex */
public class InsFillArrayData extends Ins31t {
    public InsFillArrayData() {
        super(Opcode.FILL_ARRAY_DATA);
    }

    public InsArrayData getInsArrayData() {
        InstructionList instructionList = getInstructionList();
        if (instructionList == null) {
            return null;
        }
        Ins atAddress = instructionList.getAtAddress(getTargetAddress());
        if (atAddress instanceof InsArrayData) {
            return (InsArrayData) atAddress;
        }
        return null;
    }

    @Override // com.reandroid.dex.ins.Ins31t
    String getLabelPrefix() {
        return ":array_";
    }
}
