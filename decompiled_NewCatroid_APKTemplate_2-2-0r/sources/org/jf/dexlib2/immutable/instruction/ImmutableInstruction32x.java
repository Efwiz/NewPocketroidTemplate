package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction32x extends ImmutableInstruction implements Instruction32x {
    public static final Format FORMAT = Format.Format32x;
    protected final int registerA;
    protected final int registerB;

    public ImmutableInstruction32x(Opcode opcode, int i, int i2) {
        super(opcode);
        this.registerA = Preconditions.checkShortRegister(i);
        this.registerB = Preconditions.checkShortRegister(i2);
    }

    public static ImmutableInstruction32x of(Instruction32x instruction32x) {
        if (instruction32x instanceof ImmutableInstruction32x) {
            return (ImmutableInstruction32x) instruction32x;
        }
        return new ImmutableInstruction32x(instruction32x.getOpcode(), instruction32x.getRegisterA(), instruction32x.getRegisterB());
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
