package org.jf.dexlib2.dexbacked.instruction;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInstruction21c extends DexBackedInstruction implements Instruction21c {
    public DexBackedInstruction21c(DexBackedDexFile dexBackedDexFile, Opcode opcode, int i) {
        super(dexBackedDexFile, opcode, i);
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1);
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public Reference getReference() {
        return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2));
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public int getReferenceType() {
        return this.opcode.referenceType;
    }
}
