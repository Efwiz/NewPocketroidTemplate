package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction11x extends ImmutableInstruction implements Instruction11x {
    public static final Format FORMAT = Format.Format11x;
    protected final int registerA;

    public ImmutableInstruction11x(Opcode opcode, int i) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
    }

    public static ImmutableInstruction11x of(Instruction11x instruction11x) {
        if (instruction11x instanceof ImmutableInstruction11x) {
            return (ImmutableInstruction11x) instruction11x;
        }
        return new ImmutableInstruction11x(instruction11x.getOpcode(), instruction11x.getRegisterA());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
