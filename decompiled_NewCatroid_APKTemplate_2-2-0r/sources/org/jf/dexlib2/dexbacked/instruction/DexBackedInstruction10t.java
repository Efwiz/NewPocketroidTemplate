package org.jf.dexlib2.dexbacked.instruction;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.instruction.formats.Instruction10t;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInstruction10t extends DexBackedInstruction implements Instruction10t {
    public DexBackedInstruction10t(DexBackedDexFile dexBackedDexFile, Opcode opcode, int i) {
        super(dexBackedDexFile, opcode, i);
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    public int getCodeOffset() {
        return this.dexFile.getDataBuffer().readByte(this.instructionStart + 1);
    }
}
