package com.reandroid.dex.smali.model;

import com.reandroid.dex.ins.Opcode;
import com.reandroid.dex.smali.model.SmaliSwitchEntry;
import java.util.Iterator;

/* JADX INFO: loaded from: classes11.dex */
public abstract class SmaliSwitchPayload<T extends SmaliSwitchEntry> extends SmaliInstructionPayload<T> {
    private SmaliInstruction switchInstruction;

    public abstract Opcode<?> getSwitchOpcode();

    public SmaliSwitchPayload(SmaliInstructionOperand smaliInstructionOperand) {
        super(smaliInstructionOperand);
    }

    public SmaliInstruction getSwitch() {
        SmaliInstruction smaliInstruction = this.switchInstruction;
        if (smaliInstruction != null) {
            return smaliInstruction;
        }
        SmaliInstruction smaliInstructionFindSwitch = findSwitch();
        this.switchInstruction = smaliInstructionFindSwitch;
        return smaliInstructionFindSwitch;
    }

    public void setSwitch(SmaliInstruction smaliInstruction) {
        this.switchInstruction = smaliInstruction;
    }

    private SmaliInstruction findSwitch() {
        Opcode<?> switchOpcode = getSwitchOpcode();
        SmaliCodeSet codeSet = getCodeSet();
        if (codeSet != null) {
            int address = getAddress();
            Iterator<SmaliCode> itReversedIterator = codeSet.reversedIterator(codeSet.indexOf(this) - 1);
            while (itReversedIterator.hasNext()) {
                SmaliCode next = itReversedIterator.next();
                if (!(next instanceof SmaliLabel)) {
                    return null;
                }
                SmaliLabel smaliLabel = (SmaliLabel) next;
                if (address == smaliLabel.getAddress()) {
                    Iterator<SmaliInstruction> instructions = codeSet.getInstructions(smaliLabel);
                    while (instructions.hasNext()) {
                        SmaliInstruction next2 = instructions.next();
                        if (switchOpcode == next2.getOpcode()) {
                            return next2;
                        }
                    }
                }
            }
        }
        return null;
    }
}
