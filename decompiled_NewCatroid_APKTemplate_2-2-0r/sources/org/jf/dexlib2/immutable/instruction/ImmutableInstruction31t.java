package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction31t extends ImmutableInstruction implements Instruction31t {
    public static final Format FORMAT = Format.Format31t;
    protected final int codeOffset;
    protected final int registerA;

    public ImmutableInstruction31t(Opcode opcode, int i, int i2) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.codeOffset = i2;
    }

    public static ImmutableInstruction31t of(Instruction31t instruction31t) {
        if (instruction31t instanceof ImmutableInstruction31t) {
            return (ImmutableInstruction31t) instruction31t;
        }
        return new ImmutableInstruction31t(instruction31t.getOpcode(), instruction31t.getRegisterA(), instruction31t.getCodeOffset());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.OffsetInstruction
    public int getCodeOffset() {
        return this.codeOffset;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
