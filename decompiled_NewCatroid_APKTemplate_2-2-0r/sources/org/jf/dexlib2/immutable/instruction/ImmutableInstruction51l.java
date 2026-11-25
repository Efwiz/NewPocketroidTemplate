package org.jf.dexlib2.immutable.instruction;

import org.jf.dexlib2.Format;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
import org.jf.dexlib2.util.Preconditions;

/* JADX INFO: loaded from: classes6.dex */
public class ImmutableInstruction51l extends ImmutableInstruction implements Instruction51l {
    public static final Format FORMAT = Format.Format51l;
    protected final long literal;
    protected final int registerA;

    public ImmutableInstruction51l(Opcode opcode, int i, long j) {
        super(opcode);
        this.registerA = Preconditions.checkByteRegister(i);
        this.literal = j;
    }

    public static ImmutableInstruction51l of(Instruction51l instruction51l) {
        if (instruction51l instanceof ImmutableInstruction51l) {
            return (ImmutableInstruction51l) instruction51l;
        }
        return new ImmutableInstruction51l(instruction51l.getOpcode(), instruction51l.getRegisterA(), instruction51l.getWideLiteral());
    }

    @Override // org.jf.dexlib2.iface.instruction.OneRegisterInstruction
    public int getRegisterA() {
        return this.registerA;
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
