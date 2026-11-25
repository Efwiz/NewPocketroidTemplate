package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction23x extends ImmutableInstruction implements Instruction23x {
    public static final Format FORMAT = Format.Format23x;
    protected final int registerA;
    protected final int registerB;
    protected final int registerC;

    public ImmutableInstruction23x(Opcode opcode, int i, int i2, int i3) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.registerB = Preconditions.checkByteRegister(i2);
        this.registerC = Preconditions.checkByteRegister(i3);
    }

    public static ImmutableInstruction23x of(Instruction23x instruction23x) {
        if (instruction23x instanceof ImmutableInstruction23x) {
            return (ImmutableInstruction23x) instruction23x;
        }
        return new ImmutableInstruction23x(instruction23x.getOpcode(), instruction23x.getRegisterA(), instruction23x.getRegisterB(), instruction23x.getRegisterC());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.TwoRegisterInstruction
    public int getRegisterB() {
        return this.registerB;
    }

    @Override // org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction
    public int getRegisterC() {
        return this.registerC;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
