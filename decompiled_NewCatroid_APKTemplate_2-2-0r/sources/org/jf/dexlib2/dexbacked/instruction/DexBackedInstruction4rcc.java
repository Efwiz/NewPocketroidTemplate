package org.jf.dexlib2.dexbacked.instruction;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
import org.jf.dexlib2.iface.instruction.formats.Instruction4rcc;
import org.jf.dexlib2.iface.reference.Reference;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInstruction4rcc extends DexBackedInstruction implements Instruction4rcc {
    public DexBackedInstruction4rcc(DexBackedDexFile dexBackedDexFile, Opcode opcode, int i) {
        super(dexBackedDexFile, opcode, i);
    }

    @Override // org.jf.dexlib2.iface.instruction.VariableRegisterInstruction
    public int getRegisterCount() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1);
    }

    @Override // org.jf.dexlib2.iface.instruction.RegisterRangeInstruction
    public int getStartRegister() {
        return this.dexFile.getDataBuffer().readUshort(this.instructionStart + 4);
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public Reference getReference() {
        return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.getDataBuffer().readUshort(this.instructionStart + 2));
    }

    @Override // org.jf.dexlib2.iface.instruction.ReferenceInstruction
    public int getReferenceType() {
        return this.opcode.referenceType;
    }

    @Override // org.jf.dexlib2.iface.instruction.DualReferenceInstruction
    public Reference getReference2() {
        return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType2, this.dexFile.getDataBuffer().readUshort(this.instructionStart + 6));
    }

    @Override // org.jf.dexlib2.iface.instruction.DualReferenceInstruction
    public int getReferenceType2() {
        return this.opcode.referenceType2;
    }
}
