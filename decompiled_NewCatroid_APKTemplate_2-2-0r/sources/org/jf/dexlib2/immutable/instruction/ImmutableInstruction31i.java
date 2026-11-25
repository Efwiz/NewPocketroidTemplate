package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction31i extends ImmutableInstruction implements Instruction31i {
    public static final Format FORMAT = Format.Format31i;
    protected final int literal;
    protected final int registerA;

    public ImmutableInstruction31i(Opcode opcode, int i, int i2) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.literal = i2;
    }

    public static ImmutableInstruction31i of(Instruction31i instruction31i) {
        if (instruction31i instanceof ImmutableInstruction31i) {
            return (ImmutableInstruction31i) instruction31i;
        }
        return new ImmutableInstruction31i(instruction31i.getOpcode(), instruction31i.getRegisterA(), instruction31i.getNarrowLiteral());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
    }

    @Override // org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction
    public int getNarrowLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.iface.instruction.WideLiteralInstruction
    public long getWideLiteral() {
        return this.literal;
    }

    @Override // org.jf.dexlib2.immutable.instruction.ImmutableInstruction
    public Format getFormat() {
        return FORMAT;
    }
}
