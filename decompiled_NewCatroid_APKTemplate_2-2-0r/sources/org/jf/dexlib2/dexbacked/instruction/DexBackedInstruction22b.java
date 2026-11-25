package org.jf.dexlib2.dexbacked.instruction;

import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.iface.instruction.formats.Instruction22b;

/* JADX INFO: loaded from: classes6.dex */
public class DexBackedInstruction22b extends DexBackedInstruction implements Instruction22b {
    public DexBackedInstruction22b(DexBackedDexFile dexBackedDexFile, Opcode opcode, int i) {
        super(dexBackedDexFile, opcode, i);
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 1);
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return this.dexFile.getDataBuffer().readUbyte(this.instructionStart + 2);
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    public int getNarrowLiteral() {
        return this.dexFile.getDataBuffer().readByte(this.instructionStart + 3);
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    public long getWideLiteral() {
        return getNarrowLiteral();
    }
}
