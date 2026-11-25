package org.jf.dexlib2.dexbacked.instruction;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.instruction.formats.Instruction22cs;
import org.jf.util.NibbleUtils;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInstruction22cs extends DexBackedInstruction implements Instruction22cs {
    public DexBackedInstruction22cs(DexBackedDexFile dexBackedDexFile, Opcode opcode, int i) {
        super(dexBackedDexFile, opcode, i);
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return NibbleUtils.extractLowUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1));
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return NibbleUtils.extractHighUnsignedNibble(this.dexFile.getDataBuffer().readByte(this.instructionStart + 1));
    }

    @Override // org.jf.dexlib2.iface.instruction.FieldOffsetInstruction
    public int getFieldOffset() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2);
    }
}
