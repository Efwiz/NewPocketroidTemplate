package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction22t extends ImmutableInstruction implements Instruction22t {
    public static final Format FORMAT = Format.Format22t;
    protected final int codeOffset;
    protected final int registerA;
    protected final int registerB;

    public ImmutableInstruction22t(Opcode opcode, int i, int i2, int i3) {
        super(opcode);
        this.registerA = Preconditions.checkNibbleRegister(i);
        this.registerB = Preconditions.checkNibbleRegister(i2);
        this.codeOffset = Preconditions.checkShortCodeOffset(i3);
    }

    public static ImmutableInstruction22t of(Instruction22t instruction22t) {
        if (instruction22t instanceof ImmutableInstruction22t) {
            return (ImmutableInstruction22t) instruction22t;
        }
        return new ImmutableInstruction22t(instruction22t.getOpcode(), instruction22t.getRegisterA(), instruction22t.getRegisterB(), instruction22t.getCodeOffset());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return this.registerB;
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
