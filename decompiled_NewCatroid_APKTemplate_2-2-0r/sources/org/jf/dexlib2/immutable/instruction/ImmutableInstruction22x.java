package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction22x extends ImmutableInstruction implements Instruction22x {
    public static final Format FORMAT = Format.Format22x;
    protected final int registerA;
    protected final int registerB;

    public ImmutableInstruction22x(Opcode opcode, int i, int i2) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.registerB = Preconditions.checkShortRegister(i2);
    }

    public static ImmutableInstruction22x of(Instruction22x instruction22x) {
        if (instruction22x instanceof ImmutableInstruction22x) {
            return (ImmutableInstruction22x) instruction22x;
        }
        return new ImmutableInstruction22x(instruction22x.getOpcode(), instruction22x.getRegisterA(), instruction22x.getRegisterB());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return this.registerB;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
